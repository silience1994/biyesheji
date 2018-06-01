package com.objnetwork.server.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @Column(name = "address")
    private String address;

    @Column(name = "mac")
    private String mac;

    @Column(name = "property")
    private int property;

    @Column(name = "certificate")
    private String certificate;

    public Manager() {
    }

    public Manager(String address, String mac, int property, String certificate) {
        this.address = address;
        this.mac = mac;
        this.property = property;
        this.certificate = certificate;
    }

    @Id
    public String getAddress() {
        return address;
    }

    @Column
    public void setAddress(String address) {
        this.address = address;
    }

    public String getMac() {
        return mac;
    }

    @Column
    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getProperty() {
        return property;
    }

    @Column
    public void setProperty(int property) {
        this.property = property;
    }

    public String getCertificate() {
        return certificate;
    }

    @Column
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
