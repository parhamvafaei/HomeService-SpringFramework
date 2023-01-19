package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.email.registration.token.ConfirmationToken;
import com.maktab.email.registration.token.ConfirmationTokenService;
import com.maktab.entity.SubService;
import com.maktab.entity.dto.ExpertFilterDTO;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.entity.person.Role;
import com.maktab.exception.*;

import com.maktab.repository.ExpertRepository;
import com.maktab.service.ExpertService;
import com.maktab.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class ExpertServiceImpl extends BaseServiceImpl<Expert, ExpertRepository> implements ExpertService {


    private final SubServiceService subServiceService;
    private final ConfirmationTokenService confirmationTokenService;
    @PersistenceContext
    private EntityManager em;

    public ExpertServiceImpl(ExpertRepository repository, SubServiceService subServiceService, ConfirmationTokenService confirmationTokenService) {
        super(repository);

        this.subServiceService = subServiceService;

        this.confirmationTokenService = confirmationTokenService;
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


    @Transactional
    @Override
    public void setProfileImage(byte[] image, Expert expert) {

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

    @Transactional
    @Override
    public void addExpertToSubService(Long expertId, Long subServiceId) {
        Expert expert = findById(expertId).orElseThrow(NullPointerException::new);
        SubService subService = subServiceService.findById(subServiceId).orElseThrow(NullPointerException::new);
        if (subService.getExperts().contains(expert))
            throw new ExpertAddException("expert already added !");
        if (expert.getExpertStatus() == ExpertStatus.CONFIRMED && subServiceService.checkSubServiceByName(subService.getName())) {

            List<SubService> subServices = expert.getSubServices();
            subServices.add(subService);
            expert.setSubServices(subServices);

            saveOrUpdate(expert);

        } else
            throw new ExpertAddException("add expert to subService exception");
    }


    @Transactional
    @Override
    public void deleteExpertOfSubService(Long expertId, Long subServiceId) {
        Expert expert = findById(expertId).orElseThrow(NullPointerException::new);
        SubService subService = subServiceService.findById(subServiceId).orElseThrow(NullPointerException::new);

        if (subService.getExperts().stream().anyMatch(expert1 -> expert1.getEmail().equals(expert.getEmail()))) {
            List<SubService> subServices = expert.getSubServices();
            subServices.remove(subService);
            expert.setSubServices(subServices);


            saveOrUpdate(expert);


        } else
            throw new DeleteExpertException("delete expert of subService failed!");
    }

    @Transactional
    @Override
    public String signIn(String firstName, String lastName, String Email, String password, byte[] image) {

        if (repository.existsByEmail(Email)) {
            throw new PersonSignInException("this expert already exist");
        }

        Expert expert = new Expert(firstName, lastName, Email, password, Role.ROLE_EXPERT, ExpertStatus.AWAITING_CONFIRMATION);
        setProfileImage(image, expert);
        saveOrUpdate(expert);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                expert
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);


        return token;

    }

    @Override
    public boolean checkImage(MultipartFile file) {

        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        if (!(split[split.length - 1].equals("jpg")))
            throw new FileReaderException("wrong format");

        if (file.getSize() > 300_000)
            throw new FileReaderException("image size is more than standard");


        return true;
    }

    @Override
    public List<Expert> filterExpert(ExpertFilterDTO expertDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Expert> query = criteriaBuilder.createQuery(Expert.class);
        Root<Expert> root = query.from(Expert.class);

        if (expertDTO.getFirstname() != null)
            predicateList.add(criteriaBuilder.like(root.get("firstName"), "%" + expertDTO.getFirstname() + "%"));

        if (expertDTO.getLastname() != null)
            predicateList.add(criteriaBuilder.like(root.get("lastName"), "%" + expertDTO.getLastname() + "%"));

        if (expertDTO.getEmail() != null)
            predicateList.add(criteriaBuilder.like(root.get("email"), "%" + expertDTO.getEmail() + "%"));

        if (expertDTO.getRating() != null)
            predicateList.add(criteriaBuilder.equal(root.get("rating"), expertDTO.getRating()));

        Optional<SubService> subServiceOptional = subServiceService.findByName(expertDTO.getSubService());
        if (subServiceOptional.isPresent()) {
            if (expertDTO.getSubService() != null)
                predicateList.add(criteriaBuilder.in(root.get("subServices")).value(subServiceOptional.get()));
        }


        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.select(root).where(predicates);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Expert> expertReporter(LocalDateTime signInTime, Integer ordersSet, Integer ordersDone) {
        return repository.filterExpert(signInTime, ordersSet, ordersDone);
    }

    @Override
    public Double showBudget(Long expert_id){
        Expert expert = findById(expert_id).orElseThrow(NullPointerException::new);
        return expert.getTotalMoney();
    }
}