package com.onestep.business_management.Utils;

import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired ProductRepository productRepository;

    @Autowired UserRepository userRepository;

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

    public Customer findCustomerById(Integer customerId){
        return customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer with id: "+customerId+" not found")
        );
    }

    public Product findProductInStore(UUID storeId, String barcode){
        List<Product> exitsProds = productRepository.findProductInStore(storeId, barcode);

        if(exitsProds.isEmpty()){
            throw new ResourceNotFoundException("Not found product with barcode = "+barcode+" in store: "+storeId);
        }

        return exitsProds.get(0);
    }

    public User findUserById(UUID userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: "+userId+" not found!")
        );
    }

    public Product findProductByBarcode(String barcode){
        return productRepository.findByBarcode(barcode).orElseThrow(
                () -> new ResourceNotFoundException("Product with barcode: "+barcode+" not found!")
        );
    }
}
