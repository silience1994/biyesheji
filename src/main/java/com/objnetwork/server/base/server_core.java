package com.objnetwork.server.base;

import java.io.*;

import com.objnetwork.server.beans.Device;
import com.objnetwork.server.service.DeviceService;
import org.eclipse.paho.client.mqttv3.*;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.json.JSONObject;

import com.objnetwork.server.mqtt.*;
import org.springframework.beans.factory.annotation.Autowired;


public class server_core {

	private static String managername2topic(String st) {
		return "Manager/"+st;
	}

	private static MQTTServer mqtt;

	private static void Device_New(JSONObject data) {
		String deviceName = data.getString("deviceName");
		String new_manager = data.getString("new-manager");
		String deviceID = data.getString("deviceID");
		Device devicenew = new Device(deviceName, new_manager, deviceID);
	}
	
	private static void Device_Change(JSONObject data) {
		System.out.println("void Device_change");
		String old_manager = data.getString("old-manager");
		String new_manager = data.getString("new-manager");
		String deviceName = data.getString("deviceName");
		String deviceID = data.getString("deviceID");
		Device devicechange = new Device(deviceName, new_manager, deviceID);
		
//		Device devicenew = new Device(device_name, new_manager, device_id);
		
//		Configuration dao = new Configuration().configure();
//		SessionFactory sf = dao.buildSessionFactory();
//		Session session = sf.openSession();
//		
//		session.beginTransaction();
//		session.update(devicenew);
//		session.getTransaction().commit();
//		session.close();
//		sf.close();
		
		JSONObject send = new JSONObject();
		JSONObject senddata = new JSONObject();
		senddata.append("manager", old_manager);
		senddata.append("deviceName", deviceName);
		senddata.append("deviceID", deviceID);
		send.append("contentType", "remove-device");
		send.append("data", senddata);
		
		String jst = send.toString();
		
//		MqttMessage message = new MqttMessage();
//		message.setQos(1);
//		message.setRetained(true);
//		message.setPayload(jsst.getBytes());
		System.out.println(jst.getBytes().toString());

		try {
			System.out.println("a1");
			mqtt.publish("abcd", jst.getBytes());
//			mqtt.publish(managername2topic(old_manager), jst.getBytes());
			System.out.println("abcd");
		} catch (MqttException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
	
	private static void Message_Send(JSONObject json) {
		System.err.println("servercore.message send");
		String target = json.getJSONObject("data").getString("targetManager");
		String jst = json.toString();
		
		try {
			mqtt.publish(managername2topic(target), jst.getBytes());
		} catch (MqttPersistenceException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	private static void Message_Analysis(JSONObject json) throws IOException {
		String contentType = json.getString("contentType");
		if (contentType.equals("device-new")) {
			Device_New(json.getJSONObject("data"));
		}
		if (contentType.equals("device-change")) {
			Device_Change(json.getJSONObject("data"));
		}
		if (contentType.equals("device-manager-requst")
				||contentType.equals("device-manager-response")) {
			Message_Send(json);
		}
	}
	
	public static void Message_Upload_test(String filename) throws IOException {
		System.out.println("servercore.message upload test");
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String st = "";
		while (br.ready()) st = st+br.readLine();
		System.out.println(st);
		JSONObject json = new JSONObject(st);
		
		Message_Analysis(json);
		
		
		
//		return false;
	}
	
	public static void Message_Upload(JSONObject json) throws IOException {
		Message_Analysis(json);
	}
	
	public static void test() throws IOException {
		String filename = "E:\\biyesheji\\test.json";
		
//		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		map1.put("contentType", "device-new");
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("new-manager", "NEW MANAGER NAME");
//		map2.put("deviceName", "device");
//		map2.put("deviceID", "mac");
//		map1.put("data", map2);
//		
//		JsonWriter writer = new JsonWriter(
//				new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
//		Gson gson;
//		gson.toJson(map1, writer);
		
		Message_Upload_test(filename);
	}
	
	public static void init() throws MqttException {
		mqtt = new MQTTServer();
		
	}
}