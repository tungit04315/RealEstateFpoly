package com.poly.type_property;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.poly.bean.TypePropertys;
import com.poly.service.TypePropertyService;

@SpringBootTest
public class UnitTestTypeProperty {

	@Autowired
	TypePropertyService typeService;
	
	@Test
	void getAll() {
		assertThat(typeService.findAll()).isNotNull();
	}
	
	@Test
	void findSuggest() {
		assertThat(typeService.findSuggest()).isNotNull();
	}
	
	@Test
	void findSelectTop6() {
		assertThat(typeService.findSelectTop6()).isNotNull();
	}
	
	@Test
	void getFindById() {
		List<TypePropertys> list = typeService.findAll();
		assertThat(typeService.findById(list.get(0).getTypes_id())).isNotNull();
	}
	
	@Test
	void createTypeProperty() {
		TypePropertys p = new TypePropertys();
		p.setTypes_name("Nhà phố");
		assertThat(typeService.create(p)).isNotNull();
	}
	
	@Test
	void updateTypeProperty() {
		List<TypePropertys> list = typeService.findAll();
		TypePropertys p = typeService.findById(list.get(0).getTypes_id());
		p.setTypes_name("Nhà phố Cần Thơ");
		assertThat(typeService.update(p)).isNotNull();
	}
}
