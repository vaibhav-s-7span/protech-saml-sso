package com.example.protech_sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SecurityConfig {

    @Value("${app.registration-id}")
    private String registrationId;

    @Value("${spring.security.saml2.relyingparty.registration.protech.serviceprovider.entity-id}")
    private String entityId;

    @Value("${spring.security.saml2.relyingparty.registration.protech.serviceprovider.acs-url}")
    private String acsUrl;

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final SamlRequestCaptureFilter samlRequestCaptureFilter;

    public SecurityConfig(SamlRequestCaptureFilter samlRequestCaptureFilter) {
        this.samlRequestCaptureFilter = samlRequestCaptureFilter;
    }

    // RelyingPartyRegistrationRepository : Stores IdP configurations
    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrations() {

        RelyingPartyRegistration registration = RelyingPartyRegistrations
                .fromMetadataLocation("classpath:idp-metadata.xml")
                .registrationId(registrationId)
                .entityId(entityId)  // Your SP's entity ID
                .assertionConsumerServiceLocation(acsUrl)  // Your ACS URL
                .build();
        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            RelyingPartyRegistrationRepository registrations) throws Exception {

        http
                // Add our filter BEFORE SAML processing
                .addFilterBefore(samlRequestCaptureFilter, Saml2WebSsoAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**", "/error", "/public/**").permitAll()
                        .requestMatchers("/saml2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .saml2Login(saml2 -> saml2
                        .relyingPartyRegistrationRepository(registrations)
                        .defaultSuccessUrl("/auth-status", true)
                        .failureUrl("/error?error=authentication_failed")
                        .loginProcessingUrl("/login/saml2/sso/protech")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            log.error("Authentication failed: {}", authException.getMessage());
                            response.sendRedirect("/error?error=authentication_failed");
                        })
                );

        return http.build();
    }

}
