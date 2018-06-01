package com.objnetwork.server.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {
    public Device(String mac, String name, String address) {
        this.deviceid = mac;
        this.name = name;
        this.address = address;
    }

    @Id
    @Column(name = "deviceid")
    private String deviceid;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public Device() {
    }

    @Id
    public String getDeviceid() {
        return deviceid;
    }

    @Column
    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getName() {
        return name;
    }

    @Column
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    @Column
    public void setAddress(String address) {
        this.address = address;
    }
}
