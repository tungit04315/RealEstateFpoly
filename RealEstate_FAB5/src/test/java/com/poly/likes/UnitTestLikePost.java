package com.poly.likes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.poly.bean.Likes;
import com.poly.bean.Post;
import com.poly.bean.Users;
import com.poly.service.LikeService;
import com.poly.service.PostService;
import com.poly.service.UsersService;

@SpringBootTest
public class UnitTestLikePost {

	@Autowired
	LikeService likeService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	UsersService userService;
	
	@Test
	void getAllLikePost() {
		assertThat(likeService.getAll()).isNotNull();
	}
	
	@Test
	void getAllLikePostUser() {
		String username = "tungngayngo";
		assertThat(likeService.getAllPostLikes(username)).isNotNull();
	}
	
	@Test
	void getTop4LikePostUser() {
		String username = "tungngayngo";
		assertThat(likeService.getTop4PostLikes(username)).isNotNull();
	}
	
	@Test
	void getLikePostUserAndPost() {
		String username = "tungngayngo";
		int postId = 40;
		assertThat(likeService.findByUserIDAndPostID(username, postId)).isNotNull();
	}
	
	@Test
	@Order(1)
	void createPostLike() {
		ObjectMapper mapper = new ObjectMapper();
		Post p = postService.getFindByid(67);
		Users u = userService.findById("tungngayngo");
		
		JsonNode post = mapper.valueToTree(p);
		JsonNode user = mapper.valueToTree(u);
		JsonNode likePost = ((ObjectNode) mapper.createObjectNode()
				.put("likes_status", true)
				
				.set("post_id", post))
				.set("users",user);
		assertThat(likeService.CreateJsonNode(likePost)).isNotNull();
	}
	
	@Test
	void deletePostLike() {
		List<Likes> like = likeService.getAll();
		likeService.DeleteByUserIDAndPostID(like.get(0).getPost_id().getPost_id());
		
		assertThat(likeService.findById(39)).isNull();;
	}
}
