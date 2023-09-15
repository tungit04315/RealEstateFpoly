package com.poly.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "type_propertys")
public class TypePropertys {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int types_id;
	
	private String types_name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "types_id")
	List<Post> post;
}
