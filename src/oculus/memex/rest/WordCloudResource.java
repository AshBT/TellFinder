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
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oculus.xdataht.data.WordCloud;
import oculus.xdataht.model.WordCloudRequest;
import oculus.xdataht.model.WordCloudResult;
import oculus.xdataht.model.WordCount;

import org.restlet.resource.ResourceException;

@Path("/wordCloud")
public class WordCloudResource {
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public WordCloudResult handlePost(WordCloudRequest wcr) throws ResourceException {
		WordCloudResult result = new WordCloudResult();
		
		int imageWidth = wcr.getWidth();
		int imageHeight = wcr.getHeight();
		ArrayList<WordCount> wordCounts = wcr.getWordCounts();
		if (imageWidth==0||imageHeight==0||wordCounts.size()==0) {
			result.setId("blank");
			return result;
		}
		
		Map<String,Integer> wordMap = new HashMap<String,Integer>();
		for (WordCount wc : wordCounts) {
			String word = wc.getWord();
			int count = wc.getCount();
			wordMap.put(word, count);
		}
		

		if (wordMap.size() == 0) {
			wordMap.put("No Text", 1);
		}
		
		// If we don't have the image, generate it
		StringBuffer bigStr = new StringBuffer("");
		for (String word:wordMap.keySet()) {
			for (int i = 0; i < wordMap.get(word); i++) {
				bigStr.append(word);
			}
		}
		String imgID = "" + bigStr.toString().hashCode();
		String resizedID = imgID + "_" + imageWidth + "x" + imageHeight;
		
		byte[] imgBytes = ImageResource.getImage(resizedID);
		if (imgBytes == null) {
			try {
				imgBytes = WordCloud.generateWordCloud(wordMap, imageWidth, imageHeight);

				// Store the image on the server
				ImageResource.addImage(imgBytes, resizedID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Return a link to the image servers
		result.setId(resizedID);
		return result;
	}
}
