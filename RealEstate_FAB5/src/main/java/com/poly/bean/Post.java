package com.poly.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int post_id;
	private String post_title;
	private String post_content;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date create_at;
	@Temporal(TemporalType.DATE)
	@Column(name = "end_at")
	private Date end_date;
	
	private int acreage;
	private Long price;
	private String addresss;
	private String linkVideo;
	
	@ManyToOne
	@JoinColumn(name = "services_id")
	private ServicePack services_id;
	
	@ManyToOne
	@JoinColumn(name = "types_id")
	private TypePropertys types_id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post_id")
	List<Maps> maps;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post_id")
	List<Likes> likes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post_id")
	List<Shares> shares;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post_id")
	List<Albums> albums;
	
}
