package com.onestep.business_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Documents")
public class Document {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID docId;

    @Column(name = "docNumberOne")
    private String docNumberOne;

    private Date date;

    @Column(name = "docNumberTwo")
    private String docNumberTwo;

    private String companyId;

    @Column(name = "representOne")
    private String representOne;

    @Column(name = "representTwo")
    private String representTwo;

    private Float totalAmount;

    private Float paidAmount;

    private Float paymentPercentage;

    @Column(name = "paymentStatus")
    private boolean paymentStatus;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "createdBy")
    private int createdBy;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private int updatedBy;

    @Column(name = "deletedDate")
    private Date deletedDate;

    @Column(name = "deletedBy")
    private int deletedBy;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DocumentDetail> documentDetails;
}
