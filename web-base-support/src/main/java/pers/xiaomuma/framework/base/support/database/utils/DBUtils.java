package pers.xiaomuma.framework.base.support.database.utils;



import pers.xiaomuma.framework.base.support.database.DbInfo;
import pers.xiaomuma.framework.base.support.database.DbType;
import java.net.URI;

public class DBUtils {

	private static final DbInfo EMPTY_DB_INFO = new DbInfo(null, 0, null);

	public static DbInfo getDbInfoFromJdbcUrl(String jdbcUrl) {
		String cleanURI = null;
		DbType dbType = DbType.UNKNOWN;
		String host = null;
		int port = 0;

		try {
			if(jdbcUrl.startsWith("jdbc:jtds")) {
				cleanURI = jdbcUrl.substring(10);
				dbType = DbType.SQL_SERVER;
			} else if(jdbcUrl.startsWith("jdbc:mysql")) {
				cleanURI = jdbcUrl.substring(5);
				dbType = DbType.MYSQL;
			}
			if(cleanURI != null) {
				URI uri = URI.create(cleanURI);
				host = uri.getHost();
				port = uri.getPort();
				if(port == -1) {
					//没有填端口, 使用默认端口
					switch (dbType) {
						case SQL_SERVER:
							port = 1433;
							break;
						case MYSQL:
							port = 3306;
							break;
					}
				}
			}
			return new DbInfo(host, port, dbType);

		} catch (Exception e) {
			return null;
		}
	}
}
