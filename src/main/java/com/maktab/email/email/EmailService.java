package com.maktab.email.email;


import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class EmailService implements EmailSender {



    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {

try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("p.vafaeei@gmail.com");
        message.setTo(to);
        message.setText(email);
        message.setSubject("Confirm your email");
        mailSender.send(message);
    } catch (Exception e) {

            throw new IllegalStateException("failed to send email");
        }
    }
}
