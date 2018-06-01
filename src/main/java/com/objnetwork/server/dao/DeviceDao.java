package com.objnetwork.server.dao;

import com.objnetwork.server.beans.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDao extends JpaRepository<Device, String> {
    Device findDeviceBydeviceid(String deviceid);
    List<Device> findDeviceByAddress(String address);
    List<Device> findDeviceByName(String name);
}
