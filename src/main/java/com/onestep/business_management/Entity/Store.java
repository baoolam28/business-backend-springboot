package com.onestep.business_management.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    private String storeName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_image_id", referencedColumnName = "imageId")
    private Image storeAvatar;   

    private String storeLocation;

    private String storeDescription;

    private String storeEmail;

    private String storeBankAccount;

    private String pickupAddress;

    private String storeTaxCode;


    @ManyToOne
    @JoinColumn(name = "storeManagerId")
    private User storeManager;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
