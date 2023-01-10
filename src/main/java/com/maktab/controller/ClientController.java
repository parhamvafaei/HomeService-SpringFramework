package com.maktab.controller;


import com.maktab.entity.*;
import com.maktab.entity.dto.ChangePasswordDTO;
import com.maktab.entity.dto.ClientDTO;
import com.maktab.entity.dto.CommentDTO;
import com.maktab.entity.dto.OrderDTO;
import com.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/client")
public class ClientController {
    private final ClientService clientService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;
    private final OrderService orderService;
    private final OfferService offerService;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;



    @PostMapping("/save-client")
    @ResponseBody
    void saveClient(@Valid @RequestBody ClientDTO clientDTO) {
        clientService.signIn(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getEmail(), clientDTO.getPassword());
    }

    @PutMapping("/change-password/{id}/{password}")
    @ResponseBody
    void changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO) {
        clientService.changePassword(passwordDTO.getId(), passwordDTO.getPassword());
    }

    @GetMapping("/find-all-services")
    @ResponseBody
    List<Service> findAllServices() {
        return serviceService.loadServices();
    }

    @GetMapping("/find-all-subServices")
    @ResponseBody
    List<SubService> findAllSubServices() {
        return subServiceService.loadSubServices();
    }

    //time problem
    @PostMapping("/add-order/{subService_id}")
    @ResponseBody
    void addOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long subService_id) {
        SubService subService = subServiceService.findById(subService_id).orElseThrow(NullPointerException::new);
        orderService.addOrder(orderDTO.getPrice(), orderDTO.getDescription(), orderDTO.getTime(), orderDTO.getAddress()
                , subService);

    }

    @GetMapping("/sorted-offers-by-price/{order_id}")
    @ResponseBody
    List<Offer> sortedOffersByPrice(@PathVariable Long order_id) {
        return offerService.offersToOrderByPrice(order_id);
    }

    @GetMapping("/sorted-offers-by-expert-rate/{order_id}")
    @ResponseBody
    List<Offer> sortedOffersByExpertScore(@PathVariable Long order_id) {
        return offerService.offersToOrderByExpertRate(order_id);
    }

    @GetMapping("/show-related-orders/{expert_id}")
    @ResponseBody
    List<Order> showRelatedOrders(@PathVariable Long expert_id) {
        return orderService.showRelatedOrdersBySubService(expert_id);
    }


    @PutMapping("/order-to-start/{order_id}")
    @ResponseBody
    void orderStatusToStarted( @PathVariable Long order_id) {
        orderService.changeOrderStatusToStarted(order_id);
    }

    @PutMapping("/order-to-done/{order_id}/{duration_time}")
    @ResponseBody
    void orderStatusToDone( @PathVariable Long order_id ,@PathVariable Long duration_time) {
        orderService.changeOrderStatusToDone(order_id, Duration.ofHours(duration_time));
    }


    @PutMapping("/choose-expert/{offer_id}/{order_id}")
    @ResponseBody
    void selectExpertToOrder(@PathVariable Long offer_id, @PathVariable Long order_id) {
        orderService.selectExpertToOrder(offer_id, order_id);
    }


    @PostMapping("/save-comment/{order_id}")
    @ResponseBody
    void setComment(@RequestBody CommentDTO commentDTO, @PathVariable Long order_id) {
        Comment comment=mapper.map(commentDTO,Comment.class);
orderService.setComment(comment,order_id);
    }

    @PutMapping("/pay-from-credit/{order_id}/{client_id}")
    @ResponseBody
    void payFromCredit( @PathVariable Long order_id,@PathVariable Long client_id) {
        orderService.payFromCredit(order_id,client_id);
    }

    @GetMapping("/payment")
    public String payment( Model model , Long order_id){
        Order order = orderService.findById(order_id).orElseThrow(NullPointerException::new);
        model.addAttribute("orderPrice", order.getPrice());
        return "payment";
    }


        @PostMapping("/payment")
        @ResponseBody
        public String test(@RequestParam("order_id") String order_id, @RequestParam("g-recaptcha-response") String captcha){
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6Lf53N4jAAAAAAH2qeDC50aHrbxVb97JgKgsq3GT&response=" + captcha;

            ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + params, HttpMethod.POST,
                null,ReCaptchaResponse.class).getBody();
        if(reCaptchaResponse.getSuccess()){
            orderService.payFromCard(Long.valueOf(order_id));
            return "done";
        }
        return "invalid captcha";
    }
}

