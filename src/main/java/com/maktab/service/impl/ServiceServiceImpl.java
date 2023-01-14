package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Service;
import com.maktab.exception.DuplicateServiceException;
import com.maktab.repository.ServiceRepository;
import com.maktab.service.ServiceService;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Long addService(String name) {
        if (repository.existsByName(name) )
            throw new DuplicateServiceException("Service already exist");
        Service service=Service.builder().name(name).build();
        saveOrUpdate(service);
        return service.getId();
    }


}
