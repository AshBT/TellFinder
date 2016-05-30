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
package oculus.memex.training;

import java.util.HashSet;

import oculus.memex.clustering.Cluster;
import oculus.memex.clustering.ClusterAttributes;
import oculus.memex.clustering.ClusterDetails;
import oculus.memex.db.MemexOculusDB;
import oculus.memex.graph.AttributeLinks;
import oculus.memex.graph.ClusterLinks;
import oculus.memex.util.StringUtil;
import oculus.memex.util.TimeLog;

import org.restlet.resource.ResourceException;

public class RenameAttribute {
	public static long renameValueStatic(String attribute, String oldValue, String newValue) throws ResourceException {
		TimeLog tl = new TimeLog();
		tl.pushTime("Rename " + attribute + " " + oldValue + " to " + newValue);
		tl.pushTime("Attribute updates");

		String tableName;
		if (attribute.compareToIgnoreCase("website")==0) {
			tableName = "ads_websites";
		} else if (attribute.compareToIgnoreCase("email")==0) {
			tableName = "ads_emails";
		} else {
			tableName = "ads_phones";
		}
		
		// Fetch ads with the attribute
		// Rename in ads_phones, ads_email, ads_website
		HashSet<Integer> oldMatchingAds;  // ads that match the oldValue
		HashSet<Integer> newMatchingAds;  // ads that match the newValue
		oldMatchingAds = MemexOculusDB.getValueAds(tableName, oldValue, true);
		newMatchingAds = MemexOculusDB.getValueAds(tableName, newValue, true);

		HashSet<Integer> overlappingAds = new HashSet<Integer>();
		for (Integer val:oldMatchingAds) if (newMatchingAds.contains(val)) overlappingAds.add(val);
		
		if (overlappingAds.size()>0) MemexOculusDB.deleteValueAdsById(tableName, oldValue, StringUtil.hashSetToSqlList(overlappingAds));
		MemexOculusDB.renameValueAds(tableName, oldValue, newValue);
		System.out.println("Deleted " + overlappingAds.size() + " attributes. Renamed " + (oldMatchingAds.size()-overlappingAds.size()));
		
		ClusterAttributes.renameValueAds(attribute,oldValue,newValue);
		tl.popTime();

		ClusterAttributes attributeToClusters = new ClusterAttributes();

		// Recluster
		oldMatchingAds.addAll(newMatchingAds);
		tl.pushTime("Update clusters " + oldMatchingAds.size() + " ads");
		HashSet<Integer> clusterids = Cluster.recalculateClustersForAds(oldMatchingAds, attributeToClusters, tl);
		System.out.println("Altering clusters: " + StringUtil.hashSetToSqlList(clusterids));
		tl.popTime();
		
		// Calculate cluster details, cluster locations, cluster links for affected clusters
		tl.pushTime("Update details " + clusterids.size() + " clusters " + StringUtil.hashSetToSqlList(clusterids));
		ClusterDetails.updateDetails(tl, clusterids);
		tl.popTime();
		tl.pushTime("Update links");
		ClusterLinks.computeLinks(clusterids, attributeToClusters);
		tl.popTime();

		AttributeLinks.renameAttribute(tl, attribute,oldValue,newValue);
		
		// TODO: add to rename_phones, rename_websites, rename_emails and handle in future ads
		return tl.popTime();
	}
	
	public static void main(String[] args) {
//		renameValueStatic("phone", "62634498933", "6263449893");
		renameValueStatic("phone", "16263449893", "6263449893");
//		renameValueStatic("phone", "62634498932", "6263449893");
//		renameValueStatic("phone", "62634498934", "6263449893");
//		renameValueStatic("phone", "62634498934777", "6263449893");
//		renameValueStatic("phone", "16265862716", "6265862716");
//		renameValueStatic("phone", "62658627161", "6265862716");
	}

}
