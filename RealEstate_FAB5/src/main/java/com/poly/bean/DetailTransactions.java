package com.poly.bean;

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
@Table(name = "detailTransactions")
public class DetailTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int detail_id;
	private double price;
	
	@ManyToOne
	@JoinColumn(name = "transactions_id")
	private Transactions transactions_id;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post_id;
	
}
