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

    }

}
