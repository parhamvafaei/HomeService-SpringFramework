package com.maktab.service;


import com.maktab.base.service.impl.BaseService;
import com.maktab.entity.person.Expert;

import java.io.File;


public interface ExpertService extends BaseService<Expert> {


    void changePassword(Long id, String password);


    void setProfileImage(File image, Long id);

    Long confirmExpert(Long id);


    //    void changeEmail(Long id, String email);
}
