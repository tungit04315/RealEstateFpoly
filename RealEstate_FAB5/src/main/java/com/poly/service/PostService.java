package com.poly.service;

import java.util.List;

import javax.mail.MessagingException;

import com.poly.bean.Post;

public interface PostService {

	public List<Post> getPostLikes();
	
	public List<Post> getPostExpired();

	public Post getFindByid(Integer id);

	public Post Create(Post p);

	public Post Update(Post p);

	public void Delete(Integer id);
	
	public List<Post> getAll();
	
	public void checkPostExpired() throws MessagingException;
}
