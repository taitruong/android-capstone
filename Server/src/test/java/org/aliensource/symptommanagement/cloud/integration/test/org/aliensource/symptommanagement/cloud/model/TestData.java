package org.aliensource.symptommanagement.cloud.integration.test.org.aliensource.symptommanagement.cloud.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 *  Convert an object to JSON using Jackson's ObjectMapper
	 *  
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
		return objectMapper.writeValueAsString(o);
	}
}
