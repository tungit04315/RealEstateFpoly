package com.poly.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.TotalPriceDAO;

@RestController
public class TotalRestController {

	@Autowired
	TotalPriceDAO dao;
	
	@GetMapping("/total")
	public Integer getTotal(@Param("year") Integer year) {
		return dao.getTotal(year);
	}
}
