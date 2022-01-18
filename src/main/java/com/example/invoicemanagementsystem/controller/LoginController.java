package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.model.User;
import com.example.invoicemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {

//    public String loginPage( Model model) {
//        System.out.println("HHHHHHHHHHHHHEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEE");
////        Customer customer = new Customer();
////        model.addAttribute("customer",customer);
//        return "redirect:/listCustomer";
//    }

//    @PostMapping(value = "/login")
//    public String loginUser(@ModelAttribute("customer") Customer user) {
//        System.out.println(user.toString());
//        Customer loginUser = customerService.findByUsername(user.getUsername());
//
//        System.out.println("++++++++++++++++++++++++++++++++");
//        System.out.println(loginUser.toString());
//        System.out.println(user.toString());
//        System.out.println("++++++++++++++++++++++++++++++++");
//
//        if ((loginUser != null) && (user.getPassword().equals(passwordEncoder.encode(loginUser.getPassword())))) {
//            return "redirect:/listCustomer";
//        } else{
//            System.out.println("Customer not found for this username :: " + user.getUsername());
////            return "redirect:/login?error";
//            return "redirect:/registration";
//            //throw new RuntimeException("Customer not found for this username :: " + user.getusername());
//        }
//    }
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request,HttpServletResponse response){
//
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        if (auth != null){
////            new SecurityContextLogoutHandler().logout(request, response, auth);
////        }
//        HttpSession session= request.getSession(false);
//        SecurityContextHolder.clearContext();
//        session= request.getSession(false);
//        if(session != null) {
//            session.invalidate();
//        }
//        for(Cookie cookie : request.getCookies()) {
//            cookie.setMaxAge(0);
//        }
//        return "redirect:/login?logout";
//    }





}

