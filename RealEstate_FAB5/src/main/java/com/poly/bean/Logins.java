package com.poly.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "logins")
public class Logins {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int logins_id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "users")
	Users user;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "login_sign")
	private Date login_sign = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "logins_out")
	private Date logins_out = new Date();
}
