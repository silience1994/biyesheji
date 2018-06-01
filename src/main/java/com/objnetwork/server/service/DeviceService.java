package com.objnetwork.server.service;

import com.objnetwork.server.beans.Device;

import java.util.List;

public interface DeviceService {
    Device getDevice(String deviceid);
    void saveDevice(Device device);
    List<Device> getDeviceByAddress(String address);
    List<Device> findDeviceByName(String name);
}
