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

import java.util.HashSet;
import java.util.Set;

public class StringUtil {
	public static String stripNonNumeric(String value) {
		if (value==null) return null;
		byte[] bytes = value.getBytes();
		byte[] result = new byte[bytes.length];
		int count = 0;
		for (byte b:bytes) {
			if (b>=48&&b<=57) {
				result[count] = b;
				count++;
			}
		}
		if (count==0) return null;
		return new String(result, 0, count);
	}
	
	public static String hashSetToSqlList(Set<Integer> set) {
		if (set.isEmpty())
			return "()";
		String list = set.toString();
		return "(" + list.substring(1, list.length() - 1) + ")";
	}
	
	public static String hashSetStringToSqlList(HashSet<String> set) {
		if (set.isEmpty())
			return "()";
		String list = set.toString();
		return "(" + list.substring(1, list.length() - 1) + ")";
	}
	public static String hashSetToQuotedSqlList(HashSet<String> set) {
		StringBuffer result = new StringBuffer();
		result.append("(");
		boolean isFirst = true;
		for (String item:set) {
			if (isFirst) isFirst = false;
			else result.append(",");
			result.append("'");
			result.append(item);
			result.append("'");
		}
		result.append(")");
		return result.toString();
	}
	public static String commasAndQuestions(int count) {
		StringBuilder result = new StringBuilder();
		boolean isFirst = true;
		for (int i=0; i<count; i++) {
			if (isFirst) {
				result.append("?");
				isFirst = false;
			} else result.append(",?");
		}
		return result.toString();
	}
	
	final protected static char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_CHARS[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_CHARS[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	public static int hexToInt(char hex) {
    	if (hex>='0'&&hex<='9') return (int)(hex-'0');
    	else if (hex>='A'&&hex<='F') return (int)(10+hex-'A');
    	return (int)(10+hex-'a');
	}
	public static byte[] hexToBytes(String hexStr) {
		char[] hex = hexStr.toCharArray();
		byte[] result = new byte[hex.length/2];
	    for ( int j = 0; j < result.length; j++ ) {
	    	int cval = 0x10*hexToInt(hex[j*2])+hexToInt(hex[j*2+1]);
	    	result[j] = (byte)cval;
	    }
	    return result;
	}
	
}