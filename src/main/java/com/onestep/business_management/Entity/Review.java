package com.onestep.business_management.Entity;

import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "productDetailId", nullable = false)
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String comment;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewDate", nullable = false, updatable = false)
    private Date reviewDate;

    @ElementCollection
    private List<String> imageUrls;  

    private String videoUrl;

    @Column(name = "like_count", nullable = true)
    private Integer likeCount = 0;

   
    @PrePersist
    protected void onCreate() {
        this.reviewDate = new Date();
    }
}
