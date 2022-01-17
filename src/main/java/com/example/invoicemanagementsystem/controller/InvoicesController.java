package com.example.invoicemanagementsystem.controller;
import com.example.invoicemanagementsystem.model.*;
import com.example.invoicemanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InvoicesController {

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private ItemService itemService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private InvoiceItemsService invoiceItemsService;

	@Autowired
	private UserService userService;
	@Autowired
	private FileResposeService fileResposeService;

	@GetMapping("/addNewItem")
	public String addNewItem(Model model){
		Item item=new Item();
		model.addAttribute("item",item);
		return "new_item";
	}

	@PostMapping("/saveItem/{idInvoice}")
	public String saveItem(@PathVariable ( value = "idInvoice") long idInvoice,@ModelAttribute("newInvoiceItem") InvoiceItems newInvoiceItem,Model model) {
		Item newItem=new Item();
		newItem.setName(newInvoiceItem.getItem().getName());
		newItem.setPrice(newInvoiceItem.getItem().getPrice());
		itemService.saveItem(newItem);
		System.out.println(newItem.toString()+"*******************************");

		Invoice invoice=invoiceService.getInvoiceById(idInvoice);
		System.out.println(newInvoiceItem.toString()+"++++++++++++++++++++++++++++++");
		newInvoiceItem.setItem(newItem);
		System.out.println(newInvoiceItem.toString()+"+++++++++++++++++++++++222222222222+++++++");
		invoice.addItem(newInvoiceItem);
		System.out.println(invoice.toString());



		invoiceService.saveInvoice(invoice);
		model.addAttribute("invoice", invoice);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		return "redirect:/viewInvoice/{idInvoice}";
	}
//	@GetMapping("/addItemToInvoivce/invoiceId/{idInvoice}/itemId/{idItem}")
	@GetMapping("/addItemToInvoivce/invoiceId/{idInvoice}/itemId/{idItem}/q/{quantity}/d/{discount}")
	public String addItemToInvoice(@PathVariable ( value = "idInvoice") long idInvoice,@PathVariable ( value = "idItem") long idItem,@PathVariable ( value = "discount") int discount,@PathVariable ( value = "quantity") int quantity,Model model,@ModelAttribute("invoice") Invoice invoice) throws ParseException {
		invoice=invoiceService.getInvoiceById(idInvoice);
		if(invoice==null){
			saveInvoice(invoice, model);
			//invoice.setUser(userService.getUserById(userId));
			//invoiceService.saveInvoice(invoice);
		}
		InvoiceItems invoiceItem=invoiceItemsService.getInvoiceItemsByItemId(idInvoice,idItem);
		if(invoiceItem==null){
			System.out.println("Invocie item not here 8888888888888888888888888888");
			invoiceItem=new InvoiceItems();
			Item item = itemService.getItemById(idItem);
			System.out.println(item.toString()+"++++++++++++++++++++++++++++++");
			invoiceItem.setItem(item);
			invoiceItem.setDiscount(discount);
			invoiceItem.setQuantity(quantity);
			invoice.addItem(invoiceItem);
			System.out.println(invoice.toString());
			invoiceService.saveInvoice(invoice);

		}else{
			System.out.println(invoiceItem.toString()+" 8888888888888888888888888888");
			invoiceItem.setQuantity(invoiceItem.getQuantity()+quantity);
			invoice.addItem(invoiceItem);
			invoiceService.saveInvoice(invoice);
		}


		model.addAttribute("invoice", invoice);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);
		return "redirect:/viewInvoice/{idInvoice}";
	}


	@GetMapping("/deleteItemFromInvoivce/invoiceId/{idInvoice}/InvoiceitemId/{idInvoiceitem}")
	public String deleteItemFromInvoivce(@PathVariable ( value = "idInvoice") long idInvoice,@PathVariable ( value = "idInvoiceitem") long idInvoiceitem,Model model){
		Invoice invoice=invoiceService.getInvoiceById(idInvoice);
		InvoiceItems invoiceItem=invoiceItemsService.getInvoiceItemsById(idInvoiceitem);
		System.out.println(invoiceItem.toString()+"++++++++++++++++++++++++++++++");
		invoice.deleteItem(invoiceItem);
		System.out.println(invoice.toString());
		invoiceService.saveInvoice(invoice);
		model.addAttribute("invoice", invoice);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);
		return "redirect:/viewInvoice/{idInvoice}";
	}
	@GetMapping("/updateItemFromInvoivce/invoiceId/{idInvoice}/idInvoiceitem/{idInvoiceitem}/q/{q}/d/{d}")
	public String updateItemFromInvoivce(@PathVariable ( value = "idInvoice") long idInvoice,@PathVariable ( value = "idInvoiceitem") long idInvoiceitem,@PathVariable ( value = "q") int q,@PathVariable ( value = "d") int d,Model model){
		System.out.println("+++++++++++++++++++ *********** "+q+"          "+d);
		Invoice invoice=invoiceService.getInvoiceById(idInvoice);
		InvoiceItems invoiceItem=invoiceItemsService.getInvoiceItemsById(idInvoiceitem);
		System.out.println(invoiceItem.toString()+"+++++++++++++++beforrrrrrrrrrrrre+++++++++++++++");
		invoice.deleteItem(invoiceItem);
		invoiceItem.setDiscount(d);
		invoiceItem.setQuantity(q);
		invoice.addItem(invoiceItem);

//		//invoice.addItem(invoiceItem);
		System.out.println(invoiceItem.toString()+"+++++++++++++++afteeeeeeeeeeeer+++++++++++++++");
//		invoice.updateItem(invoiceItem);
		System.out.println(invoice.toString());
		invoiceService.saveInvoice(invoice);
		model.addAttribute("invoice", invoice);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);
		return "redirect:/viewInvoice/{idInvoice}";
	}



	@GetMapping("/listInvoice")
	public String viewInvoicesPage(Model model) {
		return findPaginated(1, "creationDate", "desc", model);
	}


	@GetMapping("/showNewInvoiceForm")
	public String showNewInvoiceForm(Model model) {
		Invoice invoice = new Invoice();
		model.addAttribute("invoice", invoice);
		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItem", invoiceItemList);
		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);
		return "new_invoice";
	}

	@PostMapping("/saveInvoice")
	public void saveInvoice(@ModelAttribute("invoice") Invoice invoice,Model model) throws ParseException {
//		System.out.println(invoice.toString());
		invoice.setUser(userService.getUserById(userId));
		invoiceService.saveInvoice(invoice);
		model.addAttribute("invoice", invoice);

		List<FileResponse> files=fileResposeService.getFileByInvoiceId(invoice.getId());
		model.addAttribute("files",files);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);

		//return "update_invoice";
	}


	@GetMapping("/showFormForUpdateInvoice/{id}")
	public String showFormForUpdateInvoice(@PathVariable ( value = "id") long id, Model model) {
		Invoice invoice = invoiceService.getInvoiceById(id);
		model.addAttribute("invoice", invoice);

		List<FileResponse> files=fileResposeService.getFileByInvoiceId(id);
		model.addAttribute("files",files);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);


		return "update_invoice";
	}

	@GetMapping("/viewInvoice/{id}")
	public String viewInvoice(@PathVariable ( value = "id") long id, Model model, Authentication authentication) {
		List<FileResponse> files=fileResposeService.getFileByInvoiceId(id);
		model.addAttribute("files",files);

		Invoice invoice = invoiceService.getInvoiceById(id);
		model.addAttribute("invoice", invoice);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);
		return "view_invoice";
	}

	@GetMapping("/deleteInvoice/{id}")
	public String deleteInvoice(@PathVariable (value = "id") long id) {

		// call delete invoice method
		this.invoiceService.deleteInvoiceById(id);
		return "redirect:/listInvoice";
	}



