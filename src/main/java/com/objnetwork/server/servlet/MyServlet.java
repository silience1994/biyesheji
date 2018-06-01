package com.objnetwork.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.objnetwork.server.base.server_core;
import com.objnetwork.server.testtools.*;

@SuppressWarnings("serial")
@WebServlet(name = "test",urlPatterns = "/test")
public class MyServlet extends HttpServlet {  
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
        
    }
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		
//		testjdbc.Test();
        
//		String filename = "E:\\����\\��ҵ���\\objnetwork\\objnetwork\\test.json";
//		System.out.println(filename);
//		try {
//			server_core.Message_Upload_test(filename);
//		} catch (IOException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
		
		server_core.test();
		
//		testupload.test();
	}
  
	@Override  
	public void init() throws ServletException {  
		// TODO Auto-generated method stub  
		super.init();
		try {
			server_core.init();
		} catch (MqttException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    }  
  
      
}  