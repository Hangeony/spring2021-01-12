package org.zerock.domain;

import lombok.Data;

@Data
public class Rest1 {
	private String name;
	private int age;
	private boolean vote; // 기본값이 false
	private Object obj; //기본값이 null

}
