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
package oculus.memex.aggregation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import oculus.memex.db.DBManager;
import oculus.memex.db.MemexHTDB;
import oculus.memex.db.MemexOculusDB;
import oculus.memex.db.ScriptDBInit;
import oculus.memex.util.TimeLog;
import oculus.xdataht.model.TimeVolumeResult;

public class TimeLaborAggregation {
	static final public String POST_TIME_TABLE = "temporal_post_labor";
	private static final int BATCH_INSERT_SIZE = 2000;
	private static final int BATCH_SELECT_SIZE = 5000000;
	
	private static void createTable(MemexOculusDB db, Connection conn, String tableName) {
		try {
			String sqlCreate = 
					"CREATE TABLE `"+tableName+"` (" +
						  "count INT(11) NULL," +
						  "day INT(11) not NULL," +
						  "PRIMARY KEY (day) )";
			DBManager.tryStatement(conn, sqlCreate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initTable(String tableName) {
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		if (db.tableExists(conn, tableName)) {
			System.out.println("Clearing table: " + tableName);
			db.clearTable(conn, tableName);
		} else {			
			System.out.println("Creating table: " + tableName);
			createTable(db, conn, tableName);
		}
		db.close(conn);
	}

	public static ArrayList<String> getTimeAggregationTableNames() {
		ArrayList<String> tables = new ArrayList<String>();
		tables.add(POST_TIME_TABLE);
		return tables;
	}

	private static void computeTimes() {
		HashMap<String, HashMap<Long,Integer>> totaltimeseries = getTimeSeries();
		for(String tableName: totaltimeseries.keySet()) {
			ArrayList<TimeVolumeResult> a = new ArrayList<TimeVolumeResult>();
			for (Entry<Long, Integer> r:totaltimeseries.get(tableName).entrySet()) {
				a.add(new TimeVolumeResult(r.getKey(), r.getValue()));
			}
			Collections.sort(a, new Comparator<TimeVolumeResult>() {
				public int compare(TimeVolumeResult o1, TimeVolumeResult o2) {
					return (int)(o1.getDay()-o2.getDay());
				};
			});
			insertTemporalData(a, tableName);
		}
	}

	private static void insertTemporalData(ArrayList<TimeVolumeResult> data, String tableName) {
		PreparedStatement pstmt = null;
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("INSERT INTO " + tableName + " (day,count) VALUES (?, ?)");
			int count = 0;
			for (TimeVolumeResult r:data) {
				pstmt.setLong(1, r.getDay());
				pstmt.setInt(2, r.getCount());
				pstmt.addBatch();
				count++;
				if (count % BATCH_INSERT_SIZE == 0) {
					pstmt.executeBatch();
				}
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) { pstmt.close(); }
			} catch (SQLException e) {e.printStackTrace();}
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {e.printStackTrace();}
		}
		db.close(conn);
	}
	
	private static int getMaxAdsID() {
		String sqlStr = "SELECT max(id) as max FROM backpage_incoming";
		MemexHTDB db = MemexHTDB.getInstance();
		Connection conn = db.open();
		int result = MemexHTDB.getInt(conn, sqlStr, "Get max attribute id");
		db.close(conn);
		return result;
	}

	private static void insertTimeMapData(HashMap<Long,Integer> timeMap, Date date, Calendar c) {
		long time = 0;
		if (date!=null) {
			c.setTime(date);
			c.set(Calendar.HOUR,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND,0);
			c.set(Calendar.MILLISECOND,0);
			time = c.getTimeInMillis()/1000;
		}
		Integer i = timeMap.get(time);
		if (i==null) timeMap.put(time,1);
		else timeMap.put(time,i+1);
	}

	private static HashMap<String, HashMap<Long, Integer>> getTimeSeries() {
		HashMap<String, HashMap<Long, Integer>> result = new HashMap<String, HashMap<Long, Integer>>();
		HashMap<Long,Integer> posttimeMap = new HashMap<Long,Integer>();
		int id = 0;
		int maxID = getMaxAdsID();
		String sqlStr;
		Statement stmt = null;
		MemexHTDB db = MemexHTDB.getInstance();
		Connection conn = db.open();
		try {
			stmt = conn.createStatement();
			while(id<maxID) {
				sqlStr = "SELECT timestamp as posttime FROM backpage_incoming WHERE id >= " + id + " AND id < " + (id+BATCH_SELECT_SIZE);
				ResultSet rs = stmt.executeQuery(sqlStr);
				Calendar c = Calendar.getInstance();
				while (rs.next()) {
					insertTimeMapData(posttimeMap, rs.getDate("posttime"), c);
				}
				id += BATCH_SELECT_SIZE;
			}
			result.put(POST_TIME_TABLE, posttimeMap);
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
	
	public static void main(String[] args) {
		TimeLog tl = new TimeLog();
		tl.pushTime("Time aggregation");

		ScriptDBInit.readArgs(args);
		MemexOculusDB.getInstance(ScriptDBInit._oculusSchema, ScriptDBInit._type, ScriptDBInit._hostname, ScriptDBInit._port, ScriptDBInit._user, ScriptDBInit._pass);
		MemexHTDB.getInstance("roxy_scrape", ScriptDBInit._type, ScriptDBInit._hostname, ScriptDBInit._port, ScriptDBInit._user, ScriptDBInit._pass);
		
		initTable(POST_TIME_TABLE);
		
		computeTimes();
		tl.popTime();
	}
}