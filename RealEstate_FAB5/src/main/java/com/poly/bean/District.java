package com.poly.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "district")
public class District {

	@Id
	private int district_id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province_id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "district_id")
	List<Wards> wards;
}
