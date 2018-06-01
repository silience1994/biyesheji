package com.objnetwork.server.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "naming")
public class Naming {
    public Naming(){}

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Id
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
