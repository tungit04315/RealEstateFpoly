package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Likes;

public interface LikesDAO extends JpaRepository<Likes, Integer>{

	@Query(value="select top 4 * from likes where users = ?1 order by likes_id desc", nativeQuery = true)
	public List<Likes> getTop4PostLikes(String username);
}
