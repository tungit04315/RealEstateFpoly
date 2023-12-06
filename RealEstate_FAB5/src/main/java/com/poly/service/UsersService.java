package com.poly.service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.poly.bean.Users;

public interface UsersService {
	
	public Page<Users> findAll(String username, Pageable p);
	
	public List<Users> findAll();

	public Users findById(String id);

	public Users create(Users u);

	public Users update(Users u);

	public void delete(String id);
	
	public Users findByEmail(String email);
	
	public Users findByPhone(String phone);
	
	public List<String> getRolesByUsername(String username);
	
	public Optional<Users> getAccount(String username);
	
	public Users findByEmailOrPhone(String email, String phone);
	
	public String updateAvatar(String username, MultipartFile file);

	void loginFromOAuth2(OAuth2AuthenticationToken oauth2);
}
