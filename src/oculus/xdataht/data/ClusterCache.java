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

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import oculus.xdataht.clustering.ClusterResults;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ClusterCache {
	// TODO: Configurability
	static Cache<String, ClusterResults> clusterResultCache = CacheBuilder
			.newBuilder().maximumSize(100)
			.expireAfterAccess(30, TimeUnit.MINUTES)
			.build();
	
	static HashMap<String, HashMap<String,List<String>>> connectivity = new HashMap<String, HashMap<String,List<String>>>();
	
	static public boolean containsResults(String id, String params) {
		ClusterResults result = clusterResultCache.getIfPresent(id);
		if (result == null) {
			return TableDB.getInstance().containsClusterResults(id, params);
		}
		
		return result.getClusterParametersString().equals(params);
	}
	
	static public ClusterResults getResults(String dataset, String id) {
		ClusterResults result = clusterResultCache.getIfPresent(id);
		if (result == null) {
			result = TableDB.getInstance().getClusterResults(dataset, id);
			if (result != null) clusterResultCache.put(id, result);
		}
		return result;
	}
	static public void putResults(final String name, final ClusterResults res) {
		clusterResultCache.put(name, res);
				
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					TableDB.getInstance().putClusterResults(name, res);
				} catch (InterruptedException e) {
					TableDB.getInstance().clearClusterResults(name);
					TableDB.getInstance().close();
				}
			}
		}); 
	}
	
	static public HashMap<String,List<String>> getConnectivity(String key) { 
		return connectivity.get(key); 
	}
	
	static public void putConnectivity(String key, HashMap<String,List<String>> con)  { 
		connectivity.put(key, con); 
	}
	
	public static List<DataRow> getClusterDetails(String dataset, String clustersetName, String clusterId) {
		ClusterResults r = getResults(dataset, clustersetName);
		if (r != null) {
			return r.getClusterDetails(clusterId);
		} else {
			return null;
		}
	}
}
