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
@Table(name = "transactions")
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactions_id;
	
	@Temporal(TemporalType.DATE)
	@JoinColumn(name = "create_at")
	private Date create_at = new Date();
	
	@ManyToOne
	@JoinColumn(name = "users")
	private Users users;
}
