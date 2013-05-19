package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import base.*;

@SuppressWarnings("unchecked")
public class Uploader {
	private DefaultHttpClient httpclient;
	private String[] siteData;

	// private static String siteName,restPath,username,password;

	// public static void main(String[] args) throws Exception {
	// }
	// Singleton with public final field
	private static final Uploader INSTANCE = new Uploader();

	public static Uploader getInstance() {
		return INSTANCE;
	}

	private Uploader() {

	}

	public Uploader fillData() {
		final String[] data = DataHandler.getInstance().data;
		siteData = new String[data.length];
		generateURIelements(data[0], data[1]);
		generateUSERelements(data[2], data[3]);
		// httpclient = new DefaultHttpClient();
		params.println("Starting");
//		startAndLogin();

		// restNodeCreate();

		// restNodeUpdate();
		// restNodeUpdate2();
		// restNodeUpdate3();
		// System.out.println(getJSONvalue(restGetNode(33),"field_img"));
		// params.println(getJSONvalue(getNode(33), "field_imgs"));
		// params.println(getNode(38));
		// params.println(getNodesIndex());
		// params.println(getJSONArray(getNodeIndex())[1]);
		// params.println(fileUpload("C:/ATI/a.jpg"));
		// getNodeTypes();
//		getVocabularyTerms(1);
		return this;
	}

	// ---- HARD CODE

	private JSONObject generateJSON(String[] args, String[] fids) {
		JSONObject j = new JSONObject();
		j.put("type", args[0]);
		j.put("title", args[1]);
		j.put("body", args[2]);
		if (args.length > 3) {
			for (int i = 0; i < fids.length; i++) {
				j.put("field_img", generateJSONArray(fids[i]));
			}
		}
		return j;
	}

	private JSONArray generateJSONArray(String fid) {
		Map<String, String> mapthing = new HashMap<String, String>();
		mapthing.put("fid", "15");
		JSONArray jArrayDude = new JSONArray();
		jArrayDude.add(mapthing);
		return jArrayDude;
	}

	// ---- HARD CODE
	public HashMap<String, String> getNodeTypes() {
		String daString = goPost("drauthr/get_content_types", null, true);
		JSONObject j3 = new JSONObject();
		j3 = (JSONObject) JSONValue.parse(daString);
		return j3;
	}

	public String [] getFieldsOfType(String type) {
		JSONObject j = new JSONObject();
		j.put("name", type);
//		JSONObject fields = getJSONObject(getJSONValue(goPost("drauthr/get_content_type_fields", j, true),"fields"));
//		System.out.println("loller " + fields.size());
		/*
		 * Here the problem is whether we should separate the fields here or 
		 * in the drupal module. If I understand the RESTful practices, it 
		 * should be the module.
		 */
		String result = goPost("drauthr/get_content_type_fields", j, true);
		if(!result.equals("[null]")) return getJSONArray(result);
		else return null;
	}
	public String getTypeDescription(String type) {
		JSONObject j = new JSONObject();
		j.put("name", type);
		return goPost("drauthr/get_content_type_description", j, true);
	}
	public String[] getVocabularyTerms(int vid) {
		JSONObject j = new JSONObject();
		j.put("vid", vid);
		System.out.println(goPost("taxonomy_vocabulary/getTree", j, true));
		return new String[] { "", "" };
	}

	public void uploadNode(String[] args, String[] fids) {
		startAndLogin();
		goPost("node", generateJSON(args, fids), false);
		closeConnection();
	}

	public Uploader startAndLogin() {
		httpclient = new DefaultHttpClient();

		JSONObject j = new JSONObject();
		j.put("username", siteData[2]);
		j.put("password", siteData[3]);
		goPost("user/login", j, false);
		return this;
	}

	public String getNode(int node) {
		return goGet("node/" + node);
	}

	public String getNodeIndex() {
		// return goGet("node?fields=nid");
		// return goGet("node?page=1");
		return goGet("node?fields=nid&pagesize=50");
	}
	public String fileUpload(String theFile) {
		String fid = "-1";
		String image64 = ImageToBase64.toBase64(theFile);

		JSONObject j = new JSONObject();
		j.put("file", "" + image64);
		j.put("filepath", "sites/default/files/img.png");
		String temp = goPost("file", j, true);
		System.out.println(temp);
		fid = getJSONValue(temp, "fid");

		return fid;
	}

