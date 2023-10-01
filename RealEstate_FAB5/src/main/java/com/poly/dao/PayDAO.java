package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Pay;

public interface PayDAO extends JpaRepository<Pay, Integer>{

}
