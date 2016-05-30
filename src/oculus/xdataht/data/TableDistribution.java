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

import oculus.xdataht.model.Distribution;

public class TableDistribution {
	public HashMap<String, Integer> distribution;
	public String maxKey;
	public String minKey;
	
	public TableDistribution() { 
		distribution = new HashMap<String, Integer>();
	}
	
	public void increment(String key) {
		Integer count = distribution.get(key);
		if (count == null) {
			count = new Integer(0);
		}
		count++;
		distribution.put(key,  count);
		
		if (maxKey == null) {
			maxKey = key;
		} else {
			if (distribution.get(key) > distribution.get(maxKey)) {
				maxKey = key;
			}
		}
		
		if (minKey == null) {
			minKey = key;
		} else {
			if (distribution.get(key) < distribution.get(minKey)) {
				minKey = key;
			}
		}
	}
	
	public static ArrayList<Distribution> getDistribution(String tableName, String columnName) {
		TableDistribution td = TableDB.getInstance().getValueCounts(tableName, columnName);
		
		// Get counts based on cluster size
		HashMap<Integer, Integer> clusterDistrubution = new HashMap<Integer, Integer>();
		for (String key : td.distribution.keySet()) {
			if ((key==null) || key.equalsIgnoreCase("null")) continue;
			int valueCount = td.distribution.get(key);
			Integer clusterCount = clusterDistrubution.get(valueCount);
			if (clusterCount == null) {
				clusterCount = new Integer(0);
			}
			clusterCount++;
			clusterDistrubution.put(valueCount, clusterCount);
		}
		
		ArrayList<Distribution> dist = new ArrayList<Distribution>();
		for (Integer key : clusterDistrubution.keySet()) {
			Integer count = clusterDistrubution.get(key);
			Distribution distObject = new Distribution(key, count);
			dist.add(distObject);
		}
		
		return dist;
	}

}
