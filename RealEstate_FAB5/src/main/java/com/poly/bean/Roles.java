package com.poly.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "roles")
public class Roles implements Serializable{
	@Id
	private String roles_id;
	private String roles_name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "roles")
	List<Auth> auth;
}
