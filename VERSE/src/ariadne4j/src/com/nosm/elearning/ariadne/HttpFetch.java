package com.nosm.elearning.ariadne;

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
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


import com.nosm.elearning.ariadne.util.Constants;
import com.nosm.elearning.ariadne.util.ErrorConstants;

public abstract class HttpFetch {
	public static String getString4Url(String apiUrl, ArrayList <NameValuePair> qparams, boolean usePost) throws URISyntaxException, HttpResponseException,IOException, Exception {

		HttpClient httpclient = new DefaultHttpClient();
		StringBuffer sOut = new StringBuffer();

		String pOut = "";
		if (qparams != null) {
			if(!qparams.isEmpty()){
				pOut = URLEncodedUtils.format(qparams, HTTP.UTF_8);
			}
		}

		URI uri = URIUtils.createURI("http", Constants.GAME_HOST3, -1, apiUrl, pOut, null);
		HttpEntityEnclosingRequestBase httpRequest = null;
		HttpResponse response = null;

		if (usePost) {
			HttpPost hPost = new HttpPost(uri);
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(qparams, HTTP.UTF_8);
			//ent.consumeContent();
			hPost.setEntity(ent);
			System.out.println(ErrorConstants.XML_BASIC_PREFIX+" HttpFetch, request method should be post: "
					+ hPost.getMethod() + ": " + ent.getContent().toString()+ErrorConstants.XML_ET);
			response = httpclient.execute(hPost);
		} else {
			HttpGet hGet = new HttpGet(uri);
			System.out.println(ErrorConstants.XML_BASIC_PREFIX+" HttpFetch, request method should be get: "
					+ hGet.getMethod() + ": " + uri.toString() + ", params:"
					+ hGet.getURI().getQuery().toString()+ErrorConstants.XML_ET);
			response = httpclient.execute(hGet);
		}
		if (response.getStatusLine().toString().toLowerCase().indexOf(
				"internal server error") > 0)
			throw new Exception(ErrorConstants.XML_BASIC_PREFIX+" URL " + uri
					+ " returned remote xml service error: "
					+ response.getStatusLine().toString()+ Constants.lineSep+
					response.getParams().toString()+ErrorConstants.XML_ET);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//entity.consumeContent();
			InputStream instream = entity.getContent();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				String s;
				while ((s = reader.readLine()) != null)
					sOut.append(s);
			} catch (IOException ex) {
				throw ex;
			} catch (RuntimeException ex) {
				httpRequest.abort();
				throw ex;
			} finally {
				instream.close();
			}
			httpclient.getConnectionManager().shutdown();
		}
		return sOut.toString();
	}

}