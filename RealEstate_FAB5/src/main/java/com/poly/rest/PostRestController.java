package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Maps;
import com.poly.bean.Post;
import com.poly.service.*;

@RestController
public class PostRestController {

	@Autowired
	PostService postService;
	
	@Autowired
	AlbumsService albumService;
	
	@Autowired
	MapService mapService;
	
	@RequestMapping("/rest/list-post")
	public List<Post> getPostAll(){
		return postService.getAll();
	}
	
	@RequestMapping("/rest/list-post-expirect")
	public List<Post> getPostExpirectAll(@Param("id") String id){
		return postService.getPostExpired(id);
	}
	
	
	@RequestMapping("/rest/search-post")
	public List<Post> searchPost(@Param("title") String title, 
			@Param("address") String address, @Param("province") String province,
			@Param("type") Integer type){
		return postService.searchPost(title, address, province, type);
	}
	
	@RequestMapping("/rest/list-post-diamond")
	public List<Post> getPostDiamond(){
		return postService.getAllDiamond();
	}
	
	@RequestMapping("/rest/list-post-hots-new")
	public List<Post> getPostsDesc(){
		return postService.getAllHotsNew();
	}
	
	@RequestMapping("/rest/list-post-often")
	public List<Post> getPostOften(){
		return postService.getAllOften();
	}
	
	@RequestMapping("/rest/introducing-the-post")
	public Post getPostNew() {
		return postService.getFindIntroducingThePost();
	}
	
	@RequestMapping("/post-id/{post_id}")
	public Post getPost(@PathVariable("post_id") Integer id) {
		return postService.getFindByid(id);
	}
	
	@RequestMapping("/create-post")
	public Post Create(@RequestBody JsonNode p){
		return postService.Create(p);
	}
	
	@PutMapping("/update-post")
	public Post Update(@RequestBody JsonNode p){
		return postService.UpdateJson(p);
	}
	
	@RequestMapping("/rest/post-for-you")
	public List<Post> getPostForYou(){
		return postService.getAllPostsForYou();
	}
	
	@GetMapping("/edit-post")
	public String editPost() {
		return "redirect:/home/post";
	}
	
	@RequestMapping("/map/{post_id}")
	public Maps getMapAddress(@PathVariable("post_id") Integer id) {
		return mapService.getMapByPostId(id);
	}

	@RequestMapping("/create-mapAddress")
	public Maps createMapAddress(@RequestBody JsonNode p){
		return mapService.create(p);
	}
	
}
