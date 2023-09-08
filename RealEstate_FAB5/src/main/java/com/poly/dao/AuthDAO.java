package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.bean.Auth;

public interface AuthDAO extends JpaRepository<Auth, Integer>{

}
