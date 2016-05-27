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
package oculus.memex.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import oculus.xdataht.data.DataRow;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class DataUtil {
	private static Whitelist TAG_WHITELIST = Whitelist.simpleText().addTags("br", "hr");
	
	public static ArrayList<HashMap<String, String>> sanitizeHtml(List<DataRow> dataRows) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		for (int i=0; i < dataRows.size(); i++) {
			HashMap<String, String> row = dataRows.get(i).copyFields();
			for (Entry<String, String> entry : row.entrySet()) {
				String value = entry.getValue();
				// The indexOf check speeds this up by about 35%.
				if (value != null && value.indexOf('<') != -1) {
					entry.setValue(Jsoup.clean(value, TAG_WHITELIST));
				}
			}
			result.add(row);
		}
		return result;
	}
	
	public static String sanitizeHtml(String value) {
		if (value != null && value.indexOf('<') != -1) {
			value = Jsoup.clean(value, TAG_WHITELIST);
		}
		return value;
	}
}
