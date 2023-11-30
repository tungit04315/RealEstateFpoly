package com.poly.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Maps;

public interface MapService {
	
	public Maps getMapById(Integer id);

	public Maps getMapByPostId(Integer id);

	public Maps create(JsonNode p);

	public Maps update (Maps m);
}
