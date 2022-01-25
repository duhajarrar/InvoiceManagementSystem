package com.example.invoicemanagementsystem.service;

import com.example.invoicemanagementsystem.model.*;
import com.example.invoicemanagementsystem.model.Role;
import com.example.invoicemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void saveUser(User registration) {
		if(findByUsername(registration.getUsername())==null) {

			if (registration.getEnabled() == null) {
				registration.setEnabled(true);
			}
			registration.setPassword(passwordEncoder.encode(registration.getPassword()));
			List<Role> role=new ArrayList<>();
			role.add(new Role(RoleEnum.ROLE_USER));
			registration.setAuthorities(role);
			System.out.println("111111111111111111111111111"+registration.toString()+"222222222222222222");
			userRepository.save(registration);
		}
	}
	public void updateUser(User registration) {
			System.out.println(registration.toString());
		userRepository.deleteById(registration.getId());
		userRepository.save(registration);
	}
	public User getUserById(long id) {
		User user = userRepository.findById(id);
		if (user!=null) {
			return user;
		} else {
			throw new RuntimeException(" Invoice not found for id :: " + id);
		}
	}
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}
	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

		List<User> users=userRepository.findAll();
		for (int i=0;i<users.size();i++) {
			User user=users.get(i);
			System.out.println(user.getAuthorities().get(0).getAuthority().getAuthority());
			System.out.println(RoleEnum.Code.ROLE_ADMIN);
			if(user.getAuthorities().get(0).getAuthority().getAuthority().equals(RoleEnum.Code.ROLE_ADMIN)) {
				users.remove(i);
				System.out.println(i);
			}
		}
		Page<User> userPage=new PageImpl<User>(users,pageable,users.size());
		return userPage;
	}
	private List<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
	return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority().getAuthority()))
			.collect(Collectors.toList());
}
	public List<User> getAll() {
		return userRepository.findAll();
	}
	public User findByUsername(String username) {
			return userRepository.findByUsername(username);
	}
}
