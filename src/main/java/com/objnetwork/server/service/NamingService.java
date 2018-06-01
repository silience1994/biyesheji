package com.objnetwork.server.service;

import com.objnetwork.server.beans.Naming;

public interface NamingService {
    Naming getNaming(String name);
    void saveNaming(Naming naming);
}
