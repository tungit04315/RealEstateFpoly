package com.poly.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import com.poly.bean.*;

public interface PostDAO extends JpaRepository<Post, Integer>{
	
	@Query(value="select * from post where active = 'false' and deleted_at = 'false' and users_id = ?1", nativeQuery = true)
	public List<Post> getPostsExpired(String username); 
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query(value = "update post set deleted_at = 'true' where post_id = ?1", nativeQuery = true)
	public Integer softDeletePost(Integer id);
	
	@Query(value="select * from post where active = 'false' and deleted_at = 'true' and users_id = ?1", nativeQuery = true)
	public List<Post> getPostsDelete(String username);
	
	@Query(value="select top 1 * from post where active = 1 and deleted_at = 0 order by post_id desc", nativeQuery = true)
	public Post getPostDesc();
}
