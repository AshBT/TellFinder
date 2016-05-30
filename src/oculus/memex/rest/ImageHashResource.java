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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import oculus.memex.db.MemexOculusDB;
import oculus.memex.image.AdImages;
import oculus.memex.image.ImageHistogramHash;
import oculus.memex.util.StringUtil;
import oculus.memex.util.TimeLog;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

@Path("/imagehash")
public class ImageHashResource {
	private static HashMap<Integer, byte[]> THUMBNAIL_MAP = new HashMap<Integer, byte[]>();
	
	@GET
	@Path("bin/{bin}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getBin(@PathParam("bin") String bin) {
		JSONObject result = new JSONObject();
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		Statement stmt = null;
		try {
			JSONArray images = new JSONArray();
			result.put("images", images);
			stmt = conn.createStatement();
			String sqlStr = "SELECT * FROM " + ImageHistogramHash.IMAGE_HISTOGRAM_TABLE + 
					" inner join images_hash on imagehash_details.hash=images_hash.hash" + 
					" inner join memex_ht.images on images_id=images.id" +
					" where bin=" + bin;
			ResultSet rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				String sha1 = rs.getString("hash");
				String histogram = rs.getString("histogram");
				String location = rs.getString("location");
				int width = rs.getInt("width");
				int height = rs.getInt("height");
				JSONObject image = new JSONObject();
				image.put("sha1", sha1);
				image.put("histogram", histogram);
				image.put("url", location);
				image.put("width", width);
				image.put("height", height);
				images.put(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace();	}
		}
		db.close(conn);
		return result.toString();
	}

	@POST
	@Path("exemplars")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getExemplars(String bins) {
		JSONObject result = new JSONObject();
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		Statement stmt = null;
		try {

			HashSet<String> binSet = new HashSet<String>();
			JSONArray binsArr = new JSONArray(bins);
			for(int i = 0; i<binsArr.length();i++) {
				binSet.add(binsArr.getString(i));
			}

			stmt = conn.createStatement();
			String sqlStr = "SELECT * FROM " + ImageHistogramHash.IMAGE_HISTOGRAM_TABLE +
					" inner join images_hash on imagehash_details.hash=images_hash.hash" +
					" inner join memex_ht.images on images_id=images.id" +
					" where bin IN " + StringUtil.hashSetStringToSqlList(binSet) + " GROUP BY bin";

			ResultSet rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				String sha1 = rs.getString("hash");
				String histogram = rs.getString("histogram");
				String location = rs.getString("location");
				int width = rs.getInt("width");
				int height = rs.getInt("height");
				JSONObject image = new JSONObject();
				image.put("sha1", sha1);
				image.put("histogram", histogram);
				image.put("url", location);
				image.put("width", width);
				image.put("height", height);
				result.put(rs.getString("bin"),image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace();	}
		}
		db.close(conn);
		return result.toString();
	}
	
	@GET
	@Path("exemplar/{bin}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getExemplar(@PathParam("bin") String bin) {
		JSONObject result = new JSONObject();
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		Statement stmt = null;
		try {
			JSONArray images = new JSONArray();
			result.put("images", images);
			stmt = conn.createStatement();
			String sqlStr = "SELECT * FROM " + ImageHistogramHash.IMAGE_HISTOGRAM_TABLE + 
					" inner join images_hash on imagehash_details.hash=images_hash.hash" + 
					" inner join memex_ht.images on images_id=images.id" +
					" where bin=" + bin + " limit 1";
			
			ResultSet rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				String sha1 = rs.getString("hash");
				String histogram = rs.getString("histogram");
				String location = rs.getString("location");
				int width = rs.getInt("width");
				int height = rs.getInt("height");
				JSONObject image = new JSONObject();
				image.put("sha1", sha1);
				image.put("histogram", histogram);
				image.put("url", location);
				image.put("width", width);
				image.put("height", height);
				images.put(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace();	}
		}
		db.close(conn);
		return result.toString();
	}
	
	@GET
	@Path("list")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getList() {
		TimeLog tl = new TimeLog();
		tl.pushTime("Fetching Image Bins");
		JSONObject result = new JSONObject();
		MemexOculusDB db = MemexOculusDB.getInstance();
		Connection conn = db.open();
		Statement stmt = null;
		try {
			JSONArray bins = new JSONArray();
			result.put("bins", bins);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT distinct(bin),count(bin) as count " +
					"FROM memex_oculus.imagehash_details group by bin order by count DESC limit 0,2000");
			while (rs.next()) {
				String bin = rs.getString("bin");
				int count = rs.getInt("count");
				JSONObject binObj = new JSONObject();
				binObj.put("bin", bin);
				binObj.put("count", count);
				bins.put(binObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (stmt != null) stmt.close();
			} catch (SQLException e) { e.printStackTrace();	}
		}
		db.close(conn);
		tl.popTime();
		return result.toString();
	}
	
	@GET
	@Path("thumbnail/{id}")
	@Produces("image/png")
	public Response get(@PathParam("id") Integer imageId) { 
		try {
			byte[] rawImage = THUMBNAIL_MAP.get(imageId);
			if (rawImage==null) {
				String url = AdImages.getLocation(imageId);
				BufferedImage image = ImageIO.read(new URL(url));
				BufferedImage resizedImage = new BufferedImage(40, 40, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D g = resizedImage.createGraphics();
			    g.setComposite(AlphaComposite.Src);
			    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.drawImage(image, 0, 0, 40, 40, null);
				g.dispose();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(resizedImage, "png", baos);
				rawImage = baos.toByteArray();
				THUMBNAIL_MAP.put(imageId, rawImage);
			}
			if (rawImage!=null) {
				return Response.ok(new ByteArrayInputStream(rawImage)).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(new ByteArrayInputStream(ImageResource.BLANK_IMG)).build();
	}
	
	
}