package com.poly.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "province")
public class Province {
	
	@Id
	private int province_id;
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "province_id")
	List<District> district;
}
