package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Post;
import com.poly.dao.PostDAO;
import com.poly.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostDAO dao;
	
	@Override
	public List<Post> getPostLikes() {
		// TODO Auto-generated method stub
		return dao.getPostsLike();
	}

	@Override
	public Post getFindByid(Integer id) {
		// TODO Auto-generated method stub
		Post p = dao.findById(id).get();
		if(p != null) {
			return p;
		}else {
			return null;
		}
	}

	@Override
	public Post Create(Post p) {
		// TODO Auto-generated method stub
		return dao.save(p);
	}

	@Override
	public Post Update(Post p) {
		// TODO Auto-generated method stub
		return dao.save(p);
	}

	@Override
	public void Delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Post> getAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
