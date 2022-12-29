package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;

import java.io.File;


public interface ExpertService extends BaseService<Expert> {


    void changePassword(Long id, String password);


    void setProfileImage(byte[] image, Long id);

    Long confirmExpert(Long id);

    void addExpertToSubService(Expert expert, SubService subService);

    void deleteExpertOfSubService(Expert expert, SubService subService);

    Long signIn(String firstName, String lastName, String Email, String password , byte[] image);

//    void setComment(Long expertId , Long orderId ,Float rating , String description);

}
