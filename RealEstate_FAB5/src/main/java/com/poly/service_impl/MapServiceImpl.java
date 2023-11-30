package com.poly.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.bean.Maps;
import com.poly.dao.MapDAO;
import com.poly.service.MapService;

@Service
public class MapServiceImpl implements MapService{
	@Autowired
	MapDAO dao;

	@Override
	public Maps getMapById(Integer id) {
		Maps m = dao.findById(id).get();
		if(m != null) {
			return m;
		}else {
			return null;
		}
	}

	@Override
	public Maps getMapByPostId(Integer id) {
		Maps m = dao.getMapByPostId(id);
		if(m != null) {
			return m;
		}else {
			return null;
		}
	}

	@Override
	public Maps create(JsonNode p) {
		// TODO Auto-generated method stub
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				Maps maps = mapper.convertValue(p, Maps.class);
				return dao.save(maps);
	}

	public Maps update(Maps m) {
		return dao.save(m);
	}
}
