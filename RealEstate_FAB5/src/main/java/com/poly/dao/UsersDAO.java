package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Users;

public interface UsersDAO extends JpaRepository<Users, String>{

	@Query(value="select * from users where email = ?1 or phone = ?2", nativeQuery = true)
	public Users getUserFindByPhoneOrEmail(String email, String phone);
	
	@Query(value="select * from users where username Not in (select username from users where username = ?1)", nativeQuery = true)
	public Page<Users> getListUserAdmin(String username, Pageable p);
	
}
