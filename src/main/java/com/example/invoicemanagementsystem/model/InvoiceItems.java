package com.example.invoicemanagementsystem.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "invoice_items")
public class InvoiceItems implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    //@Id
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

//    @Column
//    private String title;

    @Column
    private int quantity;

    @Column
    private int discount;

    @Column
    private int priceafterdiscount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public InvoiceItems() {
    }

    public InvoiceItems(int quantity, int discount, Item item) {
//        this.title = title;
        this.quantity = quantity;
        this.discount = discount;
        this.item = item;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPriceafterdiscount() {
        this.priceafterdiscount=0;
        this.priceafterdiscount=(this.quantity * this.item.getPrice() *(100-this.discount))/100;
        return this.priceafterdiscount;
    }

    public void setPriceafterdiscount(int priceafterdiscount) {
        this.priceafterdiscount = priceafterdiscount;
    }

    @Override
    public String toString() {
        return "InvoiceItems{" +
                " priceafterdiscount=" + priceafterdiscount +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", item=" + item +
                '}';
    }
}
