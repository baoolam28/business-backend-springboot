package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.OriginRequest;
import com.onestep.business_management.DTO.OriginResponse;
import com.onestep.business_management.Service.OriginService.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/origins")
public class OriginController {

    @Autowired
    private OriginService originService;

    @PostMapping
    public ResponseEntity<OriginResponse> createOrigin(@RequestBody OriginRequest originRequest) {
        try {
            OriginResponse response = originService.createOrigin(originRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<OriginResponse>> getAllOrigins() {
        try {
            List<OriginResponse> response = originService.getAllOrigins();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{originId}")
    public ResponseEntity<OriginResponse> getOriginById(@PathVariable Integer originId) {
        try {
            OriginResponse response = originService.getOriginById(originId);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{originId}")
    public ResponseEntity<OriginResponse> updateOrigin(@PathVariable Integer originId, @RequestBody OriginRequest originRequest) {
        try {
            OriginResponse response = originService.updateOrigin(originId, originRequest);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{originId}")
    public ResponseEntity<Void> deleteOrigin(@PathVariable Integer originId) {
        try {
            boolean isDeleted = originService.deleteOrigin(originId);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
