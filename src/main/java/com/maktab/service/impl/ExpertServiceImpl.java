package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.FileReaderException;
import com.maktab.repository.ExpertRepository;
import com.maktab.service.ExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.io.File;
import java.io.FileInputStream;


// use service in service or repo ?

@Service
public class ExpertServiceImpl extends BaseServiceImpl<Expert, ExpertRepository> implements ExpertService {

    public ExpertServiceImpl(ExpertRepository repository) {
        super(repository);
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
//        if (image.getName().matches("/*jpg")) {
            try (FileInputStream reader = new FileInputStream(image)) {
                bytes = reader.readAllBytes();
            } catch (Exception e) {
                throw new FileReaderException();
            }
//        } else
//            throw new FileReaderException("wrong file format !");

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