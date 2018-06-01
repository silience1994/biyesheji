package com.objnetwork.server.serviceImpl;

import com.objnetwork.server.beans.Naming;
import com.objnetwork.server.dao.NamingDao;
import com.objnetwork.server.service.NamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamingServiceImpl implements NamingService {
    @Autowired
    private NamingDao namingDao;

    @Override
    public Naming getNaming(String name){
        Naming naming = namingDao.findNamingByName(name);
        return naming;
    }

    @Override
    public void saveNaming(Naming naming){
        namingDao.save(naming);
    }
}
