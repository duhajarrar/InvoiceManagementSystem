package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();
    Item findById(long id);

}
