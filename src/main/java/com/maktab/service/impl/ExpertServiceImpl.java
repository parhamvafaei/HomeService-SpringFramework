package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.*;

import com.maktab.repository.ExpertRepository;
import com.maktab.service.ExpertService;
import com.maktab.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;


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

    //how to check format

    @Override
    public void setProfileImage(byte[] image, Expert expert ) {

        expert.setImage(image);
        saveOrUpdate(expert);

    }


    @Transactional
    @Override
    public Long confirmExpert(Long id) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        saveOrUpdate(expert);
        return expert.getId();
    }

    //show orders to expert
    @Transactional
    @Override
    public void addExpertToSubService(Long expertId, Long subServiceId) {
        Expert expert = findById(expertId).orElseThrow(NullPointerException::new);
        SubService subService = subServiceService.findById(subServiceId).orElseThrow(NullPointerException::new);
        if (subService.getExperts().contains(expert))
            throw new ExpertAddException("expert already added !");
        if (expert.getExpertStatus() == ExpertStatus.CONFIRMED && subServiceService.checkSubServiceByName(subService.getName())) {

            List<SubService> subServices = expert.getSubServices();
            subServices.add(subService);
            expert.setSubServices(subServices);

            saveOrUpdate(expert);

        } else
            throw new ExpertAddException();
    }


    @Transactional
    @Override
    public void deleteExpertOfSubService(Long expertId, Long subServiceId) {
        Expert expert = findById(expertId).orElseThrow(NullPointerException::new);
        SubService subService = subServiceService.findById(subServiceId).orElseThrow(NullPointerException::new);

        if (subService.getExperts().stream().anyMatch(expert1 -> expert1.getId().equals(expert.getId()))) {
            List<SubService> subServices = expert.getSubServices();
            subServices.remove(subService);
            expert.setSubServices(subServices);


            saveOrUpdate(expert);


        } else
            throw new DeleteExpertException("delete expert of subService failed!");
    }


    @Override
    public Long signIn(String firstName, String lastName, String Email, String password, byte[] image) {

        if (repository.existsByEmail(Email)) {
            throw new PersonSignInException("this expert already exist");
        }
        Expert expert = Expert.builder().firstName(firstName).lastName(lastName).Email(Email).password(password)
                .build();

        setProfileImage(image, expert);
        saveOrUpdate(expert);
        return expert.getId();
    }

    @Override
    public boolean checkImage(MultipartFile file) {

        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        if (!(split[split.length - 1].equals("jpg")))
            throw new FileReaderException("wrong format");

        if (file.getSize() > 300_000)
            throw new FileReaderException("image size is more than standard");


        return true;
    }
//criteria
//specification
//    Expert filterExpert(String firstname , String lastName , String email , String serviceName , Float rating){
//        findAll().stream().filter(expert ->
//                if(firstname!=null){expert.getFirstName().equals(firstname))
//
//    }

}