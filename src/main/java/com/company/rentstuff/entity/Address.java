package com.company.rentstuff.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JmixEntity
@Embeddable
public class Address {
    @NotBlank
    @Column(name = "STREET", nullable = false)
    @NotNull
    private String street;

    @Column(name = "POST_CODE")
    private String postCode;

    @Column(name = "CITY")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}