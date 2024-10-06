package com.onestep.business_management.Entity;

import java.util.UUID;

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
    private Integer addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "fullName", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String fullName;

    @Column(name = "phoneNumber", length = 14, nullable = true, columnDefinition = "NVARCHAR(20)")
    private String phoneNumber;

    @Column(name = "address", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "wardCode", length = 10, nullable = true, columnDefinition = "NVARCHAR(10)")
    private String wardCode;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;

}
