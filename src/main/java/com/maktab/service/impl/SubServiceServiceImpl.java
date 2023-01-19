package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Service;
import com.maktab.entity.SubService;

import com.maktab.exception.NotFoundServiceException;
import com.maktab.repository.SubServiceRepository;
import com.maktab.service.ServiceService;
import com.maktab.service.SubServiceService;
import com.maktab.util.Utils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SubServiceServiceImpl extends BaseServiceImpl<SubService, SubServiceRepository> implements SubServiceService {

    private final ServiceService serviceService;

    public SubServiceServiceImpl(SubServiceRepository repository, ServiceService serviceService) {
        super(repository);
        this.serviceService = serviceService;
    }

    @Override
    public Boolean checkSubServiceByName(String name) {
        return repository.existsByName(name);
    }

    @Transactional
    @Override
    public Long addSubService(SubService subService, Service service) {

        serviceService.findById(service.getId()).orElseThrow(() ->new NotFoundServiceException("service not found"));

        if (checkSubServiceByName(subService.getName()))
            throw new NotFoundServiceException("this SubService was already added!");

        if (Utils.existsSubServiceInService(subService, service))
            throw new NotFoundServiceException("this SubService was already added!");

        subService.setService(service);
        saveOrUpdate(subService);

        return subService.getId();

    }

    @Override
    public List<SubService> loadSubServices() {
        return findAll();
    }

    @Transactional
    @Override
    public void editSubService(Long id, Double price, String description) {
        SubService subService = findById(id).orElseThrow(() ->new NotFoundServiceException("subService not found"));

        boolean condition = (price == null && description == null);
        while (!(condition)) {
            if (price == null)
                subService.setDescription(description);
            else if (description == null) {
                subService.setPrice(price);
            } else {
                subService.setDescription(description);
                subService.setPrice(price);
            }
            condition = true;
        }

        saveOrUpdate(subService);

    }

    @Override
    public Optional<SubService> findByName(String name) {
        return repository.findByName(name);
    }

}
