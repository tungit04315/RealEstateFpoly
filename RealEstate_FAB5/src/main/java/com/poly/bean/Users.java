package com.poly.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "username")
	private String username;
	private String passwords;
	private String email;
	private boolean gender;
	private String phone;
	private String fullname;
	private String avatar;
	private String addresss;
	private int failLogin = 0;
	private boolean active = true;
	@Temporal(TemporalType.DATE)
	@Column(name = "create_block")
	private Date create_block;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ranks_id")
	private Ranks ranks_id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pay_id")
	private Pay pay_id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "username", fetch = FetchType.EAGER)
	List<Auth> auth;
}
