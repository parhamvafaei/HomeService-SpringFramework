package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.DeleteExpertException;
import com.maktab.exception.DuplicateServiceException;
import com.maktab.exception.ExpertAddException;
import com.maktab.exception.NotFoundServiceException;
import com.maktab.repository.SubServiceRepository;
import com.maktab.service.SubServiceService;
import com.maktab.util.Utils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class SubServiceServiceImpl extends BaseServiceImpl<SubService, SubServiceRepository> implements SubServiceService {


    public SubServiceServiceImpl(SubServiceRepository repository) {
        super(repository);
    }

    @Override
    public Boolean checkSubServiceInName(String name) {
        return repository.existsByName(name);
    }

    @Transactional
    @Override
    public Long addSubService(SubService subService, Service service) {
        if (!(isExistsById(subService.getId())))
            throw new NotFoundServiceException("this SubService doesnt exist !");

        if (checkSubServiceInName(subService.getName()))
            throw new DuplicateServiceException("this SubService was already added!");

        if (!(Utils.existsSubServiceInService(subService, service)))
            throw new NotFoundServiceException("this SubService was already added!");

subService.setService(service);
        saveOrUpdate(subService);

        return subService.getId();

    }

    @Override
    public List<SubService> LoadSubServices() {
        return findAll();
    }

    @Transactional
    @Override
    public void editSubService(Long id, Double price, String description) {
        SubService subService = findById(id).orElseThrow(NullPointerException::new);

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
    public void addExpertToSubService(Expert expert, SubService subService) {
        if (subService.getExperts().contains(expert))
            throw new ExpertAddException("expert already added !");
        if (expert.getExpertStatus() == ExpertStatus.CONFIRMED && checkSubServiceInName(subService.getName())) {

            List<Expert> experts = subService.getExperts();
            experts.add(expert);
            subService.setExperts(experts);

            saveOrUpdate(subService);

        } else
            throw new ExpertAddException();
    }


    @Override
    public void deleteExpertOfSubService(Expert expert, SubService subService) {

        if (subService.getExperts().contains(expert)) {
            List<Expert> experts = subService.getExperts();
            experts.remove(expert);
            subService.setExperts(experts);

            saveOrUpdate(subService);


        } else
            throw new DeleteExpertException("delete expert of subService failed!");
    }

}
