package com.maktab.controller;


import com.maktab.entity.Offer;
import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.dto.ChangePasswordDTO;
import com.maktab.entity.dto.ClientDTO;
import com.maktab.entity.dto.OrderDTO;
import com.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/client")
public class ClientController {
    private final ClientService clientService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final OrderService orderService;
    private final OfferService offerService;


    @PostMapping("/save-client")
    void saveClient(@Valid @RequestBody ClientDTO clientDTO) {
        clientService.signIn(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getEmail(), clientDTO.getPassword());
    }

    @PutMapping("/change-password/{id}/{password}")
    void changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO) {
        clientService.changePassword(passwordDTO.getId(), passwordDTO.getPassword());
    }

    @GetMapping("/find-all-services")
    List<Service> findAllServices() {
        return serviceService.findAll();
    }

    @GetMapping("/find-all-subServices")
    List<SubService> findAllSubServices() {
        return subServiceService.findAll();
    }

    @PostMapping("/add-order/{subService_id}")
    void addOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long subService_id) {
        SubService subService = subServiceService.findById(subService_id).orElseThrow(NullPointerException::new);
        orderService.addOrder(orderDTO.getPrice(), orderDTO.getDescription(), orderDTO.getTime(), orderDTO.getAddress()
                , subService);

    }

    @GetMapping("/sorted-offers-by-price/{order_id}")
    List<Offer> sortedOffersByPrice(@PathVariable Long order_id) {
        return offerService.offersToOrderByPrice(order_id);
    }

    @GetMapping("/sorted-offers-by-expert-rate/{order_id}")
    List<Offer> sortedOffersByExpertScore(@PathVariable Long order_id) {
        return offerService.offersToOrderByExpertRate(order_id);
    }

    @PutMapping("/choose-expert/{offer_id}/{order_id}")
    void selectExpertToOrder(@PathVariable Long offer_id, @PathVariable Long order_id) {
        orderService.selectExpertToOrder(offer_id, order_id);
    }


    @GetMapping("/payment")
    public String payment(){
        return "payment";
    }
    .
}

