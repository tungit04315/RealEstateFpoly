package com.poly.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "likes")
public class Likes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likes_id;
	private boolean likes_status = true;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "likes_date")
	private Date likes_date = new Date();
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post_id;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private Users users;
}
