package com.poly.service;

import java.util.List;

import com.poly.bean.Likes;

public interface LikeService {
	public List<Likes> getAll();

	public List<Likes> getTop4PostLikes();

	public Likes Create(Likes l);

	public Likes Update(Likes l);

	public void Delete(Integer id);

	public Likes findById(Integer id);
}
