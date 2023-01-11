package com.maktab.controller;

import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.dto.*;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Expert;
import com.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final ClientService clientService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/save-admin")
    void saveAdmin(@Valid @RequestBody AdminSignInDTO adminDTO) {
        adminService.createAdmin(adminDTO.getFirstName(), adminDTO.getLastName(), adminDTO.getEmail(), passwordEncoder.encode(adminDTO.getPassword()));
    }


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
        SubService subService=mapper.map(subServiceDTO,SubService.class);
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
    adminService.changePassword(passwordDTO.getId(), passwordEncoder.encode(passwordDTO.getPassword()));
    }


    @PutMapping("/edit-subService")
    void editSubService(@RequestBody EditSubServiceDTO subServiceDTO) {
        subServiceService.editSubService(subServiceDTO.getId(), subServiceDTO.getPrice(), subServiceDTO.getDescription());
    }

    @GetMapping("/filter-client")
    List<Client> filterCustomer(@RequestBody ClientFilterDTO clientDTO){
        return clientService.filterClient(clientDTO);
    }

    @GetMapping("/filter-expert")
    List<Expert> filterExpert(@RequestBody ExpertFilterDTO expertDTO){
        return expertService.filterExpert(expertDTO);
    }

}
