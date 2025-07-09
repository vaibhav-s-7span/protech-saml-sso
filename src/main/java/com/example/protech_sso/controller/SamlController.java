package com.example.protech_sso.controller;

import com.example.protech_sso.service.SamlService;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SamlController {

    private final SamlService samlService;

    SamlController(SamlService samlService){
        this.samlService = samlService;
    }

    @GetMapping("/non-member")
    @PreAuthorize("hasRole('ROLE_NON_MEMBER')")
    public Map<String, Object> nonMemberEndpoint() {
        Map<String, Object> response = SamlService.getUserFromSecurityContext();
        response.put("message", "Non-Member User Role!");
        return response;
    }

    @GetMapping("/registered-users")
    @PreAuthorize("hasRole('ROLE_REGISTERED_USERS')")
    public Map<String, Object> registeredUsersEndpoint() {
        Map<String, Object> response = SamlService.getUserFromSecurityContext();
        response.put("message", "Registered User Role!");
        return response;
    }


    @GetMapping("/public")
    @PermitAll
    public Map<String, Object> publicEndpoint() {
        Map<String, Object> response = SamlService.getUserFromSecurityContext();
        response.put("message", "Public User Role!");
        return response;
    }

    @GetMapping("/both-roles")
    @PreAuthorize("hasAnyRole('ROLE_NON_MEMBER', 'ROLE_REGISTERED_USERS')")
    public Map<String, Object> bothRolesEndpoint() {
        Map<String, Object> response = SamlService.getUserFromSecurityContext();
        response.put("message", "Non-Member or Registered Users Role!");
        return response;
    }

    @GetMapping("/whoami")
    public String whoAmI() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "I am: " + auth.getName() + " with roles: " + auth.getAuthorities();
    }


    @GetMapping("/no-roles")
    @PreAuthorize("hasRole('XYZ')")
    public Map<String, Object> noRolesEndpoint() {
        Map<String, Object> response = SamlService.getUserFromSecurityContext();
        response.put("message", "Error!! No One Role Have Access!");
        return response;
    }

}
