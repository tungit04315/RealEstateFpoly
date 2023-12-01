package com.poly.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.poly.bean.Albums;
import com.poly.bean.Maps;
import com.poly.bean.Post;
import com.poly.service.AlbumsService;
import com.poly.service.MapService;
import com.poly.service.PostService;
@SpringBootTest
class UnitTestPost {

	@Autowired
	PostService postService;
	
	@Autowired
	AlbumsService albumsService;
	
	@Autowired
	MapService mapService;
	
	
	@Test
	void getPostExpirect() {
		String username = "tungngayngo";
		Assertions.assertThat(postService.getPostExpired(username)).isNotNull();
	}
	
	@Test
	void getPostDelete() {
		String username = "tungngayngo";
		Pageable pe = PageRequest.of(0, 4);
		Assertions.assertThat(postService.getPostDelete(username, pe)).isNotNull();
	}
	
	@Test
	void getSearchPost() {
		String title="mới đăng";
		String address="nguyễn văn linh, cần thơ";
		String province="cần thơ";
		int type= 1;
		
		Assertions.assertThat(postService.searchPost(title, address, province, type)).isNotNull();
	}
	
	@Test
	void getAllPostDesc() {
		Assertions.assertThat(postService.getPostDesc()).isNotNull();
	}
	
	@Test
	void getPostFindById() {
		int post_id = 39;
		Assertions.assertThat(postService.getFindByid(post_id)).isNotNull();
	}
	
	@Test
	void getAllPostDiamond() {
		Assertions.assertThat(postService.getAllDiamond()).isNotNull();
	}
	
	@Test
	void getAllPost() {
		Assertions.assertThat(postService.getAll()).isNotNull();
	}
	
	@Test
	void getAllPostForYou() {
		Assertions.assertThat(postService.getAllPostsForYou()).isNotNull();
	}
	
	
	@Test
	void SoftDeletePost() {
		int post_id = 55;
		Assertions.assertThat(postService.SoftDeletePost(post_id)).isNotNull();
	}
	
	@Test
	void softActionPost() {
		int post_id = 55;
		String active = "true";
		Assertions.assertThat(postService.SoftActivePost(active, post_id)).isNotNull();
	}
	
	@Test
	void createPostJson() {
		ObjectMapper mapper = new ObjectMapper();

        JsonNode servicesId = mapper.createObjectNode()
                .put("services_id", 2)
                .put("services_name", "Gói Bạc")
                .put("services_price", 1.3)
                .put("services_location", "Vị trí đầu tìm kiếm");

        JsonNode typesId = mapper.createObjectNode()
                .put("types_id", 5)
                .put("types_name", "Nhà mặt phố");

        JsonNode usersId = mapper.createObjectNode()
                .put("username", "tungngayngo")
                .put("passwords", "$2a$10$zHTz1lnDZFYaCHKQuDQB1eFqGBRTllVyCAW0fOhlnSLf9p6aA/lym")
                .put("email", "tungit04315@gmail.com")
                .put("phone", "0838565542")
                .put("fullname", "Tùng Ngây Ngô")
                .put("avatar", (String) null)
                .put("addresss", "Khóm 6a, Thị trấn sống đốc")
                .put("fail_login", 0)
                .put("active", true)
                .put("gender", true)
                .put("create_block", (String) null)
                .put("birthday", "2002-11-08");

        JsonNode jsonNode = ((ObjectNode) ((ObjectNode) ((ObjectNode) mapper.createObjectNode()
                .put("post_title", "Bất động sản unit test hàm create")
                .put("post_content", "test unit test spring boot test hàm create post json")
                .put("create_at", "2023-11-29")
                .put("end_date", "2023-12-09")
                .put("acreage", 220.0)
                .put("price", 40000000)
                .put("addresss", "Khóm 6, Phường Cửa Đông, Quận Hoàn Kiếm,  Hà Nội")
                .putNull("linkVideo")
                .set("services_id", servicesId))
                .set("types_id", typesId))
                .put("direction", "Hướng Đông")
                .put("bed", 1)
                .put("juridical", "Hợp đồng mua bán")
                .put("balcony", "Hướng Tây Nam")
                .put("toilet", 1)
                .put("interior", "Cơ bản")
                .put("active", true)
                .set("users_id", usersId))
                .put("deletedAt", false);
        
        
        Post postNew = postService.Create(jsonNode);
        JsonNode postNewNode = mapper.valueToTree(postNew);
        
        JsonNode albums = mapper.createObjectNode()
        		.put("albums_name", "7d2d66e0.jpg")
        		.set("post_id", postNewNode);
        
        JsonNode maps = mapper.createObjectNode()
        		.put("maps_address", "105.8464059031786,21.034556540719535")
        		.set("post_id", postNewNode);
        
        Albums albumsSave = albumsService.CreateJson(albums);
        Maps mapsSave = mapService.create(maps);
        
		Assertions.assertThat(postNew).isNotNull();
		Assertions.assertThat(albumsSave).isNotNull();
		Assertions.assertThat(mapsSave).isNotNull();
	}
	
