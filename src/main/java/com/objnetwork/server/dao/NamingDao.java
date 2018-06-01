package com.objnetwork.server.dao;

import com.objnetwork.server.beans.Naming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NamingDao extends JpaRepository<Naming,String> {
    Naming findNamingByName(String name);
}
