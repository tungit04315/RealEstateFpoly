package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
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
	
	@GetMapping("/find-by-post-likes")
	public List<Likes> getPostLike() {
		Users u = ss.getAttribute("user");
		return likeService.getAllPostLikes(u.getUsername());
	}
	
	@GetMapping("/find-by-post-likes/{username}/{post_id}")
	public Likes getPostLikeUserAndPost(@PathVariable("username") String username,@PathVariable("post_id") Integer id) {
		return likeService.findByUserIDAndPostID(username, id);
	}
	
	@PostMapping("/likes-add")
	public Likes getCreate(@RequestBody JsonNode likeData) {
		return likeService.CreateJsonNode(likeData);
	}
	
	@DeleteMapping("/likes-delete/{post_id}")
	public void getDelete(@PathVariable("post_id") Integer id) {
		likeService.DeleteByUserIDAndPostID(id);
	}
}
