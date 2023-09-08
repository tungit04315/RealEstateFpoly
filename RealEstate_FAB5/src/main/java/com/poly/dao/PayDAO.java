package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.bean.Pay;

public interface PayDAO extends JpaRepository<Pay, Integer>{

}
