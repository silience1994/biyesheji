package com.objnetwork.server.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import com.objnetwork.server.base.*;

public class PushCallback implements MqttCallback {
	public void connectionLost(Throwable cause) {
		System.err.println("connection lost");
		System.err.println();
	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.err.println("deliveryComplete");
	}
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String st = new String(message.getPayload());
		System.err.println("messageArrived");
		System.err.println(st);
		server_core.Message_Upload_test(st);
	}
}
