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
package oculus.memex.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import oculus.memex.image.AdImages;
import oculus.memex.util.DataUtil;
import oculus.memex.util.TimeLog;
import oculus.xdataht.data.DataRow;
import oculus.xdataht.model.ClusterDetailsResult;
import oculus.xdataht.model.StringMap;

@Path("/tipAdDetails")
public class TipAdDetailsResource  {
	@Context
	UriInfo _uri;
	
	@GET
	@Path("{type}/{tip}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ClusterDetailsResult handleGet(@PathParam("type") String type, @PathParam("tip") String tip, @Context HttpServletRequest request) {
		TimeLog log = new TimeLog();
		String user_name = request.getRemoteUser();
		log.pushTime("Ad Search tip: " + tip + " for user: " + user_name);
		HashSet<Integer> matchingAds;
		if (type.equals("tip")) {
			matchingAds = GraphResource.fetchMatchingAds(tip, log);
		} else {
			matchingAds = AdImages.getMatchingAds(Integer.parseInt(tip));
		}
		if (matchingAds==null||matchingAds.size()==0) {
			log.popTime();
			return new ClusterDetailsResult(new ArrayList<StringMap>());
		}		
		HashSet<Integer> matches = new HashSet<Integer>();
		for(Integer ad_id: matchingAds) {
			matches.add(ad_id);
		}
		List<DataRow> results = new ArrayList<DataRow>();
		PreclusterDetailsResource.getDetails(log, matches, results, user_name);
		ArrayList<HashMap<String,String>> details = DataUtil.sanitizeHtml(results);

		ArrayList<StringMap> serializableDetails = new ArrayList<StringMap>();
		for (HashMap<String,String> map : details) {
			serializableDetails.add( new StringMap(map));
		}
		log.popTime();
		return new ClusterDetailsResult(serializableDetails);
	}
}