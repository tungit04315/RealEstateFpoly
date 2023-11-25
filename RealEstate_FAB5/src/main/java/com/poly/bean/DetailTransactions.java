package com.poly.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail_transactions")
public class DetailTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int detail_id;
	private double price;
	private boolean transactions_type;
	private String timer;
	@Column(name = "account_get")
	private int account_get;
	private String fullname_get;
	private String bank_code;
	
	@ManyToOne
	@JoinColumn(name = "transactions_id")
	private Transactions transactions_id;
	
}
