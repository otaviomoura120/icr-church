package com.devhouse.adapters.outbound.repositoryJpa.entities;

import com.devhouse.core.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AddressEntityJpa {

    private String street;
    @Column(name = "zip_code")
    private String zipCode;
    private String country;
    private String neighborhood;
    private String state;
    @Column(name = "city_name")
    private String city;

    public AddressEntityJpa() {
    }

    public AddressEntityJpa(String street, String zipCode, String country, String state, String city, String neighborhood) {
        this.street = street;
        this.zipCode = zipCode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() { return neighborhood; }

    public static AddressEntityJpa from(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressEntityJpa(
            address.getStreet(),
            address.getZipCode(),
            address.getCountry(),
            address.getState(),
            address.getCity(),
            address.getNeighborhood()
        );
    }

    public Address toModel() {
        return new Address(
            this.street,
            this.zipCode,
            this.country,
            this.state,
            this.city,
            this.neighborhood
        );
    }
    
    // Setters for JPA
    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
