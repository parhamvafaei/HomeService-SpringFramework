package com.maktab.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maktab.entity.Order;
import com.maktab.entity.dto.*;
import com.maktab.entity.person.Expert;
import com.maktab.exception.FileReaderException;
import com.maktab.service.ExpertService;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/expert")
public class ExpertController {

    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/save-expert")
    void saveExpert(@Valid @RequestParam("expert") String expertJSON, @RequestParam("image") MultipartFile multipartFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ExpertDTO expertDTO = objectMapper.readValue(expertJSON, ExpertDTO.class);
            if (expertService.checkImage(multipartFile)) {


                expertService.signIn(expertDTO.getFirstName(), expertDTO.getLastName(), expertDTO.getEmail()
                        , expertDTO.getPassword(), multipartFile.getBytes());
            }
        } catch (Exception e) {
            throw new FileReaderException();
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

}
