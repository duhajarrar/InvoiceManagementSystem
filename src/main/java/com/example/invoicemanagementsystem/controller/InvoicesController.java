package com.example.invoicemanagementsystem.controller;
import com.example.invoicemanagementsystem.model.*;
import com.example.invoicemanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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


	Long userId;

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@GetMapping("/addNewItem")
	public String addNewItem(Model model){
		Item item=new Item();
		model.addAttribute("item",item);
		return "new_item";
	}

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@PostMapping("/saveItem/{idInvoice}")
	public String saveItem(@PathVariable ( value = "idInvoice") long idInvoice,@ModelAttribute("newInvoiceItem") InvoiceItems newInvoiceItem,Model model) {
		if(newInvoiceItem.getItem().getName()!=null && (newInvoiceItem.getItem().getPrice()+"") != null && (newInvoiceItem.getDiscount()+"")!=null && (newInvoiceItem.getQuantity()+"")!=null) {
			Item newItem = new Item();
			newItem.setName(newInvoiceItem.getItem().getName());
			newItem.setPrice(newInvoiceItem.getItem().getPrice());
			itemService.saveItem(newItem);
			System.out.println(newItem.toString() + "*******************************");

			Invoice invoice = invoiceService.getInvoiceById(idInvoice);
			System.out.println(newInvoiceItem.toString() + "++++++++++++++++++++++++++++++");
			newInvoiceItem.setItem(newItem);
			System.out.println(newInvoiceItem.toString() + "+++++++++++++++++++++++222222222222+++++++");
			invoice.addItem(newInvoiceItem);
			System.out.println(invoice.toString());

			newInvoiceItem.setInvoice(invoice);
			invoiceService.saveInvoice(invoice);
			model.addAttribute("invoice", invoice);

			List<InvoiceItems> invoiceItemList = invoice.getItems();
			model.addAttribute("invoiceItemList", invoiceItemList);

			newInvoiceItem = new InvoiceItems();
			model.addAttribute("newInvoiceItem", newInvoiceItem);

			List<Item> items = itemService.getAllItems();
			model.addAttribute("listItem", items);
		}
		return "redirect:/viewInvoice/{idInvoice}";
	}



//	@GetMapping("/addItemToInvoivce/invoiceId/{idInvoice}/itemId/{idItem}")

