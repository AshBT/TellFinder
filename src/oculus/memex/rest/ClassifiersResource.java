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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oculus.memex.concepts.AdKeywords;
import oculus.memex.concepts.Keywords;
import oculus.xdataht.model.ClassifiersResult;
import oculus.xdataht.model.UpdateKeywordsRequest;

@Path("/classifiers")
public class ClassifiersResource {
	
	@GET @Path("fetch")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public static ClassifiersResult getKewords() {
		ClassifiersResult classifiers = new ClassifiersResult(Keywords.getKeywords());
		return classifiers;
	}
	
	@POST @Path("update")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static void addKeywords(UpdateKeywordsRequest keywordsRequest) {
		if (!keywordsRequest.getKeywords().isEmpty()) {
			Keywords.updateKeywords(keywordsRequest.getKeywords(), true);
		}
	}
	
	@DELETE @Path("update")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static void removeKeywords(UpdateKeywordsRequest keywordsRequest) {
		if (!keywordsRequest.getKeywords().isEmpty()) {
			Keywords.updateKeywords(keywordsRequest.getKeywords(), false);
		}
	}
	
	@GET @Path("classify")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	/** Trigger re-classification of ads based on the current set of classifiers and keywords. */
	public static void classify() {
		AdKeywords.extractKeywords(true);		
	}
	
	@GET @Path("progress")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static String getProgress() {
		String result = "{ \"percentComplete\" : " + AdKeywords.getPercentComplete() + "}";
		return result;
	}
}
