package com.example.invoicemanagementsystem.model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final long serialVersionUID = 2699282209351614842L;
    private String authority;

    public Role() {}

    public Role(String authority) {
        this.authority = authority;
    }

    /*----- Getters & Setters -----*/
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getAuthority() { return authority; }

    public void setAuthority(String authority) { this.authority = authority; }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
