package com.onestep.business_management.Service.SupplierService;

import com.onestep.business_management.DTO.SupplierDTO.SupplierRequest;
import com.onestep.business_management.DTO.SupplierDTO.SupplierResponse;
import com.onestep.business_management.Entity.Supplier;
import com.onestep.business_management.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;


    public SupplierResponse create_supplier(SupplierRequest supplierRequest){
        Supplier newSupplier = SupplierMapper.INSTANCE.toEntity(supplierRequest);
        newSupplier.setCreatedDate(new Date());
        newSupplier.setDisabled(false);
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
}
