package com.poly.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "auth", uniqueConstraints = 
{ 
		@UniqueConstraint(columnNames = { "users", "roles" }) 
})
public class Auth implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int auth_id;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private Users users;
	
	@ManyToOne
	@JoinColumn(name = "roles")
	private Roles roles;
}
