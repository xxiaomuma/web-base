package pers.xiaomuma.framework.support.database;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.springframework.beans.factory.annotation.Value;

public class MybatisPlusGeneratorTemplate {

	private static final String OUTPUT_DIR = "/Users/elliot/";

	private static final String DATASOURCE_NAME = "yx-external-user.write";

	private static final String PACKAGE_PARENT = "com.yxbl.ms.user";

	private static final String PACKAGE_MODEL = "core";

	@Value("${spring.datasource."+ DATASOURCE_NAME + ".url}")
	private String url;

	@Value("${spring.datasource."+ DATASOURCE_NAME + ".username}")
	private String username;

	@Value("${spring.datasource."+ DATASOURCE_NAME + ".password}")
	private String password;

	@Value("${spring.datasource."+ DATASOURCE_NAME + ".driver-class-name}")
	private String driveClassName;

	public void generate() {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(OUTPUT_DIR);
		gc.setOpen(true);
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(true);// XML columList
		gc.setFileOverride(true);
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		// gc.setSwagger2(true); 实体属性 Swagger2 注解
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(url);
		// dsc.setSchemaName("public");
		dsc.setDriverName(driveClassName);
		dsc.setUsername(username);
		dsc.setPassword(password);
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(PACKAGE_MODEL);
		pc.setParent(PACKAGE_PARENT);
		pc.setMapper("mapper");
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		mpg.setCfg(cfg);

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();
		mpg.setTemplate(templateConfig);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		// 写于父类中的公共字段
		strategy.setSuperEntityColumns("id");
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setInclude(new String[] {
				"account",
				"role",
				"user_role_relation",
				"delivery_address"
		}); // 需要生成的表
		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new VelocityTemplateEngine());
		mpg.execute();
	}

}
