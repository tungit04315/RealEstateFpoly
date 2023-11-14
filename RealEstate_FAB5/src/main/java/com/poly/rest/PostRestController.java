package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Post;
import com.poly.service.AlbumsService;
import com.poly.service.PostService;

@RestController
public class PostRestController {

	@Autowired
	PostService postService;
	
	@Autowired
	AlbumsService albumService;
	
	@RequestMapping("/rest/list-post")
	public List<Post> getPostAll(){
		return postService.getAll();
	}
	
	@RequestMapping("/rest/list-post-diamond")
	public List<Post> getPostDiamond(){
		return postService.getAllDiamond();
	}
	
	@RequestMapping("/rest/new-post")
	public Post getPostNew() {
		return postService.getFindPostAddNew();
	}
	
	@RequestMapping("/post-id/{post_id}")
	public Post getPost(@PathVariable("post_id") Integer id) {
		return postService.getFindByid(id);
	}
	
	@RequestMapping("/create-post")
	public Post Create(@RequestBody JsonNode p){
		return postService.Create(p);
	}
	
	@RequestMapping("/rest/post-for-you")
	public List<Post> getPostForYou(){
		return postService.getAllPostsForYou();
	}
}
