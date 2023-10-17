package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Likes;

public interface LikeService {
	public List<Likes> getAll();

	public List<Likes> getAllPostLikes(String username);
	
	public List<Likes> getTop4PostLikes(String username);

	public Likes findByUserIDAndPostID(String user, Integer post);
	
	public Likes Create(Likes l);
	
	public Likes CreateJsonNode(JsonNode likeData);

	public Likes Update(Likes l);

	public void Delete(Integer id);
	
	public void DeleteByUserIDAndPostID(Integer post);

	public Likes findById(Integer id);
}
