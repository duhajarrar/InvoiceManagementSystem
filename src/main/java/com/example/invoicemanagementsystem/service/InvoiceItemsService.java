package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.InvoiceItems;
import com.example.invoicemanagementsystem.model.Item;
import com.example.invoicemanagementsystem.repository.InvoiceItemsRepository;
import com.example.invoicemanagementsystem.repository.InvoiceRepository;
import com.example.invoicemanagementsystem.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceItemsService {
    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;
    public List<InvoiceItems> getAllInvoiceItems() {
        return invoiceItemsRepository.findAll();
    }

    public void saveItem(InvoiceItems invoiceItem) {
        this.invoiceItemsRepository.save(invoiceItem);
    }

    public InvoiceItems getInvoiceItemsById(long id) {
        InvoiceItems invoiceItems = invoiceItemsRepository.findById(id);
        if (invoiceItems!=null) {
            return invoiceItems;
        } else {
            throw new RuntimeException(" Invoice not found for id :: " + id);
        }
    }

    public InvoiceItems getInvoiceItemsByItemId(Long invoiceId,Long id){
        Invoice invoice=invoiceRepository.findById(invoiceId).orElse(null);
        List<InvoiceItems> list = invoice.getItems();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getItem().getId()==id){
                return list.get(i);
            }
        }
        return null;
    }
}
