package com.poly.service_impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.bean.Post;
import com.poly.bean.Shares;
import com.poly.bean.Users;
import com.poly.dao.ShareDAO;
import com.poly.service.ShareService;
import com.poly.util.SessionService;

@Service
public class ShareServiceImpl implements ShareService{

	@Autowired
	ShareDAO dao;
	
	@Autowired
	SessionService ss;
	
	private static final String FACEBOOK_SHARE_URL = "https://www.facebook.com/dialog/share";
    private static final String APP_ID = "1035305550719180";
	
	@Override
	public String shareFacebook(String url) {
		
		Post p = ss.getAttribute("post_id");
		Users u = ss.getAttribute("user");
		
		String shareUrl = FACEBOOK_SHARE_URL +
                "?app_id=" + APP_ID +
                "&href=" + url +
                "&display=popup" +
                "&redirect_uri=" + url + "?id=" + p.getPost_id();
		
		Shares share = new Shares();
		share.setPost_id(p);
		share.setShares_date(new Date());
		share.setEmailTo(url);
		share.setUsers(u);
		dao.save(share);
		
        return "<script>window.location.href='" + shareUrl + "';</script>";
	}
	

	@Override
	public Shares createShare(Shares s) {
		// TODO Auto-generated method stub
		return dao.save(s);
	}

	@Override
	public Page<Shares> FindAll(String username, Pageable p) {
		// TODO Auto-generated method stub
		return dao.getListShare(username, p);
	}

}
