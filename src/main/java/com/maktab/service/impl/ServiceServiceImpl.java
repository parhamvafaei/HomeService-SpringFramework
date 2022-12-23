package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Service;
import com.maktab.repository.ServiceRepository;
import com.maktab.service.ServiceService;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl extends BaseServiceImpl<Service, ServiceRepository> implements ServiceService {

    public ServiceServiceImpl(ServiceRepository repository) {
        super(repository);
    }

    @Override
    public List<Service> loadServices() {
        return findAll();
    }
}
