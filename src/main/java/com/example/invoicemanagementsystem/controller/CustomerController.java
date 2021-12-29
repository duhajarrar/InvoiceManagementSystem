package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.model.Customer;
import com.example.invoicemanagementsystem.service.CustomerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	// display list of Customers
	@GetMapping("/listCustomer")
	public String viewHomePage(Model model, Authentication authentication) {
		//System.out.println(authentication.getName());
		return findPaginated(1, "firstName", "asc", model);
	}

	@GetMapping("/showNewCustomerForm")
	public String showNewCustomerForm(Model model) {
		// create model attribute to bind form data
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "new_Customer";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		// save Customer to database
		customerService.saveCustomer(customer);
		return "redirect:/listCustomer";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

		// get Customer from the service
		Customer customer = customerService.getCustomerById(id);

		// set Customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		return "update_Customer";
	}

	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable (value = "id") long id) {

		// call delete Customer method
		customerService.deleteCustomerById(id);
		return "redirect:/listCustomer";
	}


	@GetMapping("/pageCustomer/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 10;

		Page<Customer> page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Customer> listCustomers = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listCustomers", listCustomers);
		return "index";
	}
}
