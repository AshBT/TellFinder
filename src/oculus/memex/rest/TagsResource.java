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
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import oculus.memex.tags.Tags;
import oculus.xdataht.model.StringList;
import oculus.xdataht.model.TagsResult;
import oculus.xdataht.model.UpdateTagRequest;

@Path("/tags")
public class TagsResource {
	
	private HashMap<String, StringList> getTags(Collection<String> adIds, String user_name) {
		HashMap<String, StringList> tagMap =  new HashMap<String, StringList>();
		if (adIds == null || adIds.size() == 0) {
			return tagMap;
		}		
		for (String id : adIds) {
			tagMap.put(id,new StringList(Tags.getTags(id, user_name)));
		}		
		return tagMap;
	}
	
	@POST
	@Path("fetch")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TagsResult fetch(StringList adIds, @Context HttpServletRequest request) {
		return new TagsResult( getTags(adIds.getList(), request.getRemoteUser()) );
	}
	
	@POST
	@Path("update")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void update(UpdateTagRequest tagRequest, @Context HttpServletRequest request) {
		String user_name = request.getRemoteUser();
		ArrayList<String> tags = tagRequest.getTags();
		if (tags != null && tags.size() > 0) {
			if (tagRequest.getAdd() == true) {
				Tags.addTags(tagRequest.getAdIds(), tags, user_name);
			} else {
				Tags.removeTags(tagRequest.getAdIds(), tags, user_name);
			}
		}
	}	
	
	@DELETE
	@Path("resetAllTags")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void resetAllTags(@Context HttpServletRequest request) {;
		Tags.resetAllTags(request.getRemoteUser());
	}
}
