package com.example.invoicemanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="creation_date")
    private Date creationDate;

    @Column
    private long total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItems> items;

    public Invoice() {
        this.items=new ArrayList<InvoiceItems>();
    }

    public Invoice(String title, String description, Date creationDate, long total, User user, List<InvoiceItems> items) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.total = total;
        this.user = user;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<InvoiceItems> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItems> items) {
        this.items = items;
    }

    public void addItem(InvoiceItems item) {
        this.items.add(item);
    }

    public void updateItem(InvoiceItems item) {
        this.items.remove(items.indexOf(item));
        this.items.add(item);
        System.out.println(item+"****************/////////////////////////////");
        System.out.println(this.items);
    }

    public void deleteItem(InvoiceItems item) {
        this.items.remove(items.indexOf(item));
    }

    public long getTotal() {
        this.total = (long) 0.0;
        for(int i = 0; i < items.size(); i++) {
            this.total += items.get(i).getPriceafterdiscount();
        }
        return this.total;
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}
