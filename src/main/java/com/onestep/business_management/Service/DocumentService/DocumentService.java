package com.onestep.business_management.Service.DocumentService;



import com.onestep.business_management.DTO.DocumentDTO.DocumentDetailRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentDetailResponse;
import com.onestep.business_management.DTO.DocumentDTO.DocumentRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentResponse;
import com.onestep.business_management.DTO.InventoryDTO.InventoryRequest;
import com.onestep.business_management.Entity.Document;
import com.onestep.business_management.Entity.DocumentDetail;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Repository.DocumentRepository;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Service.InventoryService.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    @Transactional
    public DocumentResponse createDocument(DocumentRequest documentRequest) {
        // Convert DocumentRequest to Document entity
        Document document = DocumentMapper.INSTANCE.toEntity(documentRequest);
        document.setCreatedDate(new Date());

        // Set DocumentDetails
        List<DocumentDetail> documentDetails = documentRequest.getDocumentDetails().stream().map(detailRequest -> {
            DocumentDetail detail = DocumentMapper.INSTANCE.toEntity(detailRequest);
            Product product = productRepository.findByBarcode(detailRequest.getBarcode())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            detail.setProduct(product);
            detail.setDocument(document);
            return detail;
        }).collect(Collectors.toList());

        document.setDocumentDetails(documentDetails);

        // Update Inventory for each DocumentDetail
    documentDetails.forEach(detail -> {
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setBarcode(detail.getProduct().getBarcode());
        inventoryRequest.setQuantityInStock(detail.getQuantity());
        inventoryService.saveInventory(inventoryRequest); // Adjust the inventory quantity
    });

        Document savedDocument = documentRepository.save(document);
        return DocumentMapper.INSTANCE.toResponse(savedDocument);
    }

    // @Transactional
    // public DocumentResponse updateDocument(UUID docId, DocumentRequest documentRequest) {
    //     Document document = documentRepository.findById(docId)
    //             .orElseThrow(() -> new RuntimeException("Document not found"));

    //     // Update fields of Document entity
    //     DocumentMapper.INSTANCE.toEntity(documentRequest, document);
    //     document.setUpdatedDate(new Date());

    //     // Update DocumentDetails
    //     List<DocumentDetail> updatedDetails = documentRequest.getDocumentDetails().stream().map(detailRequest -> {
    //         DocumentDetail detail = DocumentMapper.INSTANCE.toEntity(detailRequest);
    //         Product product = productRepository.findById(detailRequest.getBarcode())
    //                 .orElseThrow(() -> new RuntimeException("Product not found"));
    //         detail.setProduct(product);
    //         detail.setDocument(document);
    //         return detail;
    //     }).collect(Collectors.toList());

    //     document.setDocumentDetails(updatedDetails);

    //     Document updatedDocument = documentRepository.save(document);
    //     return DocumentMapper.INSTANCE.toResponse(updatedDocument);
    // }

    public DocumentResponse getDocumentById(UUID docId) {
        return documentRepository.findById(docId)
                .map(DocumentMapper.INSTANCE::toResponse)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(DocumentMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteDocument(UUID docId) {
        if (documentRepository.existsById(docId)) {
            documentRepository.deleteById(docId);
            return true;
        }
        return false;
    }
}
