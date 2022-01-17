package com.example.invoicemanagementsystem.repository;

import com.example.invoicemanagementsystem.model.FileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileResposeRepository extends JpaRepository<FileResponse, Long> {
    List<FileResponse> findAll();
    FileResponse findById(long id);
    List<FileResponse> getFileByInvoiceId(long idInvoice);
    }
