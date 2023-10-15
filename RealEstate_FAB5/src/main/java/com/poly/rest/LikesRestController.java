package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.Likes;
import com.poly.bean.Users;
import com.poly.service.LikeService;
import com.poly.util.SessionService;

@RestController
public class LikesRestController {

	@Autowired
	LikeService likeService;
	
	@Autowired
	SessionService ss;
	
	@GetMapping("/likes")
	public List<Likes> getAll(){
		Users u = ss.getAttribute("user");
		return likeService.getTop4PostLikes(u.getUsername());
	}
	
}
