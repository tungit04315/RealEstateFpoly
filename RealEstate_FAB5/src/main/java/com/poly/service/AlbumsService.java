package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Albums;

public interface AlbumsService {
	
	public List<Albums> findAlbumsByPostID(Integer id);

	public Albums Create(Albums album);
	
	public Albums FindBy(String name, Integer id);
	
	public Albums CreateJson(JsonNode data);
	
	public Albums UpdateJson(JsonNode data);

	public Albums Update(Albums album);

	public void Delete(Integer id);
}
