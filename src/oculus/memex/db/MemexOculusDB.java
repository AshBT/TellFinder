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



public class MemexOculusDB extends DBManager {
	public static final int BATCH_INSERT_SIZE = 2000;
	private static MemexOculusDB _instance = null;

	public static MemexOculusDB getInstance() {
		if (_instance==null) {
			try {
				_instance = new MemexOculusDB();
			} catch (Exception e) {
				System.out.println("FAILED TO CONNECT TO DATABASE: " + e.getMessage());
			}
		}
		return _instance;
	}
	
	public static MemexOculusDB getInstance(String name, String type, String hostname, String port, String user, String pass) {
		if (_instance==null) {
			try {
				_instance = new MemexOculusDB(name, type, hostname, port, user, pass);
			} catch (Exception e) {
				System.out.println("FAILED TO CONNECT TO DATABASE: " + e.getMessage());
			}
		}
		return _instance;
	}
	
	private MemexOculusDB() throws Exception {
		super(ScriptDBInit._oculusSchema, ScriptDBInit._type, ScriptDBInit._hostname, ScriptDBInit._port, ScriptDBInit._user, ScriptDBInit._pass);
	}
	
	private MemexOculusDB(String name, String type, String hostname, String port, String user, String pass) throws Exception {
		super(name, type, hostname, port, user, pass);
	}

	public static HashSet<Integer> getPhoneAds(String whereClause) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "SELECT ads_id from ads_phones where " + whereClause;
		Statement stmt = null;
		HashSet<Integer> result = new HashSet<Integer>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				result.add(rs.getInt("ads_id"));
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
	
	public static HashSet<Integer> getValueAds(String table, String search, boolean exact) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "SELECT ads_id from " + table + " where value like '%" + search + "%'";
		if (exact) sqlStr = "SELECT ads_id from " + table + " where value='" + search + "'";
		Statement stmt = null;
		HashSet<Integer> result = new HashSet<Integer>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				result.add(rs.getInt("ads_id"));
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

	public static void deleteValueAds(String table, String search) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "delete from " + table + " where value='" + search + "'";
		DBManager.tryStatement(conn, sqlStr);
		db.close(conn);
	}

	public static void deleteValueAdsById(String table, String search, String adidStr) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "delete from " + table + " where value='" + search + "' and ads_id IN " + adidStr;
		DBManager.tryStatement(conn, sqlStr);
		db.close(conn);
	}

	public static void renameValueAds(String table, String oldValue, String newValue) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "update " + table + " set value='" + newValue + "' where value='" + oldValue + "'";
		DBManager.tryStatement(conn, sqlStr);
		db.close(conn);
	}

	public static void addBadValue(String table, String value) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		String sqlStr = "insert into " + table + "(value) values ('" + value + "')";
		DBManager.tryStatement(conn, sqlStr);
		db.close(conn);
	}	
	
}
