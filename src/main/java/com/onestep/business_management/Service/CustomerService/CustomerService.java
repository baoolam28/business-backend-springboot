package com.onestep.business_management.Service.CustomerService;

import com.onestep.business_management.DTO.CustomerDTO.CustomerRequest;
import com.onestep.business_management.DTO.CustomerDTO.CustomerResponse;
import com.onestep.business_management.Entity.Customer;
import com.onestep.business_management.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer newCustomer = CustomerMapper.INSTANCE.toEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(newCustomer);
        return CustomerMapper.INSTANCE.toResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.map(CustomerMapper.INSTANCE::toResponse).orElse(null);
    }

    public CustomerResponse updateCustomer(int id, CustomerRequest customerRequest) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = CustomerMapper.INSTANCE.toEntity(customerRequest);
            updatedCustomer.setCustomerId(id);
            Customer savedCustomer = customerRepository.save(updatedCustomer);
            return CustomerMapper.INSTANCE.toResponse(savedCustomer);
        }
        return null;
    }

    public CustomerResponse deleteCustomer(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return CustomerMapper.INSTANCE.toResponse(customer.get());
        }
        return null;
    }
}
