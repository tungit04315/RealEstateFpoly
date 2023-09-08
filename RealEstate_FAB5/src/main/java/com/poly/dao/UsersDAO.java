package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.bean.Users;

public interface UsersDAO extends JpaRepository<Users, String>{

}
