/**
Copyright 2016 Uncharted Software Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package oculus.memex.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;



public class MemexHTDB extends DBManager {
	public static final int BATCH_INSERT_SIZE = 2000;
	static final public String MEMEX_ADS_TABLE = "ads";
	private static MemexHTDB _instance = null;

	public static MemexHTDB getInstance() {
		if (_instance==null) {
			try {
				_instance = new MemexHTDB();
			} catch (Exception e) {
				System.out.println("FAILED TO CONNECT TO DATABASE: " + e.getMessage());
			}
		}
		return _instance;
	}
	
	public static MemexHTDB getInstance(String name, String type, String hostname, String port, String user, String pass) {
		if (_instance==null) {
			try {
				_instance = new MemexHTDB(name, type, hostname, port, user, pass);
			} catch (Exception e) {
				System.out.println("FAILED TO CONNECT TO DATABASE: " + e.getMessage());
			}
		}
		return _instance;
	}
	
	private MemexHTDB() throws Exception {
		super(ScriptDBInit._htSchema, ScriptDBInit._type, ScriptDBInit._hostname, ScriptDBInit._port, ScriptDBInit._user, ScriptDBInit._pass);
	}
	
	public MemexHTDB(String name, String type, String hostname, String port, String user, String pass) throws Exception {
		super(name, type, hostname, port, user, pass);
	}

	public static HashSet<Integer> getAds(String whereClause) {
		MemexHTDB db = MemexHTDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "SELECT id from ads where " + whereClause;
		Statement stmt = null;
		HashSet<Integer> result = new HashSet<Integer>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				result.add(rs.getInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) { stmt.close(); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close(conn);
		return result;
	}	
	
	
	
}
