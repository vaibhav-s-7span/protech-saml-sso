package com.example.protech_sso.Authentication;

import com.example.protech_sso.dto.WebRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("address1_city")
    private String addressCity;

    @JsonProperty("pa_member")
    private boolean member;

    @JsonProperty("pa_webloginname")
    private String webLoginName;

    @JsonProperty("defaultpricelevelid_id")
    private UUID defaultPriceLevelId;

    @JsonProperty("defaultpricelevelid")
    private String defaultPriceLevel;

    @JsonProperty("pa_contactnumber")
    private String contactNumber;

    @JsonProperty("address1_stateorprovince")
    private String addressState;

    @JsonProperty("easa_parentcompanyid")
    private String parentCompanyId;

    @JsonProperty("contactid")
    private UUID contactId;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("pa_labelname")
    private String labelName;

    @JsonProperty("emailaddress1")
    private String emailAddress;

    @JsonProperty("address1_country")
    private String addressCountry;

    @JsonProperty("parentcustomerid_id")
    private UUID parentCustomerId;

    @JsonProperty("parentcustomerid")
    private String parentCustomer;

    @JsonProperty("address1_composite")
    private String addressComposite;

    @JsonProperty("pa_token")
    private UUID token;

    @JsonProperty("pa_webroles")
    @Builder.Default
    private List<WebRole> webRoles = List.of();
}