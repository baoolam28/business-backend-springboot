package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    private String imageId;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = true)
    private Product product;

    @OneToOne()
    @JoinColumn(name = "userId", nullable = true)
    private User user;

}
