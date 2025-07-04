package com.example.protech_sso.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SamlRequestCaptureFilter extends OncePerRequestFilter {

    // It is to print header and request parameters of the request coming from Protech
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (isSamlAcsRequest(request)) {

            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.put(headerName, request.getHeader(headerName));
            }

            // 2. Capture parameters
            Map<String, String> parameters = Collections.list(request.getParameterNames())
                    .stream()
                    .collect(Collectors.toMap(
                            name -> name,
                            request::getParameter
                    ));

//            System.out.println("headers::::::: "+headers);
//            System.out.println("parameters::::::: "+parameters);
        }

        filterChain.doFilter(request, response);
    }



    private boolean isSamlAcsRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod()) &&
                request.getRequestURI().contains("/saml2/sso");
    }

}
