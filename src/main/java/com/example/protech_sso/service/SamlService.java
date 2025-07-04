package com.example.protech_sso.service;

import com.easa.apiserver.protech.AuthenticateToken;
import com.easa.apiserver.protech.AuthenticateTokenResponse;
import com.example.protech_sso.dto.AuthResponse;
import com.example.protech_sso.dto.WebRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SamlService {

    @Value("${protech.authSecret}")
    private String securityPassword;

    @Value("${protech.authUrl}")
    private String authUrl;

    @Value("${protech.soapActions.authToken}")
    private String soapAction;


    private final WebServiceTemplate webServiceTemplate;

    SamlService(WebServiceTemplate webServiceTemplate){
        this.webServiceTemplate = webServiceTemplate;
    }

    public Map<String, Object> authStatus(Authentication authentication) {
        Map<String, Object> result = new HashMap<>();
        result.put("authenticated", authentication != null && authentication.isAuthenticated());

        assert authentication != null;
        log.info("authentication getPrincipal: {}", authentication.getPrincipal());
        log.info("authentication getName: {}", authentication.getName());
        log.info("authentication getAuthorities: {}", authentication.getAuthorities());
        log.info("authentication getCredentials: {}", authentication.getCredentials());
        log.info("authentication getDetails: {}", authentication.getDetails());

        result.put("name", authentication.getName());
//            result.put("authorities", authentication.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList()));

        if (authentication.getPrincipal() instanceof Saml2AuthenticatedPrincipal principal) {
            result.put("attributes", principal.getAttributes());
        }

        return result;
    }



    public Map<String, Object> tokenValidate(String userToken) throws Exception {

        if(userToken == null || userToken.isEmpty()) {
            throw new Exception("Please add token in request header");
        }

        String authResult = authToken(userToken).getAuthenticateTokenResult();
        log.info("authResult: {}", authResult);
        if (!authResult.toLowerCase().contains("success")) {
            throw new BadCredentialsException("Authentication failure");
        }

        // token is validated at this point so the user is authenticated
        try {
            ObjectMapper extendedMapper = JsonMapper.builder()
                    .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                    .build();
            AuthResponse authResponse = (AuthResponse) extendedMapper.readValue(authResult, AuthResponse.class);

            return getResponse(authResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    public AuthenticateTokenResponse authToken(String userToken) {
        log.info("authToken method called: {}", userToken);
        AuthenticateToken request = new AuthenticateToken();
        request.setSecurityPassword(securityPassword);
        request.setToken(userToken);
        AuthenticateTokenResponse authenticateTokenResponse = (AuthenticateTokenResponse) webServiceTemplate.
                marshalSendAndReceive(authUrl, request,
                        new SoapActionCallback(soapAction));
        log.info("AuthenticateTokenResult: {}", authenticateTokenResponse.getAuthenticateTokenResult());
        return authenticateTokenResponse;
    }



    private static String defaultIfNull(String value) {
        return value != null ? value : "";
    }

    private static Map<String, Object> getResponse(AuthResponse authResponse){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", authResponse.getStatus());
        responseBody.put("addressCity", defaultIfNull(authResponse.getAddressCity()));
        responseBody.put("isMember", authResponse.isMember());
        responseBody.put("webLoginName", defaultIfNull(authResponse.getWebLoginName()));
        responseBody.put("defaultPriceLevelId", authResponse.getDefaultPriceLevelId());
        responseBody.put("defaultPriceLevel", defaultIfNull(authResponse.getDefaultPriceLevel()));
        responseBody.put("contactNumber", defaultIfNull(authResponse.getContactNumber()));
        responseBody.put("addressState", defaultIfNull(authResponse.getAddressState()));
        responseBody.put("parentCompanyId", defaultIfNull(authResponse.getParentCompanyId()));
        responseBody.put("contactId", authResponse.getContactId());
        responseBody.put("firstName", defaultIfNull(authResponse.getFirstName()));
        responseBody.put("lastName", defaultIfNull(authResponse.getLastName()));
        responseBody.put("fullName", defaultIfNull(authResponse.getFullName()));
        responseBody.put("labelName", defaultIfNull(authResponse.getLabelName()));
        responseBody.put("emailAddress", defaultIfNull(authResponse.getEmailAddress()));
        responseBody.put("addressCountry", defaultIfNull(authResponse.getAddressCountry()));
        responseBody.put("parentCustomerId", authResponse.getParentCustomerId());
        responseBody.put("parentCustomer", defaultIfNull(authResponse.getParentCustomer()));
        responseBody.put("addressComposite", defaultIfNull(authResponse.getAddressComposite()));
        responseBody.put("token", authResponse.getToken());

// Convert webRoles to List<Map<String, Object>>
        List<Map<String, Object>> roleList = new ArrayList<>();
        if (authResponse.getWebRoles() != null) {
            for (WebRole role : authResponse.getWebRoles()) {
                Map<String, Object> roleMap = new HashMap<>();
                roleMap.put("id", role.getId());
                roleMap.put("name", role.getName());
                roleList.add(roleMap);
            }
        }
        responseBody.put("webRoles", roleList);
        return responseBody;
    }



}
