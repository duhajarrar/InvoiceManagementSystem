package com.example.invoicemanagementsystem.model;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final long serialVersionUID = 2699282209351614842L;

    @Enumerated(EnumType.STRING)
    private RoleEnum authority;

    public Role() {}

    public Role(RoleEnum authority) {
        this.authority = authority;
    }

    /*----- Getters & Setters -----*/
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

  
    public RoleEnum getAuthority() { return authority; }

    @Enumerated(EnumType.STRING)
    public void setAuthority(RoleEnum authority) { this.authority = authority; }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }



}
