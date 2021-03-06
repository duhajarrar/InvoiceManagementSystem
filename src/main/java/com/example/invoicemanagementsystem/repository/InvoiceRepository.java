package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
//Page<Invoice> findAll(long customerId, Pageable pageable);
//Page<Invoice> findInvoiceByUser_Id(long customerId, Pageable pageable);

    Invoice findById(long id);
    Page<Invoice> findInvoiceByUser_Id(Long userId, Pageable pageable);
    Page<Invoice> findInvoiceByUser_IdAndAndTitle(Long userId,String title, Pageable pageable);

//    @Query("SELECT i FROM Invoice i WHERE i.user.id = ?2 and CONCAT(i.title, i.description) LIKE %?1% ")
@Query("SELECT i FROM Invoice i WHERE i.user.id = ?2 and (CONCAT(i.title, i.description, i.total) LIKE %?1% or i.id in (SELECT f.invoice.id FROM FileResponse f WHERE CONCAT( f.name, f.size, f.type) LIKE %?1%)) ")
public Page<Invoice> searchInvoice(String keyword,Long userId, Pageable pageable);

@Query("SELECT i FROM Invoice i WHERE (CONCAT(i.title, i.description, i.total) LIKE %?1% or i.id in (SELECT f.invoice.id FROM FileResponse f WHERE CONCAT( f.name, f.size, f.type) LIKE %?1%)) ")
public Page<Invoice> searchAll(String keyword, Pageable pageable);
}
