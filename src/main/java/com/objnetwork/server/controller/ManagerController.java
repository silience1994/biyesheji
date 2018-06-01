package com.objnetwork.server.controller;

import com.objnetwork.server.beans.Device;
import com.objnetwork.server.beans.Manager;
import com.objnetwork.server.beans.Naming;
import com.objnetwork.server.service.DeviceService;
import com.objnetwork.server.service.ManagerService;
import com.objnetwork.server.service.NamingService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ManagerController {
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

    private JSONObject getItemList(JSONObject data0) {
        System.out.println("get item list");
        String name = data0.getString("manager");
        Manager manager = Name2Manager(name);
        name = manager.getAddress();
        System.out.println("manager:"+name);
        JSONObject data = new JSONObject();
        data.put("manager","manager");
        List<Device>itemList = deviceService.getDeviceByAddress(name);
        for (int i = 0; i<itemList.size(); ++i) {
            JSONObject item = new JSONObject();
            item.put("deviceName",itemList.get(i).getName());
            item.put("deviceID",itemList.get(i).getDeviceid());
            data.append("itemList",item);
        }
        JSONObject res = new JSONObject();
        res.put("contentType","manager-itemlist");
        res.put("data",data);
        return res;
    }

    private JSONObject findmanager(JSONObject data){
        System.out.println("find manager");
        String name = data.getString("manager");
        Manager manager = Name2Manager(name);
        boolean success = (manager!=null);
        data.put("success",success);
        JSONObject res = new JSONObject();
        res.put("contentType","find-manager");
        res.put("data",data);
        return res;
    }

    private JSONObject finditem(JSONObject data0) {
        System.out.println("find item");
        String name = data0.getString("itemName");
        List<Device> list = deviceService.findDeviceByName(name);
        JSONObject data = new JSONObject();
        for (int i=0; i<list.size(); ++i) {
            JSONObject item = new JSONObject();
            Device device = list.get(i);
            item.put("itemName",device.getName());
            item.put("itemID",device.getDeviceid());
            item.put("manager",device.getAddress());
            data.append("items",item);
        }
        JSONObject res = new JSONObject();
        res.put("contentType","find-item-result");
        res.put("data",data);
        return res;
    }

    private JSONObject Message_Analysis(JSONObject jst) {
        String contentType = jst.getString("contentType");
        if (contentType.equals("manager-get-itemlist")) {
            return getItemList(jst.getJSONObject("data"));
        }
        if (contentType.equals("find-manager")) {
            return findmanager(jst.getJSONObject("data"));
        }
        if (contentType.equals("find-item")) {
            return finditem(jst.getJSONObject("data"));
        }
        return null;
    }

    @RequestMapping(value = "/ManagerRequest", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String ManagerRequest(@RequestBody String jst) {
        System.err.println("/ManagerRequest");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print("time:");
        System.out.println(df.format(System.currentTimeMillis()));
        System.err.println(jst);
        String res = Message_Analysis(new JSONObject(jst)).toString();
        System.out.println(res);
        return res;
    }
}
