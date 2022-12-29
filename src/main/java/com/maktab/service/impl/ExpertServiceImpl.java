package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Comment;
import com.maktab.entity.Order;
import com.maktab.entity.OrderStatus;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.*;

import com.maktab.repository.ExpertRepository;
import com.maktab.service.ExpertService;
import com.maktab.service.OrderService;
import com.maktab.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;


@Service
public class ExpertServiceImpl extends BaseServiceImpl<Expert, ExpertRepository> implements ExpertService {


    private final SubServiceService subServiceService;

    public ExpertServiceImpl(ExpertRepository repository, SubServiceService subServiceService) {
        super(repository);

        this.subServiceService = subServiceService;
    }

    @Transactional
    @Override
    public void changePassword(Long id, String password) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);

        try {
            expert.setPassword(password);
        } catch (Exception e) {
            throw new ValidationException();
        }
        saveOrUpdate(expert);

    }

    //how to check format
    @Transactional
    @Override
    public void setProfileImage(byte[] image, Long id) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);

        expert.setImage(image);
        saveOrUpdate(expert);

    }


    @Transactional
    @Override
    public Long confirmExpert(Long id) {
        Expert expert = findById(id).orElseThrow(NullPointerException::new);
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        saveOrUpdate(expert);
        return expert.getId();
    }

    //show orders to expert
    @Transactional
    @Override
    public void addExpertToSubService(Expert expert, SubService subService) {
        if (subService.getExperts().contains(expert))
            throw new ExpertAddException("expert already added !");
        if (expert.getExpertStatus() == ExpertStatus.CONFIRMED && subServiceService.checkSubServiceByName(subService.getName())) {

            List<SubService> subServices = expert.getSubServices();
            subServices.add(subService);
            expert.setSubServices(subServices);

            saveOrUpdate(expert);

        } else
            throw new ExpertAddException();
    }


    @Transactional
    @Override
    public void deleteExpertOfSubService(Expert expert, SubService subService) {

        if (subService.getExperts().stream().anyMatch(expert1 -> expert1.equals(expert))) {
            List<SubService> subServices = expert.getSubServices();
            subServices.remove(subService);
            expert.setSubServices(subServices);


            saveOrUpdate(expert);


        } else
            throw new DeleteExpertException("delete expert of subService failed!");
    }


    @Override
    public Long signIn(String firstName, String lastName,String Email, String password, byte[] image) {

//        if (repository.existsByEmail(Email)) {
//            throw new PersonSignInException("this expert already exist");
//        }
        Expert expert = new Expert();
        Expert.builder().firstName(firstName).lastName(lastName).Email(Email).password(password)
                .build();

        setProfileImage(image, expert.getId());
        saveOrUpdate(expert);
        return expert.getId();
    }
//@Transactional
//    @Override
//    public void setComment(Long expertId, Long orderId, Float rating, String description) {
//        Expert expert = findById(expertId).orElseThrow(NotFoundPersonException::new);
//        Order order = orderService.findById(orderId).orElseThrow(NullPointerException::new);
//        if (!(order.getOrderStatus() == OrderStatus.PAID))
//            throw new OrderStatusConditionException();
//        if (!(rating >= 0 || rating <= 5))
//            throw new ValidationException("not in range ");
//        Comment comment=new Comment(rating,description,order);
//    }


  /*  @Transactional
    @Override
    public void changeEmail(Long id,String email) {
        Optional<Expert> expert = repository.findById(id);
        if (expert.isPresent()) {
            try {
                expert.get().setEmail(email);
            }catch (Exception e) {
                throw new ValidationException();
            }
                saveOrUpdate(expert.get());

        } else
            throw new NullPointerException();

    }*/
}