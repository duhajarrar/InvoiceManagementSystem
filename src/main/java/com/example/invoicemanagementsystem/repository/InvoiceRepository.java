package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
//Page<Invoice> findAll(long customerId, Pageable pageable);
//Page<Invoice> findInvoiceByUser_Id(long customerId, Pageable pageable);

Invoice findById(long id);

    Page<Invoice> findInvoiceByUser_Id(Long userId, Pageable pageable);
}
