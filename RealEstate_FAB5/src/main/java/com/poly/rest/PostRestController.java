package com.poly.rest;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.poly.bean.Post;
import com.poly.service.AlbumsService;
import com.poly.service.PostService;

@RestController
public class PostRestController {

	@Autowired
	PostService postService;
	
	@Autowired
	AlbumsService albumService;
	
	@RequestMapping("/post-id/{post_id}")
	public Post getPost(@PathVariable("post_id") Integer id) {
		return postService.getFindByid(id);
	}
	
	
}
