package com.maktab.repository;




import com.maktab.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    Boolean existsByName(String name);


//    @Query("select s from SubService s where s.service.name= :subService in ")
//    Boolean existsSubServiceInService(SubService subService , Service service);

}
