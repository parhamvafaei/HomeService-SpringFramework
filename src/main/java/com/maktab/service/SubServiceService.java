package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.Service;
import com.maktab.entity.SubService;

import java.util.List;


public interface SubServiceService extends BaseService<SubService> {
    Boolean checkSubServiceByName(String name);

    Long addSubService(SubService subService, Service service);

    List<SubService> LoadSubServices();

    void editSubService(Long id, Double price, String description);



}
