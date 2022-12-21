package com.example.bakeryshop.service;

import com.example.bakeryshop.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    List<Customer> getAllCustomersByKwAndIdCountry(String kw, long idCountry);
    List<Customer> getAllCustomersByIdCountryPagging(String kw, long idCountry,int page, int pageNumber);
    void    addCustomer(Customer customer);
    Customer findCustomerById(long id);
    void updateCustomer(Customer customer);
    void deleteCustomer(long id);
    List<Customer> getAllCustomerByIdCountry(long idCountry);
    int getNoOfRecords();
}