	private void restNodeUpdate() {

		JSONObject j = new JSONObject();
		j.put("type", "story");
		// note to self: it appears that all its fields
		// are updated no matter what...
		Map<String, String> mapthing = new HashMap<String, String>();
		mapthing.put("fid", "15");
		JSONArray jArrayDude = new JSONArray();
		jArrayDude.add(mapthing);
		j.put("field_img", jArrayDude);
		goPut("node/33", j, false);
	}

	private void restNodeUpdate2() {

		JSONObject j = new JSONObject();
		j.put("type", "story");
		Map<String, String> mapthing = new HashMap<String, String>();
		mapthing.put("value", "dudeerrererer");
		JSONArray jArrayDude = new JSONArray();
		jArrayDude.add(mapthing);
		j.put("field_text", jArrayDude);
		goPut("node/33", j, false);
	}

	private void restNodeUpdate3() {
		JSONObject j = new JSONObject();
		j.put("type", "story");
		// note to self: it appears that all its fields
		// are updated no matter what...
		Map<String, String> mapthing = new HashMap<String, String>();
		mapthing.put("fid", "22");
		JSONArray jArrayDude = new JSONArray();
		jArrayDude.add(mapthing);
		jArrayDude.add(mapthing);
		j.put("field_imgs", jArrayDude);

		goPut("node/33", j, false);

	}

	public String readResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String daString = "";
		try {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream));
			daString = reader.readLine();

			// System.out.println("j3 test: " + getJSONvalue(daString,"nid"));

		} catch (IOException ex) {
			// In case of an IOException the connection will be released
			// back to the connection manager automatically
			// throw ex;
		}
		return daString;
	}

	public String getJSONValue(String daString, String theKey) {
		String result = "";
		JSONObject j3 = new JSONObject();
		j3 = (JSONObject) JSONValue.parse(daString);
		result = j3.get(theKey).toString();
		return result;
	}

	public JSONObject getJSONObject(String daString) {
		return (JSONObject) JSONValue.parse(daString);
	}

	public String[] getJSONArray(String daString) {
		String[] result;
		JSONArray j3 = new JSONArray();
		j3 = (JSONArray) JSONValue.parse(daString);
		result = new String[j3.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = j3.get(i).toString();
		}
		return result;
	}

	public void closeConnection() {
		params.println("Finished");
		httpclient.getConnectionManager().shutdown();
	}

	public void generateURIelements(String sN, String rP) {
		// do error correction
		siteData[0] = sN;
		siteData[1] = rP;
	}

	public void generateUSERelements(String user, String pass) {
		// do error correction
		siteData[2] = user;
		siteData[3] = pass;
	}

	public String compileURI(String restService) {
		return siteData[0] + siteData[1] + restService;
	}

	private String goGet(String resource) {
		String daResponse = "";
		try {
			HttpGet request = new HttpGet(compileURI(resource));
			HttpResponse response = httpclient.execute(request);
			daResponse = readResponse(response);
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
		return daResponse;
	}

	private String goPost(String resource, JSONObject j, boolean respond) {
		// HttpPost request = new HttpPost(compileURI(resource));
		// if(args.length<3) return;
		String daResponse = "";
		try {

			HttpPost request = new HttpPost(compileURI(resource));
			if (j != null) {
				StringEntity params = new StringEntity(j.toString());
				request.addHeader("content-type", "application/json");
				request.setEntity(params);
			}
			if (respond) {
				HttpResponse response = httpclient.execute(request);
				System.out.println(response);
				daResponse = readResponse(response);
			} else {
				httpclient.execute(request);
			}
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
		return daResponse;
	}

	private String goPut(String resource, JSONObject j, boolean respond) {
		String daResponse = "";
		try {

			HttpPost request = new HttpPost(compileURI(resource));

			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			if (respond) {
				HttpResponse response = httpclient.execute(request);
				daResponse = readResponse(response);
			} else {
				httpclient.execute(request);
			}
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
		return daResponse;
	}

	private String goPostToString(String resource, JSONObject j, boolean respond) {
		// HttpPost request = new HttpPost(compileURI(resource));
		// if(args.length<3) return;
		String daResponse = "";
		try {

			HttpPost request = new HttpPost(compileURI(resource));
			if (j != null) {
				StringEntity params = new StringEntity(j.toString());
				request.addHeader("content-type", "application/json");
				request.setEntity(params);
			}
			if (respond) {
				HttpResponse response = httpclient.execute(request);
				System.out.println(response);
				daResponse = readResponse(response);
			} else {
				httpclient.execute(request);
			}
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
		return daResponse;
	}
}
