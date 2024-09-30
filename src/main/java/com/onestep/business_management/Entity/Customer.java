package com.onestep.business_management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "name", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String name;
    @Column(name = "email", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String email;
    @Column(name = "phone", length = 13, nullable = true, columnDefinition = "NVARCHAR(13)")
    private String phone;
    @Column(name = "address", length = 100, nullable = true, columnDefinition = "NVARCHAR(100)")
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderOffline> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false) // Foreign key to Store table
    private Store store;

}
