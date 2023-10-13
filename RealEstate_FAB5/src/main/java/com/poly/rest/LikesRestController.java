package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.Likes;
import com.poly.service.LikeService;

@RestController
public class LikesRestController {

	@Autowired
	LikeService likeService;
	
	@GetMapping("/likes")
	public List<Likes> getAll(){
		return likeService.getTop4PostLikes();
	}
	
}
