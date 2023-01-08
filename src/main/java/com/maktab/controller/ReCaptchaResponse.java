package com.maktab.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReCaptchaResponse {

    private Boolean success;
    private String challenge_ts;
    private String hostname;
    private String errorCodes;

}
