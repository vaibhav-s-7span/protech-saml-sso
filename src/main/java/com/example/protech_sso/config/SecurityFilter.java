package com.example.protech_sso.config;

import com.example.protech_sso.service.SamlService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SecurityFilter extends GenericFilterBean {
    private final SamlService samlService;

    SecurityFilter(SamlService samlService) {
        this.samlService = samlService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        try {
            System.out.println("doFilter Called");

            String authToken = ((HttpServletRequest) request).getHeader("authToken");

            if (authToken == null) {
                throw new RuntimeException("authToken should be present in the request header");
            }

            samlService.getUserDetails(authToken); // Sets SecurityContext
            filterChain.doFilter(request, response);

        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }
    }
}