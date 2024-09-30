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
    @Column(name = "supplierName", length = 100, nullable = true, columnDefinition = "NVARCHAR(100)")
    private String supplierName;
    
    @Column(name = "email", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String email;
    private String phone;
    private String fax;
    @Column(name = "address", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String address;
    @Column(name = "createdDate")
    private Date createdDate;
    private boolean disabled;
}
