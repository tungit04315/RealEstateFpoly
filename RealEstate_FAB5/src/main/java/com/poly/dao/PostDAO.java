package com.poly.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.poly.bean.*;

public interface PostDAO extends JpaRepository<Post, Integer>{
	
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query(value = "update post set deleted_at = 'true' where post_id = ?1", nativeQuery = true)
	public Integer softDeletePost(Integer id);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query(value = "update post set active = ?1 where post_id = ?2", nativeQuery = true)
	public Integer softActionPost(String active,Integer id);
	
	@Query(value="select * from post where deleted_at = 'true' order by post_id desc", nativeQuery = true)
	public Page<Post> getHistoryDeletePostsDelete(Pageable p);
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false'", nativeQuery = true)
	public Page<Post> getPostsAll(Pageable p);
	
	@Query(value="select top 1 * from post where active = 1 and deleted_at = 0 order by post_id desc", nativeQuery = true)
	public Post getPostDesc();
	
	@Query(value="select top 1 * from post where active = 'true' and deleted_at = 'false' order by price desc", nativeQuery = true)
	public Post getFindIntroducingThePost();
	
	@Query(value="select * from post where active = 'false' and deleted_at = 'true' and users_id = ?1 order by post_id desc", nativeQuery = true)
	public Page<Post> getPostsDelete(String username, Pageable p);
	
	@Query(value="select * from post where active = 'false' and deleted_at = 'false' and users_id = ?1 order by post_id desc", nativeQuery = true)
	public List<Post> getPostsExpired(String username); 
	
	@Query(value="select top 6 * from post where active = 1 and deleted_at = 0 order by post_id desc", nativeQuery = true)
	public List<Post> getListPostDesc();
	
	@Query(value="select top 6 * from post where active = 'true' and deleted_at = 'false' and services_id = 4", nativeQuery = true)
	public List<Post> getPostsDiamond();
	
	@Query(value="select top 6 * from post where active = 'true' and deleted_at = 'false' and services_id = 15", nativeQuery = true)
	public List<Post> getPostsOften();
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false' and users_id = ?1", nativeQuery = true)
	public List<Post> getPostsByUserId(String username);
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false'", nativeQuery = true)
	public List<Post> getPostsAll();
	
	@Query(value="SELECT TOP 6 * FROM post WHERE price < (SELECT AVG(price) FROM post) ORDER BY price", nativeQuery = true)
	public List<Post> getPostsForYou();
	
//	@Query(value="select * from post where active = 1 and post_title like %?1 or addresss like %?2 or addresss like %?3 or types_id = ?4", nativeQuery = true)
//	public List<Post> searchPost(String title, String address, String province, Integer type);
	
	@Query(value = "select * from post where active = 1 and deleted_at = 0 and post_title like %:title% or addresss like %:address% or addresss like %:province% or types_id = :type", nativeQuery = true)
	public List<Post> searchPost(@Param("title") String title, @Param("address") String address, @Param("province") String province, @Param("type") Integer type);

}
