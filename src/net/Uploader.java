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
	public Uploader(final String[] data) {
		siteData = new String[data.length];
		generateURIelements(data[0], data[1]);
		generateUSERelements(data[2], data[3]);
		// httpclient = new DefaultHttpClient();
		params.println("Starting");
		startAndLogin();

		// restNodeCreate();

		// restNodeUpdate();
		// restNodeUpdate2();
		// restNodeUpdate3();
		// System.out.println(getJSONvalue(restGetNode(33),"field_img"));
		params.println(getJSONvalue(getNode(33), "field_imgs"));
	}

	public void closeConnection() {
		params.println("Finished");
		httpclient.getConnectionManager().shutdown();
	}

	public void uploadNode(String[] args,String [] fids) {
		startAndLogin();
		nodeCreate(generateJSON(args,fids));
		closeConnection();
	}

	private JSONObject generateJSON(String[] args,String [] fids) {
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

	// public static void main(String[] args) throws Exception {
	// // for example:
	//
	//
	//
	//
	//
	//
	//
	//
	// }
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

	private void startAndLogin() {
		httpclient = new DefaultHttpClient();
		try {
			HttpPost request = new HttpPost(compileURI("user/login"));
			JSONObject j = new JSONObject();
			j.put("username", siteData[2]);
			j.put("password", siteData[3]);
			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);

			base.params.println(response);
			base.params.println("loller re");
			request.releaseConnection();
			// if(response != null) {
		} catch (Exception ex) {
			// handle exception here
		} finally {

			// httpclient.getConnectionManager().shutdown();
		}
	}

	private String getNode(int node) {
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
				// System.out.println("node 27 " + daString);
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

	public String fileUpload(String theFile) {
		String fid = "-1";
		String image64 = ImageToBase64.toBase64(theFile);
		try {
			HttpPost request = new HttpPost(compileURI("file"));
			JSONObject j = new JSONObject();
			j.put("file", "" + image64);
			j.put("filepath", "sites/default/files/img.png");
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
				base.params.println(daString);

			} catch (IOException ex) {

				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw ex;
			}
		} catch (Exception ex) {
			// handle exception here
		} finally {

		}
		return fid;
	}

	private void nodeCreate(JSONObject j) {
		// if(args.length<3) return;
		try {

			HttpPost request = new HttpPost(compileURI("node"));

			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			base.params.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}

	private void restNodeUpdate() {
		try {

			HttpPut request = new HttpPut(compileURI("node/33"));
			JSONObject j = new JSONObject();
			j.put("type", "story");
			// note to self: it appears that all its fields
			// are updated no matter what...
			Map<String, String> mapthing = new HashMap<String, String>();
			mapthing.put("fid", "15");
			JSONArray jArrayDude = new JSONArray();
			jArrayDude.add(mapthing);
			j.put("field_img", jArrayDude);

			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			base.params.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}

	private void restNodeUpdate2() {
		try {

			HttpPut request = new HttpPut(compileURI("node/33"));
			JSONObject j = new JSONObject();
			j.put("type", "story");
			Map<String, String> mapthing = new HashMap<String, String>();
			mapthing.put("value", "dudeerrererer");
			JSONArray jArrayDude = new JSONArray();
			jArrayDude.add(mapthing);
			j.put("field_text", jArrayDude);
			base.params.println(j.toString());
			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			base.params.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}

	private void restNodeUpdate3() {
		try {

			HttpPut request = new HttpPut(compileURI("node/33"));
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

			StringEntity params = new StringEntity(j.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpclient.execute(request);
			base.params.println(readResponse(response));
			request.releaseConnection();
		} catch (Exception ex) {
			// handle exception here
		} finally {
			// httpclient.getConnectionManager().shutdown();
		}
	}

	public String readResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String daString = "";
		try {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream));
			// do something useful with the response
			daString = reader.readLine();

			// System.out.println("j3 test: " + getJSONvalue(daString,"nid"));

		} catch (IOException ex) {

			// In case of an IOException the connection will be released
			// back to the connection manager automatically
			// throw ex;
		}
		return daString;
	}

	public String getJSONvalue(String daString, String theKey) {
		String result = "";
		JSONObject j3 = new JSONObject();
		j3 = (JSONObject) JSONValue.parse(daString);
		result = j3.get(theKey).toString();
		return result;
	}
}
