package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
