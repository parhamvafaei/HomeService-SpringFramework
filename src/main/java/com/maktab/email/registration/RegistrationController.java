package com.maktab.email.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final ExpertRegistrationService expertRegistrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return expertRegistrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return expertRegistrationService.confirmToken(token);
    }

}
