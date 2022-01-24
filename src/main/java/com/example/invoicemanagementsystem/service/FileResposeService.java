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

    @Autowired
    UserService userService;
    public Page<FileResponse> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,Long id,Long userId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        User user = userService.getUserById(userId);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return fileResposeRepository.findFileResponseByInvoice_Id(id,pageable);
    }


//    public Page<FileResponse> findPaginatedAll(int pageNo, int pageSize, String sortField, String sortDirection,Long userId) {
//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                Sort.by(sortField).descending();
//        User user = userService.getUserById(userId);
//        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//        if ("ROLE_USER".equals(user.getAuthorities().get(0).getAuthority())){
//            return fileResposeRepository.findFileResponseBy(userId,pageable);
//        }else if("ROLE_ADMIN".equals(user.getAuthorities().get(0).getAuthority())||"ROLE_AUDIOTR".equals(user.getAuthorities().get(0).getAuthority())){
//            return this.fileResposeRepository.findAll(pageable);
//        }else{
//            return null;
//        }
//
//    }

    }


