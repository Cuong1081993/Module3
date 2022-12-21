package com.example.bakeryshop.service.inMemory;

import com.example.bakeryshop.model.Customer;
import com.example.bakeryshop.service.ICustomerService;

import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService {
    private List<Customer> customers;

    public CustomerService() {
        customers = new ArrayList<>();
        customers.add(new Customer(1L, "Quoc Cuong", "35 Le Loi", 1));
        customers.add(new Customer(2L, "Dang Tuyen", "35 Le Loi", 2));
        customers.add(new Customer(3L, "Moduel3", "35 Le Loi", 3));
        customers.add(new Customer(4L, "Alo123", "32 Le Loi", 3));
        customers.add(new Customer(5L, "Ahihihi", "35 Le Loi", 4));
        customers.add(new Customer(6L, "A cha rang", "5 Le Loi", 4));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByKwAndIdCountry(String kw, long idCountry) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomersByIdCountryPagging(String kw, long idCountry, int page, int pageNumber) {
        return null;
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public Customer findCustomerById(long id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        for (Customer cus : customers) {
            if (cus.getId() == customer.getId()) {
                cus.setName(customer.getName());
                cus.setAddress(customer.getAddress());
                cus.setIdCountry(customer.getIdCountry());
            }
        }

    }

    @Override
    public void deleteCustomer(long id) {
        for (Customer customer : customers){
            if (customer.getId()==id){
                customers.remove(customer);
                return;
            }
        }

    }

    @Override
    public List<Customer> getAllCustomerByIdCountry(long idCountry) {
        return null;
    }

    @Override
    public int getNoOfRecords() {
        return 0;
    }
}
