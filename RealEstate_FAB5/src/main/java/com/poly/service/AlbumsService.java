package com.poly.service;

import java.util.List;

import com.poly.bean.Albums;

public interface AlbumsService {
	
	public List<Albums> findAlbumsByPostID(Integer id);

	public Albums Create(Albums album);

	public Albums Update(Albums album);

	public void Delete(Integer id);
}
