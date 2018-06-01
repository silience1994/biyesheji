package com.objnetwork.server.testtools;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class testupload {
	public static void test() throws IOException {
		System.err.println("testupload");
		String jst = "{\"contentType\":\"device-new\",\"data\":{\"new-manager\":\"NEW MANAGER NAME\",\"deviceName\":\"device\",\"deviceID\":\"mac\"}}";
		System.err.println(jst);
		
		URL url = new URL("http://localhost:8080/upload");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		
		OutputStream ost = conn.getOutputStream();
		ost.write(jst.getBytes());
		ost.close();
		
		conn.connect();
	}
}
