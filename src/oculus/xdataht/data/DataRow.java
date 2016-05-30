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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Let's keep the _fields private so we can always strip unsafe html from the values before they go out.
 * It could be that some analysis/tool requires the bad html in there, in which case this isn't the best place
 * to do the filtering.
 *
 */
public class DataRow {
	@JsonProperty("fields")
	private HashMap<String,String> _fields = new HashMap<String,String>();
	
	
	public DataRow(ArrayList<String> columns, List<String> line) {
		for (int i=0; i<columns.size(); i++) {
			put(columns.get(i), line.get(i));
		}
	}
	
	public DataRow(String id, String name, String title) {
		put("id", id);
		put("name", name);
		put("title", title);
	}
	
	public DataRow() {
	}
	
	public void put(String columnName, String value) {
		_fields.put(columnName, value);
	}
	
	public String get(String id) {
		return _fields.get(id);
	}
	
	public HashMap<String, String> copyFields() {
		return new HashMap<String, String>(_fields);
	}

}
