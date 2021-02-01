package org.zerock.domain;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Ex1Criteria {
	
	private String type;
	private int bno;
	private String keyword;
	
	private List<String> list;
	private Map<String, String> map;

}
