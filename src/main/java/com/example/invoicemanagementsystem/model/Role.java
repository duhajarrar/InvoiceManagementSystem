package com.example.invoicemanagementsystem.model;//package net.javaguides.springboot.model;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "roles",uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_id", "role"})})
//
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column
//    private Long id;
//
//    @Column
//    private String role;
//
//    public Role() {
//
//    }
//
//    public Role(String role) {
//        this.role = role;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    @Override
//    public String toString() {
//        return "Role{" +
//                "id=" + id +
//                ", role='" + role + '\'' +
//                '}';
//    }
//}