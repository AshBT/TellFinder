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
package oculus.xdataht.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oculus.memex.util.CsvParser;

public class DataTable {
	private static String DATA_DIR = "c:/dev";
	
	public ArrayList<DataRow> rows;
	public ArrayList<String> columns;
	private HashMap<String, DataRow> _rowById = new HashMap<String, DataRow>();
	public DataTable() {
	}
	
	public DataRow getRowById(String id) {
		return _rowById.get(id);
	}
	
	public void updateRowLookup() {
		_rowById = new HashMap<String, DataRow>();
		for (DataRow row : rows) {
			_rowById.put(row.get("id"), row);
		}
	}
	
	public void merge(DataTable other) {
		
		for (String col : columns) {
			assert(other.columns.indexOf(col) != -1);
		}
		assert(other.columns.size() == columns.size());
		
		for (DataRow row : other.rows) {
			rows.add(row);
		}
		updateRowLookup();
	}
	
	protected void readCSV(String filename) {
		BufferedReader br = null;
		try {
			InputStream is = new FileInputStream(DATA_DIR + "/" + filename);
			br = new BufferedReader(new InputStreamReader(is));
			
			String lineString = br.readLine();
			List<String> line = CsvParser.fsmParse(lineString);
			columns = new ArrayList<String>(line);
			rows = new ArrayList<DataRow>();
			while ((lineString = br.readLine()) != null) {
				line = CsvParser.fsmParse(lineString);
				if (line.size()==columns.size()) {
					DataRow row = new DataRow(columns, line);
					
					String id = row.get("id");
					_rowById.put(id, row);
					
					rows.add(row);
				} else {
					System.out.println(line.size() + ": " + lineString);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int size() {
		if (rows == null || rows.size() == 0) {
			return 0;
		} else {
			return rows.size();
		}
	}
}
