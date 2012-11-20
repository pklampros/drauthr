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

public class apache {
	private static DefaultHttpClient httpclient;
	private static String siteName,restPath,username,password;

	public static void main(String[] args) throws Exception {
		// for example: 
		// generateURIelements("http://www.mySite.com/services/", "rest_path/");
		// generateUSERelements("username","pass");
		
		httpclient = new DefaultHttpClient();
		
		System.out.println("Starting");
		restLogin();
		//restNodeCreate();

//		restNodeUpdate();
//		restNodeUpdate2();
		restNodeUpdate3();
//		System.out.println(getJSONvalue(restGetNode(33),"field_img"));
		System.out.println(getJSONvalue(restGetNode(33),"field_imgs"));
		//restFileUpload("C:/st/cap.png");
		httpclient.getConnectionManager().shutdown();

		System.out.println("Finished");
	}
	public static void generateURIelements(String sN, String rP) {
		siteName = sN;
		restPath = rP;
	}
	public static void generateUSERelements(String user, String pass){
		username = user;
		password = pass;
	}
	public static String compileURI(String restService) {
		return siteName + restPath + restService;
	}

	private static void restLogin() {
		try {
			HttpPost request = new HttpPost(compileURI("user/login"));
			JSONObject j = new JSONObject();
			j.put("username", username);
			j.put("password", password);
			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);

			System.out.println(response);
			System.out.println("loller re");
			request.releaseConnection();
			// if(response != null) {
		} catch (Exception ex) {
			// handle exception here
		} finally {

			// httpclient.getConnectionManager().shutdown();
		}
	}

	private static String restGetNode(int node) {
		String daString = "";
		try {

			HttpGet request = new HttpGet(compileURI("node/" + node));
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				// do something useful with the response
				daString = reader.readLine();
//				System.out.println("node 27 " + daString);
			} catch (IOException ex) {

				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw ex;
			}
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
		return daString;
	}

	private static void restFileUpload(String theFile) {
		String image64 = ImageToBase64.toBase64(theFile);
		try {
			HttpPost request = new HttpPost(compileURI("file"));
			JSONObject j = new JSONObject();
			j.put("file", "" + image64);
			j.put("filepath", "sites/default/files/imglol.png");
			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			InputStream instream = entity.getContent();
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				// do something useful with the response
				String daString = reader.readLine();
				System.out.println(daString);

			} catch (IOException ex) {

				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw ex;
			}
		} catch (Exception ex) {
			// handle exception here
		} finally {

		}
	}

	
	private static void restNodeCreate() {
		try {

			HttpPost request = new HttpPost(compileURI("node"));
			JSONObject j = new JSONObject();
			j.put("type", "story");
			j.put("title", "testnode2");
			j.put("body", "dude!!!!!");
		
			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			System.out.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}
	private static void restNodeUpdate() {
		try {

			HttpPut request = new HttpPut(compileURI("node/33"));
			JSONObject j = new JSONObject();
			j.put("type", "story");
			// note to self: it appears that all its fields 
			// are updated no matter what...
			Map<String,String> mapthing = new HashMap<String,String>();
			mapthing.put("fid", "15");
			JSONArray jArrayDude = new JSONArray();
			jArrayDude.add(mapthing);
			j.put("field_img", jArrayDude);

			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			System.out.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}
	private static void restNodeUpdate2() {
		try {

			HttpPut request = new HttpPut(compileURI("node/33"));
			JSONObject j = new JSONObject();
			j.put("type", "story");
			Map<String,String> mapthing = new HashMap<String,String>();
			mapthing.put("value","dudeerrererer");
			JSONArray jArrayDude = new JSONArray();
			jArrayDude.add(mapthing);
			j.put("field_text", jArrayDude);
			System.out.println(j.toString());
			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			System.out.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}
	private static void restNodeUpdate3() {
		try {

			HttpPut request = new HttpPut(compileURI("node/33"));
			JSONObject j = new JSONObject();
			j.put("type", "story");
			// note to self: it appears that all its fields 
			// are updated no matter what...
			Map<String,String> mapthing = new HashMap<String,String>();
			mapthing.put("fid", "22");
			JSONArray jArrayDude = new JSONArray();
			jArrayDude.add(mapthing);
			jArrayDude.add(mapthing);
			j.put("field_imgs", jArrayDude);

			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			System.out.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}
	// private static String [] breakResponse(String response){
	//
	// }
	public static String readResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String daString = "";
		try {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			// do something useful with the response
			daString = reader.readLine();

			//System.out.println("j3 test: " + getJSONvalue(daString,"nid"));

		} catch (IOException ex) {

			// In case of an IOException the connection will be released
			// back to the connection manager automatically
			//throw ex;
		}
		return daString;
	}
	public static String getJSONvalue(String daString, String theKey) {
		String result = "";
			JSONObject j3 = new JSONObject();
			j3 = (JSONObject) JSONValue.parse(daString);
			result = j3.get(theKey).toString();
		return result;
	}
}
