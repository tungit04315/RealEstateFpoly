package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Post;

public interface PostDAO extends JpaRepository<Post, Integer>{

	@Query(value="select top 4 * from post order by post_id desc", nativeQuery = true)
	public List<Post> getPostsLike(); 
	
	@Query(value="select * from post where active = 'false'", nativeQuery = true)
	public List<Post> getPostsExpired(); 
}
