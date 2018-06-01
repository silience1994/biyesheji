package com.objnetwork.server.controller;

import com.objnetwork.server.beans.Device;
import com.objnetwork.server.beans.Manager;
import com.objnetwork.server.beans.Naming;
import com.objnetwork.server.mqtt.MQTTServer;
import com.objnetwork.server.service.DeviceService;
import com.objnetwork.server.service.ManagerService;
import com.objnetwork.server.service.NamingService;
import com.objnetwork.server.tool.tools;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Iterator;

@Controller
public class AutoController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private NamingService namingService;

    private Manager Name2Manager(String name){
        Manager manager = managerService.getManager(name);
        if (manager!=null) {
            System.err.println("get from manager");
            return manager;
        }
        Naming naming = namingService.getNaming(name);
        if (naming!=null){
            System.err.println("get from name");
            return managerService.getManager(naming.getAddress());
        }
        return null;
    }

    private JSONObject Device_New(JSONObject data) {
        System.err.println("auto.devicenew");
        String deviceName = data.getString("deviceName");
        String new_manager = data.getString("new-manager");
        String deviceID = data.getString("deviceID");
        Device devicenew = new Device(deviceID,deviceName,new_manager);
        deviceService.saveDevice(devicenew);
        JSONObject res = new JSONObject();
        res.put("result","Succeed");
        res.put("action","Device_New");
        return res;
    }

    private JSONObject Device_Change(JSONObject data) {
        System.err.println("auto.devicechange");
        String old_manager = data.getString("old-manager");
        String new_manager = data.getString("new-manager");
        String deviceName = data.getString("deviceName");
        String deviceID = data.getString("deviceID");

        if (old_manager.equals(new_manager)) {
            System.out.println("Same Manager");
            Device deviceedit = new Device(deviceID,deviceName,new_manager);
            deviceService.saveDevice(deviceedit);
            JSONObject res = new JSONObject();
            res.put("result","Succeed");
            res.put("action","Device_New");
            return res;
        }

        Device devicenew = new Device(deviceID,deviceName,new_manager);
        System.err.println("auto:get target manager");
        Manager target = Name2Manager(old_manager);
        if (target == null) {
            System.err.println("auto:target manager not found");
            JSONObject res = new JSONObject();
            res.put("result","Failed");
            res.put("action","Device_Change");
            res.put("message","Target Not Found");
            return res;
        }
        System.err.println("auto:target manager got");

        JSONObject send = new JSONObject();
        JSONObject senddata = new JSONObject();
        senddata.put("manager", target.getAddress());
        senddata.put("deviceName", devicenew.getName());
        senddata.put("deviceID", devicenew.getDeviceid());
        send.put("contentType", "remove-device");
        send.put("data", senddata);

        String jst = send.toString();

        System.err.println(jst.getBytes().toString());

        try {
            System.err.println("auto:publish ready");
//            MQTTServer.publish("abcd", jst.getBytes());
			MQTTServer.publish(tools.managername2topic(target.getAddress()), jst.getBytes());
            System.err.println("auto:published");
            JSONObject res = new JSONObject();
            res.put("result","Succeed");
            res.put("action","Device_Change");
            return res;
        } catch (MqttException e) {
            e.printStackTrace();
            JSONObject res = new JSONObject();
            res.put("result","Failed");
            res.put("action","Device_Change");
            res.put("message",e.getMessage());
            return res;
        }
    }
    private JSONObject Message_Send(JSONObject json) {
        System.err.println("autocontroller.message send");
        String targetname = json.getJSONObject("data").getString("targetManager");

        System.err.println("auto:get target manager");
        Manager target = Name2Manager(targetname);
        if (target == null) {
            System.err.println("auto:target manager not found");
            JSONObject res = new JSONObject();
            res.put("result","Failed");
            res.put("action","Message_Send");
            res.put("message","Target Not Found");
            return res;
        }
        System.err.println("auto:target manager got");

        JSONObject s = new JSONObject();
        JSONObject sdata = new JSONObject();
        JSONObject data = json.getJSONObject("data");
        Iterator<String> list = data.keys();
        while (list.hasNext()) {
            String key = list.next();
            if (key.equals("targetManager")){
                sdata.put("targetManager",target.getAddress());
            }
            else {
                if (key.equals("success")) {
                    sdata.put(key,data.getBoolean(key));
                }
                else {
                    sdata.put(key,data.getString(key));
                }
            }
        }
        s.put("data",sdata);
        s.put("contentType",json.getString("contentType"));
        String jst = s.toString();

        try {
            System.err.println("auto:publish ready");
            MQTTServer.publish(tools.managername2topic(target.getAddress()), jst.getBytes());
            System.err.println("auto:published");
            JSONObject res = new JSONObject();
            res.put("result","Succeed");
            res.put("action","Message_Send");
            return res;
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
            JSONObject res = new JSONObject();
            res.put("result","Failed");
            res.put("action","Message_Send");
            res.put("message",e.getMessage());
            return res;
        } catch (MqttException e) {
            JSONObject res = new JSONObject();
            res.put("result","Failed");
            res.put("action","Message_Send");
            res.put("message",e.getMessage());
            e.printStackTrace();
            return res;
        }
    }

    private JSONObject Message_Analysis(JSONObject json) {
        String contentType = json.getString("contentType");

        if (contentType.equals("device-new")) {
            return Device_New(json.getJSONObject("data"));
        }
        if (contentType.equals("device-change")) {
            return Device_Change(json.getJSONObject("data"));
        }
        if (contentType.equals("device-manager-request")
                ||contentType.equals("device-manager-response")) {
            return Message_Send(json);
        }

        JSONObject res = new JSONObject();
        res.put("result","Failed");
        res.put("action","Message_Analysis");
        res.put("message","Wrong contentType");
        return res;
    }

    @RequestMapping(value = "/upload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Upload(@RequestBody String jst) {
        System.err.println("/upload");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print("time:");
        System.out.println(df.format(System.currentTimeMillis()));
        System.err.println(jst);
        String res = Message_Analysis(new JSONObject(jst)).toString();
        System.out.println(res);
        return res;
    }
}
