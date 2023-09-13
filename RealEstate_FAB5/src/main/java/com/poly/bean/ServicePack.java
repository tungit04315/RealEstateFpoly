package com.poly.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "servicePack")
public class ServicePack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int servicePack_id;
	
	private String servicePack_name;
	private double servicePack_price;
	private String servicePack_location;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "servicePack_StarsDate")
	private Date servicePack_StarsDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "servicePack_EndDate")
	private Date servicePack_EndDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "servicePack_id")
	List<Post> post;
}
