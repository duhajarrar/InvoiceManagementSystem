package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.model.Customer;
import com.example.invoicemanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public String login(@ModelAttribute("customer") Customer user) {
        System.out.println(user.toString());
        Customer loginUser = customerService.findByEmail(user.getEmail());
        System.out.println(loginUser.toString());
        if (loginUser != null) {
            return "redirect:/listCustomer";
        } else{
            System.out.println("Customer not found for this email :: " + user.getEmail());
            throw new RuntimeException("Customer not found for this email :: " + user.getEmail());
        }
    }





}

