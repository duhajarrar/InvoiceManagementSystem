package com.example.invoicemanagementsystem.controller;
//import com.example.invoicemanagementsystem.model.Role;
import com.example.invoicemanagementsystem.model.Role;
import com.example.invoicemanagementsystem.model.User;
import com.example.invoicemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("customer")
public class RegistrationController {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @ModelAttribute("customer")
    public User UserRegistration() {
        return new User();
    }

    //@RequestMapping(value = "/registration" ,method = RequestMethod.GET)
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

//    @PostMapping("/registration")
    @RequestMapping(value = "/registration" ,method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("customer") User registration) {
        System.out.println(registration.toString());
        if(userService.findByUsername(registration.getUsername())==null){
            userService.saveUser(registration);
            return "redirect:/registration?success";
        }else{
            System.out.println(registration.getUsername()+" => username used,try to register using another username .. " );
            //throw new RuntimeException(registration.getusername()+" => username used,try to register using another username .. " );
            return "redirect:/registration?error";
        }
        //return "redirect:/login";
    }
}

