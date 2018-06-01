package com.objnetwork.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.objnetwork.server.base.server_core;
import org.springframework.stereotype.Controller;

@Controller
public class ServletUpload extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.err.println("ServletUpload.doPost");
        request.setCharacterEncoding("UTF-8");
        InputStream is = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String st = "";
		while (br.ready()) st = st+br.readLine();
		System.out.println(st);        
        
        JSONObject json = new JSONObject(st);
        server_core.Message_Upload(json);
    }
	
	@Override
	public void init() {
		
	}
}
