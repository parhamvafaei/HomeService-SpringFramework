package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.DeleteExpertException;
import com.maktab.exception.ExpertAddException;
import com.maktab.exception.FileReaderException;
import com.maktab.repository.ExpertRepository;
import com.maktab.service.ExpertService;
import com.maktab.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


// use service in service or repo ?
//why contains method doesn't work ?

@Service
public class ExpertServiceImpl extends BaseServiceImpl<Expert, ExpertRepository> implements ExpertService {

    private final SubServiceService subServiceService;
    public ExpertServiceImpl(ExpertRepository repository, SubServiceService subServiceService) {
        super(repository);
        this.subServiceService = subServiceService;
    }

    @Transactional
    @Override
    public void changePassword(Long id, String password) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);

        try {
            expert.setPassword(password);
        } catch (Exception e) {
            throw new ValidationException();
        }
        saveOrUpdate(expert);

    }


    @Transactional
    @Override
    public void setProfileImage(File image, Long id) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);
        byte[] bytes;
        if (image.getName().contains(".jpg")) {
            try (FileInputStream reader = new FileInputStream(image)) {
                bytes = reader.readAllBytes();
            } catch (Exception e) {
                throw new FileReaderException();
            }
        } else
            throw new FileReaderException("wrong file format !");

        expert.setImage(bytes);
        saveOrUpdate(expert);
    }


    @Transactional
    @Override
    public Long confirmExpert(Long id) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        return expert.getId();
    }


    //just save from expert side which has table join ?
    @Transactional
    @Override
    public void addExpertToSubService(Expert expert, SubService subService) {
        if (subService.getExperts().contains(expert))
            throw new ExpertAddException("expert already added !");
        if (expert.getExpertStatus() == ExpertStatus.CONFIRMED && subServiceService.checkSubServiceInName(subService.getName())) {

            List<SubService> subServices = expert.getSubServices();
            subServices.add(subService);
            expert.setSubServices(subServices);

            saveOrUpdate(expert);

        } else
            throw new ExpertAddException();
    }

    @Transactional
    @Override
    public void deleteExpertOfSubService(Expert expert, SubService subService) {

        if (subService.getExperts().stream().anyMatch(   expert1 -> expert1.equals(expert))) {
            List<SubService> subServices = expert.getSubServices();
            subServices.remove(subService);
            expert.setSubServices(subServices);


            saveOrUpdate(expert);


        } else
            throw new DeleteExpertException("delete expert of subService failed!");
    }


//    @Transactional
//    @Override
//    public void changeEmail(Long id,String email) {
//        Optional<Expert> expert = repository.findById(id);
//        if (expert.isPresent()) {
//            try {
//                expert.get().setEmail(email);
//            }catch (Exception e) {
//                throw new ValidationException();
//            }
//                saveOrUpdate(expert.get());
//
//        } else
//            throw new NullPointerException();
//
//    }
}