package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Maps;
import com.poly.service.MapService;

@RestController
public class MapRestController {
//abc
	@Autowired
	MapService mapService;
	
	@RequestMapping("/map/{post_id}")
	public Maps getMapAddress(@PathVariable("post_id") Integer id) {
		return mapService.getMapByPostId(id);
	}
	
	@RequestMapping("/create-mapAddress")
	public Maps createMapAddress(@RequestBody JsonNode p){
		return mapService.create(p);
	}
	
//	@RequestMapping("/update-mapAddress")
//	public Maps updateMapAddress( Maps p){
//		return mapService.update(p);
//	}
}
