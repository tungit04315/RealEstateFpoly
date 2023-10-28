package com.poly.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Likes;

public interface LikesDAO extends JpaRepository<Likes, Integer>{

	@Query(value="select top 4 * from likes where users = ?1 order by likes_id desc", nativeQuery = true)
	public List<Likes> getTop4PostLikes(String username);
	
	@Query(value="select * from likes where users = ?1", nativeQuery = true)
	public List<Likes> getAllPostLikes(String user);
	
	@Query(value="select * from likes where users = ?1 and post_id = ?2", nativeQuery = true)
	public Likes getPostLikeByUserIDAndPostID(String user, Integer post);
	
//	@Modifying
//	@Query(value = "DELETE FROM Likes l WHERE l.users.username = :user AND l.post_id.post_id = :post")
//	public void deleteByUserIdAndPostId(@Param("user") String user, @Param("post") Integer post);

	@Modifying
	@Transactional
	@Query(value = "delete likes where post_id = ?1", nativeQuery = true)
	public void deleteByUserIdAndPostId(Integer post);
}
