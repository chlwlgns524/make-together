package com.maketogether.membership.userinfo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    @Column(nullable = false, length = 6)
    private String zipcode;

    @Column(nullable = false, length = 10)
    private String city;

    @Column(nullable = false, length = 50)
    private String street;

    @Builder
    public Address(String zipcode, String city, String street) {
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
    }

}
