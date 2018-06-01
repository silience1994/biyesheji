package com.objnetwork.server.tool;

import java.io.*;

public class tools {
	public static String file2string (String Filename) throws IOException {
		FileInputStream ifs = new FileInputStream(Filename);
		BufferedReader  ifsr = new BufferedReader (new InputStreamReader(ifs));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line=ifsr.readLine())!=null) {
			buffer.append(line);
		}
		ifsr.close();
		return buffer.toString();
	}

	public static String managername2topic(String st) {
		return "Manager/"+st;
	}
}