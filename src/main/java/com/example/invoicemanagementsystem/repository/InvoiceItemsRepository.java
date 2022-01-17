package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.InvoiceItems;
import com.example.invoicemanagementsystem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItems, Long> {
    List<InvoiceItems> findAll();
    InvoiceItems findById(long id);

    InvoiceItems findByItemId(Long id);
}
