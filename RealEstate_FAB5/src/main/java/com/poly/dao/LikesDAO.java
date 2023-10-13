package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Likes;

public interface LikesDAO extends JpaRepository<Likes, Integer>{

	@Query(value="select top 4 * from likes order by likes_id desc", nativeQuery = true)
	public List<Likes> getTop4PostLikes();
}
