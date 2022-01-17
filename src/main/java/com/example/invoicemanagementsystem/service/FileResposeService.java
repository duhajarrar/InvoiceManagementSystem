package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.FileResponse;
import com.example.invoicemanagementsystem.model.Item;
import com.example.invoicemanagementsystem.repository.FileResposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileResposeService {

    @Autowired
    private FileResposeRepository fileResposeRepository;


    public List<FileResponse> getAllFiles() {
        return fileResposeRepository.findAll();
    }

    public void saveFile(FileResponse file) {
        this.fileResposeRepository.save(file);
    }

    public FileResponse getFileById(long id) {
        FileResponse file = fileResposeRepository.findById(id);
        if (file!=null) {
            return file;
        } else {
            throw new RuntimeException(" Invoice not found for id :: " + id);
        }
    }

    public List<FileResponse> getFileByInvoiceId(long idInvoice) {
       List<FileResponse> files = fileResposeRepository.getFileByInvoiceId(idInvoice);
       return files;
    }
}
