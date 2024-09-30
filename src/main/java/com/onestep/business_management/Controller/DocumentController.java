package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.DocumentDTO.DocumentRequest;
import com.onestep.business_management.DTO.DocumentDTO.DocumentResponse;
import com.onestep.business_management.Service.DocumentService.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(@RequestBody DocumentRequest documentRequest) {
        try {
            DocumentResponse response = documentService.createDocument(documentRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping("/{docId}")
    // public ResponseEntity<DocumentResponse> updateDocument(@PathVariable UUID docId,
    //         @RequestBody DocumentRequest documentRequest) {
    //     try {
    //         DocumentResponse response = documentService.updateDocument(docId, documentRequest);
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping("/{docId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable UUID docId) {
        try {
            DocumentResponse response = documentService.getDocumentById(docId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        try {
            List<DocumentResponse> response = documentService.getAllDocuments();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID docId) {
        try {
            boolean isDeleted = documentService.deleteDocument(docId);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
