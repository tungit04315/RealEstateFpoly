package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Likes;
import com.poly.dao.LikesDAO;
import com.poly.service.LikeService;

@Service
public class LikesServiceImpl implements LikeService{

	@Autowired
	LikesDAO dao;
	
	@Override
	public List<Likes> getAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public List<Likes> getTop4PostLikes(String username) {
		// TODO Auto-generated method stub
		List<Likes> Likes = dao.getTop4PostLikes(username);
		if(Likes != null) {
			return Likes;
		}else {
			return null;
		}
		
	}

	@Override
	public Likes Create(Likes l) {
		// TODO Auto-generated method stub
		return dao.save(l);
	}

	@Override
	public Likes Update(Likes l) {
		// TODO Auto-generated method stub
		return dao.save(l);
	}

	@Override
	public void Delete(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public Likes findById(Integer id) {
		// TODO Auto-generated method stub
		Likes l = dao.findById(id).get();
		if(l != null) {
			return l;
		}else {
			return null;
		}
	}

}
