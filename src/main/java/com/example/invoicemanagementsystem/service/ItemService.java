package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.Invoice;
import com.example.invoicemanagementsystem.model.Item;
import com.example.invoicemanagementsystem.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
    @Service
    public class ItemService {
        @Autowired
        private ItemRepository itemRepository;

        public List<Item> getAllItems() {
            return itemRepository.findAll();
        }

        public void saveItem(Item item) {
            this.itemRepository.save(item);
        }

        public Item getItemById(long id) {
            Item item = itemRepository.findById(id);
            if (item!=null) {
                return item;
            } else {
                throw new RuntimeException(" Invoice not found for id :: " + id);
            }
        }

    }
