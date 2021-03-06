package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    User findById(long id);
}
