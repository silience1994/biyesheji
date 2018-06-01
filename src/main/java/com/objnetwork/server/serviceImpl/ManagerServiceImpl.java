package com.objnetwork.server.serviceImpl;

import com.objnetwork.server.beans.Manager;
import com.objnetwork.server.dao.ManagerDao;
import com.objnetwork.server.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;

    @Override
    public Manager getManager(String address) {
        Manager manager = managerDao.findManagerByAddress(address);
        return manager;
    }

    @Override
    public void saveManager(Manager manager) {
        managerDao.save(manager);
    }

    @Override
    public List<Manager> getManagerbyprefix(String prefix) {
        List<Manager>list = managerDao.findManagersByAddressContains(prefix);
        return list;
    }
}
