package com.poly.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import com.poly.bean.*;

public interface PostDAO extends JpaRepository<Post, Integer>{
	
	@Query(value="select * from post where active = 'false' and deleted_at = 'false' and users_id = ?1", nativeQuery = true)
	public List<Post> getPostsExpired(String username); 
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query(value = "update post set deleted_at = 'true' where post_id = ?1", nativeQuery = true)
	public Integer softDeletePost(Integer id);
	
	@Query(value="select * from post where active = 'false' and deleted_at = 'true' and users_id = ?1 order by post_id desc", nativeQuery = true)
	public List<Post> getPostsDelete(String username);
	
	@Query(value="select * from post where deleted_at = 'true' order by post_id desc", nativeQuery = true)
	public Page<Post> getHistoryDeletePostsDelete(Pageable p);
	
	@Query(value="select top 1 * from post where active = 1 and deleted_at = 0 order by post_id desc", nativeQuery = true)
	public Post getPostDesc();
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false' and users_id = ?1", nativeQuery = true)
	public List<Post> getPostsByUserId(String username);
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false'", nativeQuery = true)
	public List<Post> getPostsAll();
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false'", nativeQuery = true)
	public Page<Post> getPostsAll(Pageable p);
	
	@Query(value="select top 1 * from post where active = 'true' and deleted_at = 'false' and services_id = 4", nativeQuery = true)
	public Post getPostsAddNew();
	
	@Query(value="select * from post where active = 'true' and deleted_at = 'false' and services_id = 4", nativeQuery = true)
	public List<Post> getPostsDiamond();
	
	@Query(value="SELECT * FROM post "
			+ "GROUP BY post_id, post_title, post_content, create_at, end_date, acreage,price,addresss,link_video,services_id,types_id,direction,bed,juridical,balcony,toilet,interior,active,users_id,deleted_at "
			+ "HAVING AVG(price) < 100000000", nativeQuery = true)
	public List<Post> getPostsForYou();
	
	@Query(value="select * from post where active = 1 and post_title = ?1 or addresss = ?2 or addresss like %?3 or types_id = ?4", nativeQuery = true)
	public List<Post> searchPost(String title, String address, String province, Integer type);
}
