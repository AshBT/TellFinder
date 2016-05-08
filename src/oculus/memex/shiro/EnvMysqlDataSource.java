package oculus.memex.shiro;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class EnvMysqlDataSource extends MysqlDataSource {
	
	private static final long serialVersionUID = 1180035204072942804L;

	@Override
	public void setServerName(String serverName_) {
		String serverName = System.getenv(serverName_) != null ? System.getenv(serverName_) : serverName_;
		super.setServerName(serverName);
	}
	
	@Override
	public void setUser(String userID_) {
		String userID = System.getenv(userID_) != null ? System.getenv(userID_) : userID_;
		super.setUser(userID);
	}
	
	@Override
	public void setPassword(String pass_) {
		String pass = System.getenv(pass_) != null ? System.getenv(pass_) : pass_;
		super.setPassword(pass);
	}
	
	@Override
	public void setDatabaseName(String dbName_) {
		String dbName = System.getenv(dbName_) != null ? System.getenv(dbName_) : dbName_;
		super.setDatabaseName(dbName);
	}

}
