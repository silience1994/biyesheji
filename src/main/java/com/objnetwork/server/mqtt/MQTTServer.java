package com.objnetwork.server.mqtt;

import java.io.*;
import java.text.SimpleDateFormat;

import org.eclipse.paho.client.mqttv3.MqttClient;  
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;  
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;  
import org.eclipse.paho.client.mqttv3.MqttException;  
import org.eclipse.paho.client.mqttv3.MqttMessage;  
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;  
import org.eclipse.paho.client.mqttv3.MqttTopic;  
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

public class MQTTServer {
	public static String HOST = "tcp://127.0.0.1:61613";
	public static final String TOPIC = "pos_message_all";
	private static String clientid = "server";
	
	private static MqttClient client;
	private static MqttTopic mqttTopic;
	private static MQTTServer server;
	
	private static String userName = "server";
	private static String passWord = "server";
	
	public MQTTServer() throws MqttException {
		client = new MqttClient(HOST, clientid, new MemoryPersistence());
		connect();
	}
	
	private void connect() {
		System.err.println("void connect");
		String st = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/MQTT.json"));
			while (br.ready()) st = st+br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject op = new JSONObject(st);
		System.err.println(op.toString());
		HOST = op.getString("HOST");
		clientid = op.getString("ClientID");
		userName = op.getString("UserName");
		passWord = op.getString("Password");
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		options.setConnectionTimeout(10);
		options.setKeepAliveInterval(20);
		try {
			client.setCallback(new PushCallback());
			client.connect(options);
			mqttTopic = client.getTopic(TOPIC);
			if (client.isConnected()) {
				System.err.println("MQTT connected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void publish(String topic, byte[] bytein) throws MqttPersistenceException, MqttException {
		String st = new String(bytein);
		System.out.println(st);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.print("time:");
		System.out.println(df.format(System.currentTimeMillis()));
		MqttMessage message = new MqttMessage();
		message.setQos(1);
		message.setPayload(bytein);
		message.setRetained(true);
		boolean check = true;
		mqttTopic = client.getTopic(topic);
		while (check) {
			try {
				MqttDeliveryToken token = mqttTopic.publish(message);
				token.waitForCompletion();
				token.getResponse();
				check = false;
			}
			catch (Exception e) {
				client.connect();
			}
		}
		if (client.isConnected()) {
//			client.disconnect(10000);
			System.out.println("client connected");
		}
		
	}
	
	public static void init() throws MqttException {
		server = new MQTTServer();
	}

	public MqttTopic getMqttTopic() {
		return mqttTopic;
	}

	public void setMqttTopic(MqttTopic mqttTopic) {
		this.mqttTopic = mqttTopic;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
