package com.maktab;


import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.*;
import org.apache.catalina.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



import java.util.List;


@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProjectApplication.class, args);
        ExpertService expertService = run.getBean(ExpertService.class);
//        SubServiceService service = run.getBean(SubServiceService.class);
//        Expert expert = new Expert();
//        expert.setExpertStatus(ExpertStatus.CONFIRMED);
//        expert.setPassword("paRham23");
//        SubService subService = new SubService();
//        subService.setName("sas salma");
//        expertService.saveOrUpdate(expert);
//        service.saveOrUpdate(subService);
//
//       List<SubService> list = expert.getSubServices();
//        list.add(subService);
//        expert.setSubServices(list);
//      expertService.saveOrUpdate(expert);
//
////        List<Expert> experts = subService.getExperts();
////        experts.add(expert);
////        subService.setExperts(experts);
////        service.saveOrUpdate(subService);
//
//
//        System.out.println(service.findById(subService.getId()).get());
//        System.out.println(service.findById(subService.getId()).get().getExperts().contains(expert));
//        System.out.println(service.findById(subService.getId()).get().getExperts());
//        System.out.println(expertService.findById(expert.getId()).get().getSubServices());

expertService.signIn("sgsrg","sgsg","parjf@gmail.com","sarga1Ag",null);
    }

}
