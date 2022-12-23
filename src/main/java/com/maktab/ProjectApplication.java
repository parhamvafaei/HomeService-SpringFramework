package com.maktab;



import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.AdminService;
import com.maktab.service.ClientService;
import com.maktab.service.ExpertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class ProjectApplication {

//	public static void main(String[] args) {
//		ConfigurableApplicationContext run = SpringApplication.run(ProjectApplication.class, args);
//	Expert expert=	new Expert(null,ExpertStatus.CONFIRMED,null,null,null);
//	expert.setPassword("kdJas345");
//		run.getBean(ExpertService.class).saveOrUpdate(expert);
//run.getBean(AdminService.class).saveOrUpdate(new Admin());
//
//		run.getBean(ExpertService.class).saveOrUpdate(new
//				Expert(20F, ExpertStatus.CONFIRMED,null,null,null));

//		AdminService bean = run.getBean(AdminService.class);
//		Admin admin = new Admin("sdfd","adfv",null,null);
//		 System.out.println(bean.findById(admin.getId()).orElseThrow(NullPointerException::new) );

//		admin.setPassword("Pksdjfj2");
//		bean.saveOrUpdate(admin);
//		System.out.println(bean.findById(admin.getId()).get().getPassword());
//		bean.changePassword(admin.getId(), "FGsrofm3");
////		admin.setPassword("FGsrofm3");
//		Long aLong = bean.saveOrUpdate(admin);
//		System.out.println(bean.findById(admin.getId()).get().getPassword());

//		ClientService bean = run.getBean(ClientService.class);
//		Client client = new Client();
//		client.setPassword("sfevsvD3");
//		bean.saveOrUpdate(client);
//		System.out.println(bean.findById(client.getId()).get().getPassword());
//		Long aLong = bean.saveOrUpdate(client);
//		bean.changePassword(aLong,"kkkkKkj3");
//		System.out.println(bean.findById(client.getId()).get().getPassword();

	}
