package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.User;
import com.example.invoicemanagementsystem.repository.InvoiceRepository;
import com.example.invoicemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	public UserService userService;

	public List<Invoice> getAllInvoices() {
		return invoiceRepository.findAll();
	}

	public void saveInvoice(Invoice invoice) {
		this.invoiceRepository.save(invoice);
	}


	public Invoice getInvoiceById(long id) {
		Invoice invoice = invoiceRepository.findById(id);
		if (invoice!=null) {
			return invoice;
		} else {
			throw new RuntimeException(" Invoice not found for id :: " + id);
		}
	}

	public void deleteInvoiceById(long id) {
		this.invoiceRepository.deleteById(id);
	}

	public Page<Invoice> findPaginatedInvoice(int pageNo, int pageSize, String sortField, String sortDirection,long userId) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
				Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		User user = userService.getUserById(userId);
		if ("ROLE_USER".equals(user.getAuthorities().get(0).getAuthority())){
			return this.invoiceRepository.findInvoiceByUser_Id(userId, pageable);
		}else if("ROLE_ADMIN".equals(user.getAuthorities().get(0).getAuthority())||"ROLE_AUDIOTR".equals(user.getAuthorities().get(0).getAuthority())){
			return this.invoiceRepository.findAll(pageable);
		}else{
			return null;
		}
	}


	public Page<Invoice> findPaginatedInvoiceSearch(int pageNo, int pageSize, String sortField, String sortDirection,long userId,String keyword) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
				Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.invoiceRepository.search(keyword,userId,pageable);
	}


}
