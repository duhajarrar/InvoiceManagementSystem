package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByEmail(String email);
    //User findByUsername(String username);
   // List<User> findAll();
}
