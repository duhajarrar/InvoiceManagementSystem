package com.example.invoicemanagementsystem.controller;
import com.example.invoicemanagementsystem.model.Customer;
import com.example.invoicemanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("customer")
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    @ModelAttribute("customer")
    public Customer UserRegistration() {
        return new Customer();
    }

    //@RequestMapping(value = "/registration" ,method = RequestMethod.GET)
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

//    @PostMapping("/registration")
    @RequestMapping(value = "/registration" ,method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("customer") Customer registration) {
        registration.setRole("USER");
        System.out.println(registration.toString());
        if(customerService.findByEmail(registration.getEmail())==null){
            customerService.saveCustomerUser(registration);
            return "redirect:/registration?success";
        }else{
            System.out.println(registration.getEmail()+" => Email used,try to register using another email .. " );
            //throw new RuntimeException(registration.getEmail()+" => Email used,try to register using another email .. " );
            return "redirect:/registration?error";
        }
        //return "redirect:/login";
    }
}

