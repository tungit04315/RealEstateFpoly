package com.poly.service;

import java.util.List;

import javax.mail.MessagingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Post;

public interface PostService {
	
	public List<Post> getPostExpired(String username);
	
	public List<Post> getPostDelete(String username);

	public Post getFindByid(Integer id);

	public Post Create(JsonNode p);

	public Post Update(Post p);

	public void Delete(Integer id);
	
	public List<Post> getAll();
	
	public void checkPostExpired() throws MessagingException;
	
	public Integer SoftDeletePost(Integer id);
}
