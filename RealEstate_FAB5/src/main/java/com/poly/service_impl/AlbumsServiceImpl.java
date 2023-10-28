package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Albums;
import com.poly.dao.AlbumsDAO;
import com.poly.service.AlbumsService;

@Service
public class AlbumsServiceImpl implements AlbumsService{

	@Autowired
	AlbumsDAO dao;
	
	@Override
	public List<Albums> findAlbumsByPostID(Integer id) {
		// TODO Auto-generated method stub
		return dao.getAlbums(id);
	}

	@Override
	public Albums Create(Albums album) {
		// TODO Auto-generated method stub
		return dao.save(album);
	}

	@Override
	public Albums Update(Albums album) {
		// TODO Auto-generated method stub
		return dao.save(album);
	}

	@Override
	public void Delete(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

}
