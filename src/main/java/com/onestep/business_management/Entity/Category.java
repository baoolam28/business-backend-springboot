package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "categoryName", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String categoryName;
    @Column(name = "categoryDescription", length = 100, nullable = true, columnDefinition = "NVARCHAR(100)")
    private String categoryDescription;
    private boolean disabled;
}
