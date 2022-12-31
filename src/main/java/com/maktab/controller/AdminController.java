package com.maktab.controller;

import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.dto.ChangePasswordDTO;
import com.maktab.entity.dto.EditSubServiceDTO;
import com.maktab.entity.dto.SubServiceDTO;
import com.maktab.service.AdminService;
import com.maktab.service.ExpertService;
import com.maktab.service.ServiceService;
import com.maktab.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {


    private final AdminService adminService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;

    private final ExpertService expertService;

    @GetMapping("/find-all-services")
    List<Service> findAllServices() {
        return serviceService.loadServices();
    }

    @GetMapping("/find-all-subServices")
    List<SubService> findAllSubServices() {
        return subServiceService.loadSubServices();
    }

    @PostMapping("/save-service/{name}")
    void saveService(@PathVariable String name) {
        serviceService.addService(name);
    }

    @PostMapping("/save-subService/{service_id}")
    void addSubService(@RequestBody SubServiceDTO subServiceDTO, @PathVariable Long service_id) {
        Service service = serviceService.findById(service_id).orElseThrow(NullPointerException::new);
        SubService subService = SubService.builder().name(subServiceDTO.getName())
                .description(subServiceDTO.getDescription())
                .price(subServiceDTO.getPrice())
                .build();
        subServiceService.addSubService(subService, service);
    }


    @PutMapping("/add-expert-to-subService/{expert_id}/{subService_id}")
    void addExpertToSubService(@PathVariable Long expert_id, @PathVariable Long subService_id) {
        expertService.addExpertToSubService(expert_id, subService_id);
    }

    @DeleteMapping("/delete-expert-from-subService/{expert_id}/{subService_id}")
    void deleteExpertFromSubService(@PathVariable Long expert_id, @PathVariable Long subService_id) {
        expertService.deleteExpertOfSubService(expert_id, subService_id);
    }

    @PutMapping("/confirm-expert/{id}")
    void confirmExpertSignup(@PathVariable Long id) {
        expertService.confirmExpert(id);
    }

    @PutMapping("/change-password/{id}/{password}")
    void changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO) {
    adminService.changePassword(passwordDTO.getId(), passwordDTO.getPassword());
    }


    @PutMapping("/edit-subService")
    void editSubService(@RequestBody EditSubServiceDTO subServiceDTO) {
        subServiceService.editSubService(subServiceDTO.getId(), subServiceDTO.getPrice(), subServiceDTO.getDescription());
    }



}
