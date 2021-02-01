package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@RestController
@RequestMapping("/replies")
@Log4j
@AllArgsConstructor
public class ReplyController {
	
	private ReplyService service;
	//뎃글작성
	//요청경로 : http://localhost:8080/replies/new
	//요청법 : post / headers-> Content-Type 체크 / {"bno":165,"reply":"reply body", "replyer":"user01"} 
	@PostMapping(value ="/new",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		
		log.info("vo : " + vo); //vo가 잘들어왔는지
		
		int insertCount = service.register(vo);
		
		log.info("conunt : " + insertCount); //카운트가 잘들어왔는지
		
		if(insertCount == 1) {
			return new ResponseEntity<>("success",HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//여러개 뎃글 볼때
	//http://localhost:8080/replies/pages/165/1 <= 요청경로 165 내 db에 있는 게시물번호
	//요청법 get/ headers-> Content-Type 체크해제 
	@GetMapping(value = "/pages/{bno}/{page}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<ReplyVO>> getList(
			@PathVariable("page") int page, 
			@PathVariable("bno")Long bno) {
		
		Criteria cri = new Criteria(page, 10);
		List<ReplyVO> list = service.getList(cri, bno);
		
		log.info(list);
		
		return new ResponseEntity<List<ReplyVO>>(list, HttpStatus.OK);
	}
	
	//하나만 뎃글을 조회
	//http://localhost:8080/replies/7 <= 요청경로 7은 뎃글 rno의 고유 번호
	//요청법 get/ headers-> Content-Type 체크해제 
	@GetMapping(value = "/{rno}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		ReplyVO vo = service.get(rno);
		
		log.info(vo);
		
		return new ResponseEntity<ReplyVO>(vo, HttpStatus.OK);
	}
	
	//삭제
	//http://localhost:8080/replies/7 <= 요청경로 , 위랑동일  /method = RequestMethod.DELETE GetMapping, PostMapping처럼 DeleteMapping도 있다,
	//요청법 delete/ header-> Content-Type 체크
	@DeleteMapping(value="/{rno}", produces = MediaType.TEXT_PLAIN_VALUE) //rno값을 잘읽어서 리턴을 잘시켜줌.
	public ResponseEntity<String> remove(
			@PathVariable("rno") Long rno){
		
		int cnt = service.remove(rno); //1이면 ok, 1이 아니면 500번 에러를 발생.
		
		log.info(cnt);
		
		if(cnt == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK); //1이면 ok
		}else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); //500번 써버오류 발생
		} 
		
	}
	//수정
	//http://localhost:8080/replies/21 <- rno DB검색하거나 postman에 있는 get방식으로 rno를 찾기
	//요청법 put or path -> header있는거 삭제 , 사진참고
	//본문내용 {"bno":165,"reply":"수정된 본문", "replyer":"user01"} // rno는 시퀸스번호임으로 안보내도된다.
	@RequestMapping(value = "/{rno}",
			method = {RequestMethod.PUT, RequestMethod.PATCH}, //put 또는 Pathch로 와도 실행시킴 pathvariable로 일을 처리하게끔 사진참고.
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable Long rno){
		vo.setRno(rno);
		
		log.info(vo);
		
		int cnt = service.modify(vo);
		
		if(cnt == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK); //위랑 동일
		}else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
