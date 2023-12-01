package com.poly.service_pack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.poly.bean.ServicePack;
import com.poly.service.ServicePackService;

@SpringBootTest
public class UnitTestServicePack {

	@Autowired
	ServicePackService servicePackService;
	
	@Test
	void getAll() {
		assertThat(servicePackService.getFindAll()).isNotNull();
	}
	
	@Test
	void getFindById() {
		List<ServicePack> list = servicePackService.getFindAll();
 		assertThat(servicePackService.FindBy(list.get(0).getServices_id())).isNotNull();
	}
	
	@Test
	void createServicePack() {
		ServicePack s = new ServicePack();
		s.setServices_location("tìm kiếm");
		s.setServices_name("Hiển thị đầu tìm kiếm");
		s.setServices_price(2000);
		assertThat(servicePackService.Create(s)).isNotNull();
	}
	
	@Test
	void updateServicePack() {
		List<ServicePack> list = servicePackService.getFindAll();
		ServicePack s = servicePackService.FindBy(list.get(0).getServices_id());
		s.setServices_location("tìm kiếm");
		s.setServices_name("Hiển thị đầu tìm kiếm");
		s.setServices_price(2000);
		assertThat(servicePackService.Create(s)).isNotNull();
	}
}
