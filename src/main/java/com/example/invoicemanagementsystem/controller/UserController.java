package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.model.RoleEnum;
import com.example.invoicemanagementsystem.model.User;
//import com.example.invoicemanagementsystem.model.Role;
import com.example.invoicemanagementsystem.model.Role;
import com.example.invoicemanagementsystem.service.UserService;
//import com.example.invoicemanagementsystem.service.UploadFileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

//	@Autowired
//	private UploadFileService uploadFileService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@GetMapping("/listCustomer")
	public String viewHomePage(Model model, Authentication authentication) {
		if (authentication != null) {
			logger.info("Hello " + authentication.getName());
			System.out.println("Hello " + authentication.getName()+" !!!!!!!!!!!!!!!!!!");
		}
		//System.out.println(authentication.getName());
		return findPaginated(1, "firstName", "asc", model);
	}

	@Autowired
	InvoicesController invoicesController;

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@GetMapping("/showNewCustomerForm")
	public String showNewCustomerForm(Model model) {
		// create model attribute to bind form data
		User customer = new User();
		model.addAttribute("customer", customer);
		model.addAttribute("isAdmin",invoicesController.hasRole(RoleEnum.Code.ROLE_ADMIN));
		return "new_Customer";
	}

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@PostMapping("/saveCustomer")
	public String saveUser(@ModelAttribute("customer") User customer) {
		// save Customer to database
		//customer.setRole(new Role("USER"));
		System.out.println(customer.toString());
		if(userService.findByUsername(customer.getUsername())==null && !customer.getUsername().equals("") && !customer.getLastName().equals("") && !customer.getFirstName().equals("") && !customer.getPassword().equals("")){
			List<Role> role=new ArrayList<>();
			role.add(new Role(RoleEnum.ROLE_USER));
			customer.setAuthorities(role);
//			customer.setRole("USER");
			userService.saveUser(customer);
			return "redirect:/listCustomer?success";

		}else if (userService.findByUsername(customer.getUsername())!=null){
			System.out.println(customer.getUsername()+" => username used,try to register using another username .. " );
			return "redirect:/showNewCustomerForm?error";
			//throw new RuntimeException(customer.getusername()+" => username used,try to register using another username .. " );
			//return "redirect:/registration?error";
		}
		else{
			return "redirect:/listCustomer";
		}
	}

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@PostMapping("/updateCustomer")
	public String updateCustomer(@ModelAttribute("customer") User customer) {
			System.out.println(customer.toString()+"+++++++++++");
			userService.updateUser(customer);

		return "redirect:/listCustomer?success1";
	}

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@GetMapping("/updateCustomer/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

		// get Customer from the service
		User customer = userService.getUserById(id);

		// set Customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		model.addAttribute("isAdmin",invoicesController.hasRole(RoleEnum.Code.ROLE_ADMIN));

		return "update_Customer";
	}

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable (value = "id") long id) {

		// call delete Customer method
		userService.deleteUserById(id);
		return "redirect:/listCustomer?delete";
	}

	@Secured(RoleEnum.Code.ROLE_ADMIN)
	@GetMapping("/pageCustomer/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 10;

		Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<User> listCustomers = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("isAdmin",invoicesController.hasRole(RoleEnum.Code.ROLE_ADMIN));

		model.addAttribute("listCustomers", listCustomers);
		return "index";
	}


}
