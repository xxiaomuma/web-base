package pers.xiaomuma.framework.support.database.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.List;
import java.util.Properties;


@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class MybatisSQLParameterInterceptor implements Interceptor {


	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		if (SqlCommandType.INSERT.equals(sqlCommandType) ||
				SqlCommandType.UPDATE.equals(sqlCommandType)) {
			// 检查parameter 中是否有 NULL 的值
			checkNullValue(boundSql, configuration);
		}
		return invocation.proceed();
	}

	private void checkNullValue(BoundSql boundSql, Configuration configuration) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

			if (!typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);

				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					JdbcType jdbcType = parameterMapping.getJdbcType();

					if (jdbcType == null) {
						return;
					}

					if (metaObject.hasGetter(propertyName) && metaObject.getValue(propertyName) == null) {
						metaObject.setValue(propertyName, getReplaceValue(jdbcType));
					} else if (boundSql.hasAdditionalParameter(propertyName) &&
							boundSql.getAdditionalParameter(propertyName) == null) {
						boundSql.setAdditionalParameter(propertyName, getReplaceValue(jdbcType));
					}
				}
			}
		}
	}

	public Object getReplaceValue(JdbcType jdbcType) {
		Object value = null;
		switch (jdbcType) {
			case VARCHAR:
				value = "";
				break;
			case INTEGER:
				value = 0;
				break;
			case TINYINT:
				value = -1;
				break;
			default:
				break;
		}
		return value;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