///////////////////////////////////////////
	public Long userId;
//	@Autowired
//	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

//	@GetMapping("/login")
//	public String login1(Model model){
//		model.addAttribute("customer",new User());
//		return "login";
//	}


	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
	public String login(@ModelAttribute("customer") User user) {
		System.out.println("login done ..."+" 11111 "+" logout: "+user.toString());
		//if(principal != null) {
//            userId=user.getId();
	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String name = auth.getName();
		if(!name.equals("")) {
			System.out.println("HIIIIIIIIIIIIII " + name);
			System.out.println(auth);
			User loggedUser = userService.findByUsername(name);
//			if(loggedUser==null){
//				loggedUser=userService.findByUsername("admin");
//			}
			if (loggedUser != null) {
				userId = loggedUser.getId();
				System.out.println(loggedUser + " -------------------------------------44444444444444");
				return "redirect:/listInvoice";
			}
		}


		return "login";
	}
	///////////////////////////////////////////////////
	@GetMapping("/pageInvoice/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 10;
//		User user = (User)authentication.getPrincipal();

		Page<Invoice> page = invoiceService.findPaginatedInvoice(pageNo, pageSize, sortField, sortDir,userId);
//		User user = userService.getUserById(id);

		List<Invoice> listinvoices = page.getContent();
//		List<Invoice> listinvoices = page.getContent();
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).getUser().equals(user)){
//				listinvoices.add(list.get(i));
//			}
//		}
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
