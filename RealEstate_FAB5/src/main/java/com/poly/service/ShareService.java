package com.poly.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.bean.Shares;

public interface ShareService {
	
	public String shareFacebook(String url);

	public Shares createShare(Shares s);
	
	public Page<Shares> FindAll(String username, Pageable p);
}
