package pers.xiaomuma.framework.support.database;

public class DbInfo {

	private String host;

	private int port;

	private DbType dbType;

	public DbInfo() {}

	public DbInfo(String host, int port, DbType dbType) {
		this.host = host;
		this.port = port;
		this.dbType = dbType;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public DbType getDbType() {
		return dbType;
	}

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

	public boolean isNull() {
		return host == null || port <= 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DbInfo dbInfo = (DbInfo) o;

		if (port != dbInfo.port) return false;
		return host != null ? host.equals(dbInfo.host) : dbInfo.host == null;
	}

	@Override
	public int hashCode() {
		int result = host != null ? host.hashCode() : 0;
		result = 31 * result + port;
		return result;
	}

}
