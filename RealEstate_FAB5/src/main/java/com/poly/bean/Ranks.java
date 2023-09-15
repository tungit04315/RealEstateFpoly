package com.poly.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ranks")
public class Ranks implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ranks_id;
	private int ranks_point;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "rank_id")
	private RankName rank_id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "endow_id")
	private Endow endow_id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ranks_id")
	List<Users> users;

}
