package com.onestep.business_management.Service.SupplierService;

import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.DTO.SupplierDTO.SupplierRequest;
import com.onestep.business_management.DTO.SupplierDTO.SupplierResponse;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.Supplier;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Repository.SupplierRepository;
import com.onestep.business_management.Service.ProductService.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    private StoreRepository storeRepository;
    public SupplierResponse create_supplier(SupplierRequest supplierRequest){

        Store store = storeRepository.findById(supplierRequest.getStoreId()).orElseThrow(
                () -> new ResourceNotFoundException("Store with id = "+supplierRequest.getStoreId()+" not found!")
        );

        Supplier newSupplier = SupplierMapper.INSTANCE.toEntity(supplierRequest);
        newSupplier.setCreatedDate(new Date());
        newSupplier.setDisabled(false);
        newSupplier.setStore(store);
        System.out.println(newSupplier.toString());
        Supplier savedSupplier = supplierRepository.save(newSupplier);
        return SupplierMapper.INSTANCE.toResponse(savedSupplier);
    }

    public List<SupplierResponse> get_all() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public SupplierResponse get_byName(String supplierName){
        Supplier supplier = supplierRepository.findBySupplierName(supplierName).orElse(null);
        return SupplierMapper.INSTANCE.toResponse(supplier);
    }

    public List<SupplierResponse> searchByKeyword(String keyword) {
        List<Supplier> suppliers = supplierRepository.searchByKeyword(keyword);
        return suppliers.stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByEmail(String email) {
        List<Supplier> suppliers = supplierRepository.findByEmail(email);
        return suppliers.stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByPhone(String phone) {
        List<Supplier> suppliers = supplierRepository.findByPhone(phone);
        return suppliers.stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByFax(String fax) {
        List<Supplier> suppliers = supplierRepository.findByFax(fax);
        return suppliers.stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByAddress(String address) {
        List<Supplier> suppliers = supplierRepository.findByAddress(address);
        return suppliers.stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public SupplierResponse update_supplier(SupplierRequest supplierRequest, Integer supplierId){
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        if (supplier!= null){
            supplier.setSupplierName(supplierRequest.getSupplierName());
            supplier.setEmail(supplierRequest.getEmail());
            supplier.setPhone(supplierRequest.getPhone());
            supplier.setFax(supplierRequest.getFax());
            supplier.setAddress(supplierRequest.getAddress());
            Supplier updatedSupplier = supplierRepository.save(supplier);
            return SupplierMapper.INSTANCE.toResponse(updatedSupplier);
        }
        return null;
    }

    public List<SupplierResponse> getAllByStore(UUID storeId) {
        return supplierRepository.findByStore(storeId).stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());

    }
}
