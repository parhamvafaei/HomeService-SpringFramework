package com.maktab.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maktab.email.registration.ExpertRegistrationService;
import com.maktab.email.registration.RegistrationRequest;
import com.maktab.entity.Order;
import com.maktab.entity.dto.*;
import com.maktab.entity.person.Expert;
import com.maktab.exception.FileReaderException;
import com.maktab.service.ExpertService;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/expert")
public class ExpertController {

    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final ExpertRegistrationService registrationService;


    @PostMapping("/save-expert")
    String saveExpert(@Valid @RequestBody RegistrationRequest registrationRequest) {
            registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
              return  registrationService.register(registrationRequest);

    }

    @PutMapping("/set-image/{expert_id}")
    void setImage(@PathVariable Long expert_id ,@RequestParam("image") MultipartFile multipartFile){
        if (expertService.checkImage(multipartFile))
            throw new FileReaderException();

        try {
            byte[]   image = multipartFile.getBytes();
            expertService.setProfileImage(image, expert_id);
        } catch (IOException e) {
            throw new FileReaderException(e.getMessage());
        }

    }


    @PutMapping("/change-password")
    void changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO) {
        expertService.changePassword(passwordDTO.getId(), passwordEncoder.encode(passwordDTO.getPassword()));
    }

    @PostMapping("/offer-to-order/{expert_id}/{order_id}")
    void addNewOfferToOrder(@RequestBody OfferDTO offerDTO ,@PathVariable Long expert_id, @PathVariable Long order_id) {
        Expert expert = expertService.findById(expert_id).orElseThrow(NullPointerException::new);
        Order order = orderService.findById(order_id).orElseThrow(NullPointerException::new);
        offerService.addNewOfferToOrder(offerDTO.getPrice(), Duration.ofHours( offerDTO.getDurationTime())
              ,expert,order );
    }

    @GetMapping("/find-orders-to-offer/{expert_id}")
    List<Order> findOrdersToOffer(@PathVariable Long expert_id) {
        return orderService.showRelatedOrdersBySubService(expert_id);
    }

    @GetMapping("/show-expert-rate/{order_id}/{expert_id}")
    Float showExpertRate(@PathVariable Long order_id , @PathVariable Long expert_id) {
        return orderService.showCommentRatingToExpert(order_id,expert_id);
    }

    @PutMapping("/check-expert-account-status/{order_id}/{offer_id}")
    void checkExpertAccountStatus(@PathVariable Long order_id , @PathVariable Long offer_id) {
   orderService.expertAccountStatus(order_id,offer_id);
    }

    @GetMapping("/expert-orders/{expert_id}/{orderStatus}")
    List<Order> expertOrders(@PathVariable Long expert_id,@PathVariable String orderStatus){
        return orderService.expertOrders(expert_id,orderStatus);
    }

    @GetMapping("/show-Budget/{expert_id}")
    public Double showBudget(@PathVariable Long expert_id){
        return expertService.showBudget(expert_id);
    }
}
