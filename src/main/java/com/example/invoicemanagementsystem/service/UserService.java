package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.Role;
import com.example.invoicemanagementsystem.model.User;
import com.example.invoicemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	@Autowired
	private UserRepository userRepository;

	    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void saveUser(User registration) {
		if(findByUsername(registration.getUsername())==null) {

			if (registration.getEnabled() == null) {
				registration.setEnabled(true);
				registration.setPassword("test@2022");
			}
			registration.setPassword(passwordEncoder.encode(registration.getPassword()));
			List<Role> role=new ArrayList<>();
			role.add(new Role("USER"));
			registration.setAuthorities(role);
			System.out.println("111111111111111111111111111"+registration.toString()+"222222222222222222");
			userRepository.save(registration);
		}
	}
	public void updateUser(User registration) {
		userRepository.save(registration);
	}


	public User getUserById(long id) {
		Optional<User> optional = userRepository.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException(" User not found for id :: " + id);
		}
		return user;
	}

	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}

	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return userRepository.findAll(pageable);
	}

private List<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
	return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority().toString()))
			.collect(Collectors.toList());
}
	public List<User> getAll() {
		return userRepository.findAll();
	}


	public User findByUsername(String username) {
			return userRepository.findByUsername(username);
	}
}
