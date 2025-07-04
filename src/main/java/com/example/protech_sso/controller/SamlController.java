package com.example.protech_sso.controller;

import com.example.protech_sso.service.SamlService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class SamlController {

    private final SamlService samlService;
    private static final Logger log = LoggerFactory.getLogger(SamlController.class);

    SamlController(SamlService samlService){
        this.samlService = samlService;
    }

    @GetMapping("/auth-status")
    public Map<String, Object> authStatus(Authentication authentication) {
        return samlService.authStatus(authentication);
    }

    @GetMapping("/validate-token")
    public Map<String, Object> validateToken(@RequestParam("Token") String userToken) throws Exception {
        return samlService.tokenValidate(userToken);
    }


    @GetMapping("/error")
    public String handleError(@RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            log.error("Error occurred: {}", error);
            return "Error: " + error;
        }
        return "An unexpected error occurred. Please try again.";
    }
}
