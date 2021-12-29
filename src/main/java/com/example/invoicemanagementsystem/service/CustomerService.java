package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.Customer;
import com.example.invoicemanagementsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	@Autowired
	private CustomerRepository customerRepository;

	    public CustomerService(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public void saveCustomer(Customer customer) {
		customer.setRole("USER");
		customerRepository.save(customer);
	}
	public void saveCustomerUser(Customer registration) {
		customerRepository.save(registration);
	}

	public Customer getCustomerById(long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		Customer Customer = null;
		if (optional.isPresent()) {
			Customer = optional.get();
		} else {
			throw new RuntimeException(" Customer not found for id :: " + id);
		}
		return Customer;
	}

	public void deleteCustomerById(long id) {
		customerRepository.deleteById(id);
	}

	public Page<Customer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return customerRepository.findAll(pageable);
	}

//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//		Customer user = customerRepository.findByEmail(email);
//		if (user == null) {
//			throw new UsernameNotFoundException("Invalid username or password.");
//		}
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//				(Collection<? extends GrantedAuthority>) user.getRole());
//	}

//	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String roles) {
//		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
//	}

	public List<Customer> getAll() {
		return customerRepository.findAll();
	}


	public Customer findByEmail(String email) {
			return customerRepository.findByEmail(email);
	}
}
