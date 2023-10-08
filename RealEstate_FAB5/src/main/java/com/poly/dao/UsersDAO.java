package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Users;

public interface UsersDAO extends JpaRepository<Users, String>{

	@Query(value="select * from users where email = ?1 or phone = ?2", nativeQuery = true)
	public Users getUserFindByPhoneOrEmail(String email, String phone);
	
}
