package com.nosm.elearning.ariadne.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;


public abstract class HttpFetch {
	public static String getString4Url(String apiUrl, String paramName, String paramVal) throws URISyntaxException, HttpResponseException, IOException {

		HttpClient httpclient = new DefaultHttpClient();
		String returnedUrl = "";

		//temp
		apiUrl = "tinyurl.com";
		String apiPage = "api-create.php";
		paramName = "url";

		List qparams = new ArrayList();
		qparams.add(new BasicNameValuePair(paramName, paramVal));
		URI uri = URIUtils.createURI("http", apiUrl, -1, "/"+apiPage,
				URLEncodedUtils.format(qparams, "UTF-8"), null);
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);

		//ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//String responseBody = httpclient.execute(httpget, responseHandler);
		//System.out.println(response.getStatusLine());

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				returnedUrl = reader.readLine();
			} catch (IOException ex) {
				throw ex;
			} catch (RuntimeException ex) {
				httpget.abort();
				throw ex;
			} finally {
				instream.close();
			}
			httpclient.getConnectionManager().shutdown();
		}
		return returnedUrl;
	}

}