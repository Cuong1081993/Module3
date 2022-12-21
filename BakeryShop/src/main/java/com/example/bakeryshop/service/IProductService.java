package com.example.bakeryshop.service;

import com.example.bakeryshop.model.Customer;
import com.example.bakeryshop.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    List<Product> getAllProducts(String query, int page, int numberOfPage);

//    List<Customer> getAllCustomersByKwAndIdCountry(String kw, long idCountry);
    List<Product> getAllProductsPagging(String kw, int offset, int numberOfPage);
    void    addProduct(Product product);
    Product findProductById(int id);
    void updateProduct(Product product);
    void deleteProduct(int id);
//    List<Customer> getAllCustomerByIdCountry(long idCountry);
    int getNoOfRecords();
}
