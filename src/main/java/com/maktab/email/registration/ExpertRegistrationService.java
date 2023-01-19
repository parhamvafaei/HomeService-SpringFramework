package com.maktab.email.registration;


import com.maktab.email.email.EmailSender;
import com.maktab.email.registration.token.ConfirmationToken;
import com.maktab.email.registration.token.ConfirmationTokenService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.repository.ExpertRepository;
import com.maktab.repository.PersonRepository;
import com.maktab.service.AdminService;
import com.maktab.service.ExpertService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ExpertRegistrationService {

    private final ExpertService expertService;

    private final PersonRepository<Expert> personRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    private final ExpertRepository expertRepository;


    public String register(RegistrationRequest request, byte[] image) {


        String token =expertService.signIn(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                image
        );

        String link = "http://localhost:8080/api/v1/admin/expert-confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        personRepository.enableAppUser(
                confirmationToken.getPerson().getEmail());
expertRepository.confirmedExpert(ExpertStatus.CONFIRMED, confirmationToken.getPerson().getEmail());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "Hey "+name+"\n" +
                " click link below to confirm your email :" +"\n"+
                link ;
    }

}
