package com.devsuperior.DSCommerce.tests;

import java.time.LocalDate;

import com.devsuperior.DSCommerce.entities.Role;
import com.devsuperior.DSCommerce.entities.User;

public class UserFactory {
	
	public static User createClientUser() {
		User user = new User(1L, "Michael", "michael@gmail.com", "925062009", LocalDate.parse("1958-08-29"), "$2a$10$v.NupjjdUlrYBeuvmzki2u6TZb5H0b1xwTvq65CcZjd/jm07/NmqC");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	public static User createAdmintUser() {
		User user = new User(2L, "Emmett", "emmett@gmail.com", "921102015", LocalDate.parse("1955-11-05"), "$2a$10$v.NupjjdUlrYBeuvmzki2u6TZb5H0b1xwTvq65CcZjd/jm07/NmqC");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}

}