//	@GetMapping("/addItemToInvoivce/invoiceId/{idInvoice}/itemId/{idItem}/quantity/{quantity}/discount/{discount}")
	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@GetMapping("/addItemToInvoivce/invoiceId/{idInvoice}/itemId/{idItem}")
	public String addItemToInvoice(@PathVariable ( value = "idInvoice") long idInvoice,@PathVariable ( value = "idItem") long idItem,@RequestParam("discount1") Integer discount,@RequestParam("quantity1") Integer quantity,Model model) throws ParseException {
//		quantity=1;
//		discount=0;

		Invoice invoice=invoiceService.getInvoiceById(idInvoice);
		if(invoice==null){
			saveInvoice(invoice, model);
			//invoice.setUser(userService.getUserById(userId));
			//invoiceService.saveInvoice(invoice);
		}
		if(discount==null){
			discount=0;
		}
		if(quantity==null){
			quantity=1;
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
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));
		return "redirect:/showFormForUpdateInvoice/{idInvoice}";
	}

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
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

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@GetMapping("/updateItemFromInvoivce/invoiceId/{idInvoice}/InvoiceitemId/{idInvoiceitem}")
		public String updateItemFromInvoivce(@PathVariable ( value = "idInvoice") long idInvoice,@PathVariable ( value = "idInvoiceitem") long idInvoiceitem,@RequestParam("discount2") Integer discount,@RequestParam("quantity2") Integer quantity,Model model) throws ParseException {
		System.out.println("+++++++++++++++++++ *********** "+quantity+"          "+discount);
		Invoice invoice=invoiceService.getInvoiceById(idInvoice);
		InvoiceItems invoiceItem=invoiceItemsService.getInvoiceItemsById(idInvoiceitem);
		System.out.println(invoiceItem.toString()+"+++++++++++++++beforrrrrrrrrrrrre+++++++++++++++");
		//invoice.deleteItem(invoiceItem);
		invoiceItem.setDiscount(discount);
		invoiceItem.setQuantity(quantity);
		//invoice.addItem(invoiceItem);
		System.out.println(invoiceItem.toString()+"+++++++++++++++afteeeeeeeeeeeer+++++++++++++++");
		invoiceItemsService.saveItem(invoiceItem);
		System.out.println(invoice.getItems().toString());
		invoiceService.saveInvoice(invoice);
		model.addAttribute("invoice", invoice);

		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItemList", invoiceItemList);

		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);

		InvoiceItems newInvoiceItem= new InvoiceItems();
		model.addAttribute("newInvoiceItem",newInvoiceItem);
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));
		return "redirect:/showFormForUpdateInvoice/{idInvoice}";
	}

	@GetMapping("/listInvoice")
	public String viewInvoicesPage(Model model,@Param("keyword")String keyword) {
		model.addAttribute("invoice",new Invoice());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User loggedUser = userService.findByUsername(name);
		userId = loggedUser.getId();
		System.out.println(keyword+"5555555555555555");
			return findPaginated(1, "creationDate", "desc", model,keyword);
	}

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@GetMapping("/showNewInvoiceForm")
	public String showNewInvoiceForm(Model model) {
		Invoice invoice = new Invoice();
		model.addAttribute("invoice", invoice);
		List<InvoiceItems> invoiceItemList =invoice.getItems();
		model.addAttribute("invoiceItem", invoiceItemList);
		List<Item> items=itemService.getAllItems();
		model.addAttribute("listItem", items);
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));
		return "new_invoice";
	}

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@PostMapping("/saveInvoice")
	public String saveInvoice(@ModelAttribute("invoice") Invoice invoice,Model model) throws ParseException {
//		System.out.println(invoice.toString());
		if(invoice.getTitle()!=null & invoice.getDescription()!=null && invoice.getCreationDate()!=null ) {
			invoice.setUser(userService.getUserById(userId));
			invoiceService.saveInvoice(invoice);
			model.addAttribute("invoice", invoice);

			List<FileResponse> files = fileResposeService.getFileByInvoiceId(invoice.getId());
			model.addAttribute("files", files);

			List<Item> items = itemService.getAllItems();
			model.addAttribute("listItem", items);

			List<InvoiceItems> invoiceItemList = invoice.getItems();
			model.addAttribute("invoiceItemList", invoiceItemList);

			InvoiceItems newInvoiceItem = new InvoiceItems();
			model.addAttribute("newInvoiceItem", newInvoiceItem);
			model.addAttribute("isAdmin", hasRole(RoleEnum.Code.ROLE_ADMIN));
			model.addAttribute("isUser", hasRole(RoleEnum.Code.ROLE_USER));
			model.addAttribute("isAdminOrUser", hasRole(RoleEnum.Code.ROLE_ADMIN) | hasRole(RoleEnum.Code.ROLE_USER));

//		return "redirect:/viewInvoice/{"+invoice.getId()+"}";
//		return "redirect:/listInvoice";
			return "view_invoice1";
		}
		return "redirect:/listInvoice";
	}


	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
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
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));

		return "update_invoice";
	}


	@GetMapping("/viewFiles/{id}")
	public String viewFilesFiles(@PathVariable ( value = "id") long id, Model model) {
		List<FileResponse> files=fileResposeService.getFileByInvoiceId(id);
//		model.addAttribute("files",files);
		//return "indexFiles";
		return findPaginatedFile(1, "id", "asc", model,id);

	}

	@GetMapping("/listFile")
	public String viewFiles(Model model) {
		return findPaginatedFile(1, "id", "asc", model,(long)-1);

	}
	@GetMapping("/pageFile/{pageNo}")
	public String findPaginatedFile(@PathVariable (value = "pageNo") int pageNo,
								@RequestParam("sortField") String sortField,
								@RequestParam("sortDir") String sortDir,
								Model model,Long id) {
		int pageSize = 10;

		Page<FileResponse> page = fileResposeService.findPaginated(pageNo, pageSize, sortField, sortDir,id,userId);
		List<FileResponse> listFiles = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("id",id);
		model.addAttribute("files", listFiles);
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));
		return "indexFiles";
	}


	@GetMapping("/viewInvoice/{id}")
	public String viewInvoice(@PathVariable ( value = "id") long id, Model model) {
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
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));
		return "view_invoice";
	}

	@Secured({RoleEnum.Code.ROLE_ADMIN,RoleEnum.Code.ROLE_USER})
	@GetMapping("/deleteInvoice/{id}")
	public String deleteInvoice(@PathVariable (value = "id") long id) {

		// call delete invoice method
		this.invoiceService.deleteInvoiceById(id);
		return "redirect:/listInvoice";
	}


	@Autowired
	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();




	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "logout", required = false) String logout,
						Model model, Principal principal) {
		System.out.println("login done ..."+principal+error+" logout: "+logout);
		if(principal != null) {
			System.out.println("hiiii11111111111111111111111");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();
			System.out.println("HIIIIIIIIIIIIII " + name);
			User loggedUser = userService.findByUsername(name);
			if(loggedUser!=null) {
				userId = loggedUser.getId();
				return "redircet:/listInvoice";
			}

			return "redirect:/";
		}

		if(error != null) {
			System.out.println("error");

		}

		if(logout != null) {
			System.out.println("success");
		}

		return "login";
	}
	private boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return true;
	}
@GetMapping("/")
public String home(){
		return "home";
}

//
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		SecurityContextHolder.clearContext();
		userId=(long)-1;
		return "redirect:/login?logout";
	}



	@GetMapping("/pageInvoice/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model,String keyword) {
		int pageSize = 10;

		Page<Invoice> page = invoiceService.findPaginatedInvoice(pageNo, pageSize, sortField, sortDir, userId);
		System.out.println("================ search for => "+keyword);
		if(keyword!=null) {
			page = invoiceService.findPaginatedInvoiceSearch(pageNo, pageSize, sortField, sortDir, userId,keyword);
		}
		List<Invoice> listinvoices = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("invoice", new Invoice());

		model.addAttribute("listinvoices", listinvoices);
		User user=userService.getUserById(userId);
		model.addAttribute("isAdmin",hasRole(RoleEnum.Code.ROLE_ADMIN));
		model.addAttribute("isUser",hasRole(RoleEnum.Code.ROLE_USER));
		model.addAttribute("isAdminOrUser",hasRole(RoleEnum.Code.ROLE_ADMIN)|hasRole(RoleEnum.Code.ROLE_USER));

		return "indexInvoice";
	}


	 boolean hasRole(String role) {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) { return false; }
		Authentication auth = context.getAuthentication();

		if (auth == null) { return false; }
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(role));

	}



}
