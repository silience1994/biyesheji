package com.objnetwork.server.controller;

import com.objnetwork.server.beans.Device;
import com.objnetwork.server.beans.Manager;
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
public class MapController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private NamingService namingService;

    private JSONObject mapgetitemlist(String manager) {
        System.out.println("map get itemlist");
        List<Device>list = deviceService.getDeviceByAddress(manager);
        JSONObject data = new JSONObject();
        for (int i=0; i<list.size(); ++i) {
            data.append("itemList",list.get(i).getName());
        }
        return data;
    }

    private JSONObject mapgetmanagers(String prefix) {
        System.out.println("map get managers");
        List<Manager>list = managerService.getManagerbyprefix(prefix);
        JSONObject data = new JSONObject();
        for (int i=0; i<list.size(); ++i) {
            data.append("managerList",list.get(i).getAddress());
        }
        return data;
    }

    private JSONObject Message_Analysis(JSONObject json) {
        JSONObject res = new JSONObject();
        String contentType = json.getString("contentType");
        if (contentType.equals("map-get-itemlist")) {
            JSONObject data = mapgetitemlist(json.getJSONObject("data").getString("manager"));
            res.put("contentType","item-found");
            res.put("data",data);
            return res;
        }
        if (contentType.equals("map-get-manager")) {
            JSONObject data = mapgetmanagers(json.getJSONObject("data").getString("managerLocation"));
            res.put("contentType","manager-found");
            res.put("data",data);
            return res;
        }
        res.put("contentType","Error");
        res.put("Message","Unknown contentType");
        return res;
    }

    @RequestMapping(value = "/MapRequest", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String MapRequest(@RequestBody String jst) {
        System.err.println("/MapRequest");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print("time:");
        System.out.println(df.format(System.currentTimeMillis()));
        System.err.println(jst);
        String res = Message_Analysis(new JSONObject(jst)).toString();
        System.out.println(res);
        return res;
    }

}
