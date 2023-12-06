package com.poly.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.bean.Auth;
import com.poly.bean.Pay;
import com.poly.bean.Ranks;
import com.poly.bean.Users;
import com.poly.dao.AuthDAO;
import com.poly.dao.UsersDAO;
import com.poly.service.*;

@Service
public class UsersServiceImpl implements UsersService{
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	UsersDAO dao;
	
	@Autowired
	AuthDAO authDAO;

	@Autowired
	PaymentService payService;
	
	@Autowired
	RanksService rankService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	RoleService roleService;
	
	@Override
	public Page<Users> findAll(String username, Pageable p) {
		return dao.getListUserAdmin(username, p);
	}

	@Override
	public Users findById(String id) {
		Users users = dao.findById(id).orElse(null);
		if(users != null) {
			return users;
		}else {
			return null;
		}
	}

	@Override
	public Users create(Users u) {
		return dao.save(u);
	}

	@Override
	public Users update(Users u) {
		return dao.save(u);
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public Users findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users findByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRolesByUsername(String username) {
		List<String> roleNames = new ArrayList<>();

		List<Auth> authorities = authDAO.findAll();

		for (Auth auth : authorities) {
			if(auth.getUsers().getUsername().equals(username)){
				roleNames.add(auth.getRoles().getRoles_id());
			}
		}
		return roleNames;
	}

	@Override
	public Optional<Users> getAccount(String username) {
		return dao.findById(username);
	}

	@Override
	public Users findByEmailOrPhone(String email, String phone) {
		// TODO Auto-generated method stub
		return dao.getUserFindByPhoneOrEmail(email, phone);
	}
	
	@Override
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
		// String fullname = oauth2.getPrincipal().getAttribute("name");
		String email = oauth2.getPrincipal().getAttribute("email");
		String idToken = oauth2.getPrincipal().getAttribute("id_token");
		System.out.println("123123"+idToken);
		Users Umail = findByEmailOrPhone(email, null);
		if(Umail == null) {
		String password = Long.toHexString(System.currentTimeMillis());
		//payment
		Pay newpay = new Pay();
		newpay.setPay_money((long)0.00);
		payService.Create(newpay);
		Pay payFind =  payService.findByTop1Desc();
		
		//rank
		Ranks rank = rankService.findById(1);
		Users u = new Users();
		u.setUsername(email);
		u.setEmail(email);
		OAuth2User oAuth2User;
		String phoneNumber = oauth2.getPrincipal().getAttribute("phone_number");
		u.setPhone(phoneNumber);
		u.setPasswords(pe.encode(password));
		u.setPay_id(payFind);
		u.setRanks_id(rank);
		userService.create(u);

		Auth uAuth = new Auth();
		uAuth.setUsers(u);
		uAuth.setRoles(roleService.findbyId("user"));
		authService.create(uAuth);
		
		UserDetails user = User.withUsername(email).password(pe.encode(password)).roles("USER").build();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		}
		UserDetails user = User.withUsername(email).password(Umail.getPasswords()).roles("USER").build();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Override
	public String updateAvatar(String username, MultipartFile file) {
//		Optional<Users> user = dao.findById(username);
//		
//		if (user != null) {
//            String fileName = "avatar_" + username + "_" + System.currentTimeMillis() + ".png";
//            String uploadDir = "/path/to/upload/directory"; 
//            // Thay đổi đường dẫn thư mục lưu ảnh của bạn
//
//            FileUploadUtil.saveFile(uploadDir, fileName, file);
//
//            // Cập nhật đường dẫn avatar trong cơ sở dữ liệu
//            user.setAvatar(fileName);
//            userRepository.save(user);
//
//            return fileName;
//        }

		return null;
	}

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

}
