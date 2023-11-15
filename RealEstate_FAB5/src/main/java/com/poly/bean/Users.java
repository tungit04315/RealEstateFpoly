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
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = 
{ 
		@UniqueConstraint(columnNames = { "username"}) 		
})
public class Users implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "username")
	private String username;
	private String passwords;
	private String email;
	private String phone;
	private String fullname;
	private String avatar;
	private String addresss;
	private int fail_login = 0;
	private boolean active = false;
	private boolean gender = true;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_block")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date create_block;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ranks_id")
	private Ranks ranks_id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pay_id")
	private Pay pay_id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	@JsonIgnore
	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
	List<Auth> auth;
	
	@JsonIgnore
	@OneToMany(mappedBy = "users_id")
	List<Post> post;
	
	@JsonIgnore
	@OneToMany(mappedBy = "users")
	List<Likes> likes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "users")
	List<Shares> shares;
	
	@JsonIgnore
	@OneToMany(mappedBy = "users")
	List<Transactions> transactions;
}
