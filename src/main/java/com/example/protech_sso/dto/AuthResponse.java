package com.example.protech_sso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public String getWebLoginName() {
        return webLoginName;
    }

    public void setWebLoginName(String webLoginName) {
        this.webLoginName = webLoginName;
    }

    public UUID getDefaultPriceLevelId() {
        return defaultPriceLevelId;
    }

    public void setDefaultPriceLevelId(UUID defaultPriceLevelId) {
        this.defaultPriceLevelId = defaultPriceLevelId;
    }

    public String getDefaultPriceLevel() {
        return defaultPriceLevel;
    }

    public void setDefaultPriceLevel(String defaultPriceLevel) {
        this.defaultPriceLevel = defaultPriceLevel;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(String parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public UUID getParentCustomerId() {
        return parentCustomerId;
    }

    public void setParentCustomerId(UUID parentCustomerId) {
        this.parentCustomerId = parentCustomerId;
    }

    public String getParentCustomer() {
        return parentCustomer;
    }

    public void setParentCustomer(String parentCustomer) {
        this.parentCustomer = parentCustomer;
    }

    public String getAddressComposite() {
        return addressComposite;
    }

    public void setAddressComposite(String addressComposite) {
        this.addressComposite = addressComposite;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public List<WebRole> getWebRoles() {
        return webRoles;
    }

    public void setWebRoles(List<WebRole> webRoles) {
        this.webRoles = webRoles;
    }
}