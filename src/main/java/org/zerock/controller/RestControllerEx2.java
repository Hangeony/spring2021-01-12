package org.zerock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Rest1;
import org.zerock.domain.Rest2;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("rest2")
public class RestControllerEx2 {
	
	@RequestMapping("/ex1")
	public String method1() {
		return "hello";
	}
	
	@RequestMapping("/ex2")
	public Rest1 method2() {
//		전송, 수신 방법이 Http를 따르고 있는데 HyperText Transper Protocol 전송하고 수신하는방법이 다 Text임
		log.info("method2");
		
		Rest1 r = new Rest1();
		r.setName("donald");
		r.setAge(33);
		
		return r;
	}
	
	@RequestMapping("/ex3")
	public String method3() {
		log.info("method3");
		
		Rest1 r = new Rest1();
		r.setName("donald");
		r.setAge(33);
		
//		String res = "이름 : " + r.getName() + ", " + "나이 : " + r.getAge(); 
		String res = "{\"name\":\"" + r.getName() + "\", \"age\":" + r.getAge() + "}";
		return res;
	}
	
	@RequestMapping("/ex4")
	public Rest1 method4() {
		log.info("method4");
		
		Rest1 r = new Rest1();
		r.setName("donald");
		r.setAge(33);
		r.setVote(true);
		
		return r;
	}

	@RequestMapping("/ex5")
	public Rest2 method5() {
		Rest2 r2 = new Rest2();
		r2.setAddress("seoul");
		
		Rest1 r1 = new Rest1();
		r1.setName("jeju");
		r1.setAge(100);
		r1.setVote(true);
		
		r2.setRest1(r1);
		
		return r2;
	}

}
