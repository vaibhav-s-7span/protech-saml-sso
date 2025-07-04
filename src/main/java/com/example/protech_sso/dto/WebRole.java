package com.example.protech_sso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebRole {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
}