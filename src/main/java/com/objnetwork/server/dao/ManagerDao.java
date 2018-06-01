package com.objnetwork.server.dao;

import com.objnetwork.server.beans.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerDao extends JpaRepository<Manager,String> {
    Manager findManagerByAddress(String address);
    List<Manager> findManagersByAddressContains(String prefix);

}
