package com.poly.service;

import java.util.List;

import com.poly.bean.Post;

public interface PostService {

	public List<Post> getPostLikes();

	public Post getFindByid(Integer id);

	public Post Create(Post p);

	public Post Update(Post p);

	public void Delete(Integer id);
	
	public List<Post> getAll();
}
