package com.poly.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shares")
public class Shares {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shares_id;
	
	@Temporal(TemporalType.DATE)
	private Date shares_date = new Date();
	
	private String emailTo;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post_id;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private Users users;
}
