package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Albums;
import com.poly.service.AlbumsService;

@RestController
public class AlbumsRestController {

	@Autowired
	AlbumsService albumsService;
	
	@PostMapping("/rest/create-albums")
	public Albums Create(@RequestBody JsonNode data) {
		return albumsService.CreateJson(data);
	}
	
	@PutMapping("/rest/update-albums")
	public Albums Update(@RequestBody JsonNode dt) {
		return albumsService.UpdateJson(dt);
	}
	
	@DeleteMapping("/rest/delete-albums")
	public void Delete(@Param("id") Integer id) {
		albumsService.Delete(id);
	}
	
	@GetMapping("/rest/find-by")
	public Albums getFindByObject(@Param("name")String name, @Param("id")Integer id){
		return albumsService.FindBy(name, id);
	}
	
	@RequestMapping("/rest/find-albums")
	public List<Albums> getAllPostByPostId(@Param("id") Integer id){
		return albumsService.findAlbumsByPostID(id);
	}
}
