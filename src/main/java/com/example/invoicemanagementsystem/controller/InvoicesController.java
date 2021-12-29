package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
public class InvoicesController {

	@Autowired
	private InvoiceService invoiceService;

	// display list of invoices
	@GetMapping("/listInvoice")
	public String viewInvoicesPage(Model model) {
		return findPaginated(1, "creationDate", "desc", model);
	}


	@GetMapping("/showNewInvoiceForm")
	public String showNewInvoiceForm(Model model) {
		// create model attribute to bind form data
		Invoice invoice = new Invoice();
		model.addAttribute("invoice", invoice);
		return "new_invoice";
	}

	@PostMapping("/saveInvoice")
	public String saveInvoice(@ModelAttribute("invoice") Invoice invoice) throws ParseException {
//		 save invoice to database
		invoiceService.saveInvoice(invoice);
		return "redirect:/listInvoice";
	}

	@GetMapping("/showFormForUpdateInvoice/{id}")
	public String showFormForUpdateInvoice(@PathVariable ( value = "id") long id, Model model) {

		// get invoice from the service
		Invoice invoice = invoiceService.getInvoiceById(id);

		// set invoice as a model attribute to pre-populate the form
		model.addAttribute("invoice", invoice);
		return "update_invoice";
	}

	@GetMapping("/deleteInvoice/{id}")
	public String deleteInvoice(@PathVariable (value = "id") long id) {

		// call delete invoice method
		this.invoiceService.deleteInvoiceById(id);
		return "redirect:/listInvoice";
	}


	@GetMapping("/pageInvoice/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 10;

		Page<Invoice> page = invoiceService.findPaginatedInvoice(pageNo, pageSize, sortField, sortDir);
		List<Invoice> listinvoices = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listinvoices", listinvoices);
		return "indexInvoice";
	}
}
