package com.onestep.business_management.Utils;

import com.onestep.business_management.Entity.Cart;
import com.onestep.business_management.Entity.Category;
import com.onestep.business_management.Entity.Origin;
import com.onestep.business_management.Entity.Product;
import com.onestep.business_management.Entity.Store;
import com.onestep.business_management.Entity.Supplier;
import com.onestep.business_management.Entity.User;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.CartRepository;
import com.onestep.business_management.Repository.CategoryRepository;
import com.onestep.business_management.Repository.OriginRepository;
import com.onestep.business_management.Repository.ProductRepository;
import com.onestep.business_management.Repository.StoreRepository;
import com.onestep.business_management.Repository.SupplierRepository;
import com.onestep.business_management.Repository.UserRepository;

import com.onestep.business_management.Entity.*;
import com.onestep.business_management.Repository.*;
import com.onestep.business_management.Service.ImageService.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired 
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    @Autowired ProductDetailRepository productDetailRepository;


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

    public Product findProductById(Integer productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Cart findCartById(Integer cartId){
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
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

    public ProductDetail findProductDetailById(Integer id){
        return productDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product detail with id: "+id+" not found!")
        );
    }

    public List<Image> uploadImages(List<MultipartFile> files){
        return imageService.uploadImages(files);
    }

    public Image uploadImage(MultipartFile file){
        return imageService.uploadImage(file);
    }
}
