package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.FileResponse;
import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.Item;
import com.example.invoicemanagementsystem.model.User;
import com.example.invoicemanagementsystem.repository.FileResposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    InvoiceService invoiceService;
    public Page<FileResponse> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,Long id) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        //Invoice invoice=invoiceService.getInvoiceById(id);
        return fileResposeRepository.findFileResponseByInvoice_Id(id,pageable);
    }

}
