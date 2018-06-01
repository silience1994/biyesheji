package com.objnetwork.server.service;

import com.objnetwork.server.beans.Manager;

import java.util.List;

public interface ManagerService {
    Manager getManager(String address);
    void saveManager(Manager manager);
    List<Manager> getManagerbyprefix(String prefix);
}
