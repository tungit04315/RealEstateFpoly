package com.poly.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "word_filter")
public class WordFilter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int word_id;
	
	private String word_name;
}
