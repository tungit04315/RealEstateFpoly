package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Albums;

public interface AlbumsDAO extends JpaRepository<Albums, Integer>{

	@Query(value="select * from albums where post_id = ?1", nativeQuery = true)
	List<Albums> getAlbums(Integer id);
	
	@Query(value="select * from albums where albums_name = ?1 and post_id = ?2", nativeQuery = true)
	Albums getAlbum(String name,Integer id);
}
