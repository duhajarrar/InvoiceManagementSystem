package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.FileResponse;
import com.example.invoicemanagementsystem.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileResposeRepository extends JpaRepository<FileResponse, Long> {
    List<FileResponse> findAll();
    FileResponse findById(long id);
    List<FileResponse> getFileByInvoiceId(long idInvoice);
    Page<FileResponse> findFileResponseByInvoice_Id(Long id,Pageable pageable);


}
