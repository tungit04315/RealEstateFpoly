package com.poly.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "wards")
public class Wards {

	@Id
	private int wards_id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district_id;
}
