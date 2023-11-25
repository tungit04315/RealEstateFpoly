package com.poly.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Statistical {

	@Id
	private int month;
	private int year;
	private double total;
}
