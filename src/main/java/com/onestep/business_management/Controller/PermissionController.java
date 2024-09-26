package com.onestep.business_management.Controller;

import com.onestep.business_management.DTO.PermissionRequest;
import com.onestep.business_management.DTO.PermissionResponse;
import com.onestep.business_management.Service.PermissionService.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        try {
            PermissionResponse response = permissionService.createPermission(permissionRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        try {
            List<PermissionResponse> response = permissionService.getAllPermissions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Integer permissionId) {
        try {
            PermissionResponse response = permissionService.getPermissionById(permissionId);
            return response != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionResponse> updatePermission(@PathVariable Integer permissionId, @RequestBody PermissionRequest permissionRequest) {
        try {
            PermissionResponse response = permissionService.updatePermission(permissionId, permissionRequest);
            return response != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable Integer permissionId) {
        try {
            boolean deleted = permissionService.deletePermission(permissionId);
            return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
