package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.dto.ExpertFilterDTO;
import com.maktab.entity.person.Expert;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


public interface ExpertService extends BaseService<Expert> {


    void changePassword(Long id, String password);


    void setProfileImage(byte[] image, Expert expert);

    Long confirmExpert(Long id);


    void addExpertToSubService(Long expertId, Long subServiceId);


    void deleteExpertOfSubService(Long expertId, Long subServiceId);

    Long signIn(String firstName, String lastName, String Email, String password, byte[] image);

    boolean checkImage(MultipartFile file);


    List<Expert> filterExpert(ExpertFilterDTO expertDTO);

    List<Expert> expertReporter(LocalDateTime signInTime, Integer ordersSet, Integer ordersDone);
}
