package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.Service;

import java.util.List;

public interface ServiceService extends BaseService<Service> {


    List<Service> loadServices();
}