	@Test
	void updatePostJson() {
		ObjectMapper mapper = new ObjectMapper();

        JsonNode servicesId = mapper.createObjectNode()
                .put("services_id", 2)
                .put("services_name", "Gói Bạc")
                .put("services_price", 1.3)
                .put("services_location", "Vị trí đầu tìm kiếm");

        JsonNode typesId = mapper.createObjectNode()
                .put("types_id", 5)
                .put("types_name", "Nhà mặt phố");

        JsonNode usersId = mapper.createObjectNode()
                .put("username", "tungngayngo")
                .put("passwords", "$2a$10$zHTz1lnDZFYaCHKQuDQB1eFqGBRTllVyCAW0fOhlnSLf9p6aA/lym")
                .put("email", "tungit04315@gmail.com")
                .put("phone", "0838565542")
                .put("fullname", "Tùng Ngây Ngô")
                .put("avatar", (String) null)
                .put("addresss", "Khóm 6a, Thị trấn sống đốc")
                .put("fail_login", 0)
                .put("active", true)
                .put("gender", true)
                .put("create_block", (String) null)
                .put("birthday", "2002-11-08");

        JsonNode jsonNode = ((ObjectNode) ((ObjectNode) ((ObjectNode) mapper.createObjectNode()
                .put("post_id", 55)
                .put("post_title", "Bất động sản unit test")
                .put("post_content", "test unit test spring boot")
                .put("create_at", "2023-11-29")
                .put("end_date", "2023-12-09")
                .put("acreage", 220.0)
                .put("price", 40000000)
                .put("addresss", "Khóm 6, Phường Cửa Đông, Quận Hoàn Kiếm,  Hà Nội")
                .putNull("linkVideo")
                .set("services_id", servicesId))
                .set("types_id", typesId))
                .put("direction", "Hướng Đông")
                .put("bed", 1)
                .put("juridical", "Hợp đồng mua bán")
                .put("balcony", "Hướng Tây Nam")
                .put("toilet", 1)
                .put("interior", "Cơ bản")
                .put("active", true)
                .set("users_id", usersId))
                .put("deletedAt", false);
        
        JsonNode albums = mapper.createObjectNode()
        		.put("albums_id", "22")
        		.put("albums_name", "e8f8087f.jpg")
        		.set("post_id", jsonNode);
        
        JsonNode maps = mapper.createObjectNode()
        		.put("maps_id", "1")
        		.put("maps_address", "104.83897719231504,9.04055587425431")
        		.set("post_id", jsonNode);
		
		Assertions.assertThat(postService.UpdateJson(jsonNode)).isNotNull();
		Assertions.assertThat(albumsService.UpdateJson(albums)).isNotNull();
		Assertions.assertThat(mapService.create(maps)).isNotNull();
	}
}
