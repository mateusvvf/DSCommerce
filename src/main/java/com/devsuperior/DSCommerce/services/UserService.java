package com.devsuperior.DSCommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSCommerce.dto.UserDTO;
import com.devsuperior.DSCommerce.entities.Role;
import com.devsuperior.DSCommerce.entities.User;
import com.devsuperior.DSCommerce.projections.UserDetailsProjection;
import com.devsuperior.DSCommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	
	@Autowired
	UserRepository repository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> list = repository.searchUserAndRolesByEmail(username);
		
		if (list.size() == 0) {
			throw new UsernameNotFoundException("E-mail not found");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(list.get(0).getPassword());
		
		for (UserDetailsProjection projection : list) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}
	
	protected User authenticated() {
		
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			
			User user = repository.findByEmail(username).get();
			return user;
			
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("E-mail not found");
		}
		
	}
	
	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = authenticated();
		return new UserDTO(user);
	}
	
}
