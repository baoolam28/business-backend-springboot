package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ShippingAddresses")

public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @Column(name = "user_id")
    private User userId;

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "address_line", length = 255)
    private String addressLine;

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "postal_code", length = 50)
    private String postalCode;

    @Column(name = "country", length = 255)
    private String country;

    @Column(name = "is_default")
    private boolean isDefault;

}
