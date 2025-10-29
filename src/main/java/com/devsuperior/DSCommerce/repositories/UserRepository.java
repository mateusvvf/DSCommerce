package com.devsuperior.DSCommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.DSCommerce.entities.User;
import com.devsuperior.DSCommerce.projections.UserDetailsProjection;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	@Query(nativeQuery = true, value = "SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority "
			+ "FROM tb_user JOIN tb_user_role ON tb_user.id = tb_user_role.user_id "
			+ "JOIN tb_role ON tb_role.id = tb_user_role.role_id "
			+ "WHERE tb_user.email = :email")
	List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

}
