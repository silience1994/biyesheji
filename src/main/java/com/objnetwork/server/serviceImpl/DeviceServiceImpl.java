package com.objnetwork.server.serviceImpl;

import com.objnetwork.server.beans.Device;
import com.objnetwork.server.dao.DeviceDao;
import com.objnetwork.server.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Device getDevice(String deviceid) {
        Device device = deviceDao.findDeviceBydeviceid(deviceid);
        return device;
    }

    @Override
    public void saveDevice(Device device){
        deviceDao.save(device);
    }

    @Override
    public List<Device> getDeviceByAddress(String address) {
        List<Device>list = deviceDao.findDeviceByAddress(address);
        return list;
    }
    @Override
    public List<Device> findDeviceByName(String name) {
        List<Device>list = deviceDao.findDeviceByName(name);
        return list;
    }
}
