package com.onestep.business_management.Entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String comment;

    private Date reviewDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewDate", nullable = false, updatable = false)
    @PrePersist
    protected void onCreate() {
        this.reviewDate = new Date();
    }
}
