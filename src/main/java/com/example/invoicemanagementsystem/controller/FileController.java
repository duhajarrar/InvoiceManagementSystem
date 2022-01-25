package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.model.FileResponse;
import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.RoleEnum;
import com.example.invoicemanagementsystem.service.FileResposeService;
import com.example.invoicemanagementsystem.service.InvoiceService;
import com.example.invoicemanagementsystem.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private FileResposeService fileResposeService;

    @Autowired
    private InvoicesController invoicesController;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }
    @Secured(RoleEnum.Code.ROLE_ADMIN)
    @GetMapping("/listFiles")
    public String listAllFiles(Model model){
        List<FileResponse> files=fileResposeService.getAllFiles();
        model.addAttribute("files",files);
        model.addAttribute("isAdmin",invoicesController.hasRole(RoleEnum.Code.ROLE_ADMIN));
        model.addAttribute("isAdminOrUser",invoicesController.hasRole(RoleEnum.Code.ROLE_ADMIN)|invoicesController.hasRole(RoleEnum.Code.ROLE_USER));

        return "listFiles";
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/upload-file/id/{idInvoice}")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file,@PathVariable ( value = "idInvoice") long idInvoice){
        if(!file.isEmpty()) {
            String name = storageService.store(file);
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(name)
                    .toUriString();
            Invoice invoice = invoiceService.getInvoiceById(idInvoice);
            fileResposeService.saveFile(new FileResponse(invoice, name, uri, file.getContentType(), file.getSize()));
            return new FileResponse(invoice, name, uri, file.getContentType(), file.getSize());
        }
        return null;
    }

    @PostMapping("/upload-multiple-files/id/{idInvoice}")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable ( value = "idInvoice") long idInvoice){
        if(files.length!=0){
        Arrays.stream(files)
                .map(file -> uploadFile(file,idInvoice))
                .collect(Collectors.toList());
            return "redirect:/viewFiles/{idInvoice}";
        }else{
            return "redirect:/viewFiles/{idInvoice}";
        }

    }


}