package com.poly.service;

import javax.servlet.http.HttpServletRequest;

import com.poly.bean.Pay;

public interface PaymentService {

	public String createOrder(Integer tatol, String orderInfo, String urlReturn);
	
	public Integer orderDetail(HttpServletRequest req);
	
	public Pay findByID(Integer id);
	
	public Pay findByTop1Desc();
	
	public Pay Create(Pay p);
	
	public Pay Update(Pay p);

	public void Delete(Integer id);

}
