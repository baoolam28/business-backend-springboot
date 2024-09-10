package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;
    @Column(name = "supplierName")
    private String supplierName;
    private String email;
    private String phone;
    private String fax;
    private String address;
    @Column(name = "createdDate")
    private Date createdDate;
    private boolean disabled;
}
