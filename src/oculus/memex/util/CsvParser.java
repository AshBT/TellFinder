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
import java.util.List;

public class CsvParser {	
	public static List<String> fsmParse(String input) {
		ArrayList<String> result = new ArrayList<String>();
		int startChar = 0;
		int endChar = 0;
		boolean inString = false;
		while(endChar<input.length()) {
			char c = input.charAt(endChar);
			if (inString) {
				endChar++;
				if (c=='"') {
					if (endChar<input.length()&&input.charAt(endChar)=='"') {
						endChar++;
					} else {
						inString = false;
					}
				}
			} else {
				endChar++;
				if (c==',') {
					result.add(input.substring(startChar, endChar-1));
					startChar = endChar;
				} else if (c=='"') {
					inString = true;
				}
			}
		}
		if (startChar != endChar) {
			result.add(input.substring(startChar, endChar));
		}
		return result;
	}
}
