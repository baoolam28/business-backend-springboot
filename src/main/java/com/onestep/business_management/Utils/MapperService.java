package com.onestep.business_management.Utils;

import com.onestep.business_management.Entity.Category;
import com.onestep.business_management.Entity.Origin;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.Supplier;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.CategoryRepository;
import com.onestep.business_management.Repository.OriginRepository;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MapperService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private OriginRepository originRepository;

    public Store findStoreById(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    public Category findCategoryById(Integer categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Supplier findSupplierById(Integer supplierId){
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    public Origin findOriginById(Integer originId){
        return originRepository.findById(originId)
                .orElseThrow(() -> new ResourceNotFoundException("Origin not found"));
    }


}
