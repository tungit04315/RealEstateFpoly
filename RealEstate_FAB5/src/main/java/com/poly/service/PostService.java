package com.poly.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Post;

public interface PostService {
	
	public List<Post> getPostExpired(String username);
	
	public Page<Post> getPostDelete(String username, Pageable p);
	
	public Page<Post> getPostDelete(Pageable p);
	
	public List<Post> searchPost(String title, String address, String province, Integer type);
	
	public List<Post> getAll();
	
	public Page<Post> getPageAll(Pageable p);
	
	public List<Post> getAllDiamond();
	
	public List<Post> getAllHotsNew();
	
	public List<Post> getAllOften();
	
	public List<Post> getAllPostsForYou();
	
	public List<Post> getAllByUserId(String username);
	
	public Post getPostDesc();

	public Post getFindByid(Integer id);
	
	public Post getFindIntroducingThePost();

	public Post Create(JsonNode p);
	
	public Post UpdateJson(JsonNode p);

	public Post Update(Post p);

	public void Delete(Integer id);
	
	public void checkPostExpired() throws MessagingException;
	
	public Integer SoftDeletePost(Integer id);
	
	public Integer SoftActivePost(String active, Integer id);
}
