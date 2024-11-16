package com.onestep.business_management.Service.DocumentService;



import com.onestep.business_management.DTO.DocumentDTO.DocumentDetailRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentDetailResponse;
import com.onestep.business_management.DTO.DocumentDTO.DocumentRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentResponse;
import com.onestep.business_management.DTO.InventoryDTO.InventoryRequest;
import com.onestep.business_management.Entity.Document;
import com.onestep.business_management.Entity.DocumentDetail;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.DocumentRepository;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Service.InventoryService.InventoryService;

import com.onestep.business_management.Service.StoreService.StoreService;
import com.onestep.business_management.Utils.MapperService;
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

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MapperService mapperService;

    @Transactional
    public DocumentResponse createDocument(DocumentRequest documentRequest) {

        Store store = storeRepository.findById(documentRequest.getStoreId()).orElseThrow(
                () -> new ResourceNotFoundException("store with storeId: "+documentRequest.getStoreId()+" not found!")
        );
        // Convert DocumentRequest to Document entity
        Document document = DocumentMapper.INSTANCE.toEntity(documentRequest);
        document.setCreatedDate(new Date());
        document.setStore(store);

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
        return DocumentMapper.INSTANCE.toResponse(savedDocument, mapperService);
    }


    public DocumentResponse getDocumentById(UUID docId) {
        Document savedDocument = documentRepository.findById(docId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        return DocumentMapper.INSTANCE.toResponse(savedDocument, mapperService);
    }


    public List<DocumentResponse> getAllDocumentsByStore(UUID storeId) {
        return documentRepository.findAllByStore(storeId).stream()
                .map(document -> DocumentMapper.INSTANCE.toResponse(document, mapperService))
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
