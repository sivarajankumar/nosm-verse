package com.nosm.elearning.ariadne;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.*;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.*;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;
import com.nosm.elearning.ariadne.constants.*;
import com.nosm.elearning.ariadne.model.game.uri.GameUri;


public abstract class HttpFetch {

	public static String getString4Url(GameUri uri, boolean usePost) throws URISyntaxException, UnsupportedEncodingException, HttpResponseException,IOException, Exception {
		return getString4Url( uri.getAddress(), null, usePost);
	}

	public static String getString4Url(String apiUrl, ArrayList <NameValuePair> qparams, boolean usePost) throws URISyntaxException, UnsupportedEncodingException, HttpResponseException,IOException, Exception {
		StringBuffer sOut = new StringBuffer();
		String pOut = "";

		if (apiUrl.indexOf("?") > -1){
			StringTokenizer qT = new StringTokenizer(apiUrl,"?");
				apiUrl = qT.nextToken();
				pOut = qT.nextToken();

		}

		if (qparams != null) {
			if(!qparams.isEmpty()){
				pOut = URLEncodedUtils.format(qparams, HTTP.UTF_8);
			}
		}

		URI uri = null;
		if (apiUrl.indexOf("http") == 0){
			apiUrl = apiUrl.substring(apiUrl.indexOf("://")+ "://".length());
			StringTokenizer uT = new StringTokenizer(apiUrl,"/");
			String host = uT.nextToken();
			uri = URIUtils.createURI("http", host, -1, apiUrl.substring(
					apiUrl.indexOf(host)+ host.length()), pOut, null);
		}else{
			uri = URIUtils.createURI("http", C.GAME_HOST, -1, apiUrl, pOut, null);
		}

		//HttpEntityEnclosingRequestBase httpRequest = null;
		HttpResponse response = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		//HttpConnectionParams.setConnectionTimeout(new DefaultHttpParams(), SOCKET_TIMEOUT);
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
        CookieStore cookieStore = new BasicCookieStore();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		if (usePost) {
			HttpPost hPost = new HttpPost(uri);
/*			//HttpPost hPost = new HttpPost("http://"+C.GAME_HOST3+apiUrl);
			hPost.addHeader("User-Agent", "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8");

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(qparams, HTTP.UTF_8);
			hPost.setEntity(ent);
			System.out.println(EC.XML_BASIC_PREFIX+" HttpFetch, request method should be post: "
					+ hPost.getMethod() + ": " + hPost.getURI().getQuery().toString()+EC.XML_ET);
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
*/

	        System.out.println("Initial set of cookies:");
	        List<Cookie> cookies = cookieStore.getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }

	        //httpclient.getCookieStore().addCookie(new BasicClientCookie("session", getParameter("session")));
			//httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);

         	hPost.setHeader("User-Agent", XC.POST_HEADER_USER_AGENT);
         	hPost.setHeader("Accept", XC.POST_HEADER_ACCEPT);
         	hPost.setHeader("Content-Type", XC.POST_HEADER_CONTENT_TYPE);


           	HttpEntity tmp = null;
           	try {
           		tmp = new UrlEncodedFormEntity(qparams, HTTP.UTF_8);
           		if(tmp == null ){
           			tmp = new StringEntity(pOut,"UTF-8");
           		}
    			hPost.setEntity(tmp);
           	} catch (UnsupportedEncodingException e) {
           		System.out.println("HTTPFetch : UnsupportedEncodingException : "+e);
           	}

           	hPost.setEntity(tmp);
			response = httpclient.execute(hPost, localContext);
		} else {
			HttpGet hGet = new HttpGet(uri);
			System.out.println(EC.XML_BASIC_PREFIX+" HttpFetch, request method should be get: "
					+ hGet.getMethod() + ": " + uri.toString() + ", params:"
					+ hGet.getURI().getQuery().toString()+EC.XML_ET);
			response = httpclient.execute(hGet, localContext);
		}

        int statusCode = response.getStatusLine().getStatusCode();

        if ( statusCode >  307){
            if ( statusCode > 499 && statusCode <  600){
            	String eStr = " returned general remote xml service error for URI " + uri.toString();
    			if (response.getStatusLine().toString().toLowerCase().indexOf(
    					"internal server error") > 0){
    				eStr = " returned probable remote xml service ssid error for URI " + uri.toString();
    			}
    				throw new Exception(EC.XML_BASIC_PREFIX+" URL " + uri
    						+ eStr
    						+ response.getStatusLine().toString()+ C.br+
    						response.getParams().toString()+EC.XML_ET);
            }else{
				System.out.println("HTTPFetch Remote error: "+response.getStatusLine().toString() +" for URI " + uri.toString());
				throw new Exception("HTTPFetch Remote error: "+response.getStatusLine().toString()+" for URI " + uri.toString());
            }
        }


		HttpEntity entity = response.getEntity();
		if (entity != null) {
	        sOut.append(EntityUtils.toString(entity)); // key line

/*			InputStream instream = entity.getContent();
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
*/
            System.out.println("Post connection cookies:");
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None!");
            } else {
            	Iterator i = cookies.iterator();
        		while (i.hasNext()) {
                    System.out.println(" - " + ((Cookie)i.next()).toString());
                }
            }
			httpclient.getConnectionManager().shutdown();
		}
		return sOut.toString();
	}

}