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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/image")
public class ImageResource {
	static byte[] BLANK_IMG = {};
	static {
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D gb = bi.createGraphics();
        gb.setBackground(Color.WHITE);
        gb.clearRect(0, 0, 1, 1);
    	// Output the image to a byte array
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	try {
	    	ImageIO.write( bi, "png", baos );
	    	baos.flush();
	    	BLANK_IMG = baos.toByteArray();
	    	baos.close();
    	} catch (Exception e) {
    		System.out.println("**ERROR: Failed to write blank image: " + e.getMessage());
		}
	}
	
	private static Object _lock = new Object();
	
	private static HashMap<String,byte[]> IMAGE_MAP = new HashMap<String,byte[]>();

	
	public static byte[] getImage(String imgID) {
		return IMAGE_MAP.get(imgID);
	}
	
	public static void addImage(byte[] img, String imgID) {		
		synchronized (_lock) {
			IMAGE_MAP.put(imgID, img);
		}
	}
	
	@GET
	@Path("{id}")
	@Produces("image/png")
	public Response get(@PathParam("id") String imageId) { 
		try {
			byte[] rawImage = IMAGE_MAP.get(imageId);
			if (rawImage!=null) {
				return Response.ok(new ByteArrayInputStream(rawImage)).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(new ByteArrayInputStream(BLANK_IMG)).build();
	}
	
	
}