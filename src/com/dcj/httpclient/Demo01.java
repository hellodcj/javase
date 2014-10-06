package com.dcj.httpclient;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class Demo01 {
	public void test() throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://localhost/");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			int l;
			byte[] tmp = new byte[2048];
			while ((l = instream.read(tmp)) != -1) {
			}
		}
		
		HttpGet httpget2 = new HttpGet(
				"http://www.google.com/search?hl=en&q=httpclient&btnG=Google"
				+"Search&aq=f&oq=");
	}
	
	@Test
	public void test02() throws Exception{
		URI uri = URIUtils.createURI("http", "www.google.com", -1, 
				"/search",
				"q=httpclient&btnG=Google+Search&aq=f&oq=", null);
				HttpGet httpget3 = new HttpGet(uri);
				System.out.println(httpget3.getURI()); 
	}
}
