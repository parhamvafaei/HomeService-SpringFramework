package com.maktab.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maktab.entity.Offer;
import com.maktab.entity.Order;
import com.maktab.entity.dto.ChangePasswordDTO;
import com.maktab.entity.dto.ClientDTO;
import com.maktab.entity.dto.ExpertDTO;
import com.maktab.entity.dto.OfferDTO;
import com.maktab.entity.person.Expert;
import com.maktab.exception.FileReaderException;
import com.maktab.service.ExpertService;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/expert")
public class ExpertController {

    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;

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


    @PutMapping("/change-password/{id}/{password}")
    void changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO) {
        expertService.changePassword(passwordDTO.getId(), passwordDTO.getPassword());
    }

    @PostMapping("/offer-to-order/{expert_id}/{order_id}")
    void addNewOfferToOrder(@RequestBody OfferDTO offerDTO ,@PathVariable Long expert_id, @PathVariable Long order_id) {
        Expert expert = expertService.findById(expert_id).orElseThrow(NullPointerException::new);
        Order order = orderService.findById(order_id).orElseThrow(NullPointerException::new);
        offerService.addNewOfferToOrder(offerDTO.getPrice(), offerDTO.getDurationTime()
              ,expert,order );
    }

    @GetMapping("/ind-orders-to-offer")
    List<Order> findOrdersToOffer(Long expertId) {
        return orderService.showRelatedOrdersBySubService(expertId);
    }
}
