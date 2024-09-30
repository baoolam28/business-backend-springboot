package com.onestep.business_management.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Stores")
public class Store {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID storeId;

    @Column(name = "storeName", length = 100, nullable = false, columnDefinition = "NVARCHAR(100)")
    private String storeName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId", nullable = true)
    private Image storeAvatar;

    @Column(name = "storeLocation", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String storeLocation;

    @Column(name = "storeDescription", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String storeDescription;

    @Column(name = "storeEmail", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String storeEmail;

    @Column(name = "storeBankAccount", length = 30, nullable = true, columnDefinition = "NVARCHAR(30)")
    private String storeBankAccount;

    @Column(name = "pickupAddress", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String pickupAddress;

    @Column(name = "storeTaxCode", length = 20, nullable = true, columnDefinition = "NVARCHAR(20)")
    private String storeTaxCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User storeManager;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "store")
    private List<Inventory> inventories = new ArrayList<>();
}
