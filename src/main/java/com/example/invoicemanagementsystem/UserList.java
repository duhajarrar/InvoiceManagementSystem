package com.example.invoicemanagementsystem;

import com.example.invoicemanagementsystem.model.User;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
public class UserList {

	@XmlElement(name = "user")
	public List<User> users;

	public UserList() {}

	public UserList(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}
	
}
