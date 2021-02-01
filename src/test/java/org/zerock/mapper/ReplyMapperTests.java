package org.zerock.mapper;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.ReplyVO;
import org.zerock.persistence.DataSourceTests;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	private Long[] bnoArr = {165L, 164L, 162L, 161L, 125L};
	
	@Setter(onMethod_= @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testExist() {
		assertNotNull(mapper);
	}
	
	@Test //책 383P
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			log.info(i + "," + (i % 5)); // 시작값과 종료값을 1~10까지의 stream을 리턴해줌 완성된 stream을 한번씩 파라미터에 들어가서 본문이 실행됨.
			
			ReplyVO vo = new ReplyVO();
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("뎃글테스트" + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
		});
	}
	
	
	
	
	@Test
	public void testCreate2() {
		ReplyVO vo = new ReplyVO();
//		vo.setRno(rno); 시컨스로 가져와서 필요없음.
		vo.setBno(125L); //tbl_board 테이블에 값이 있는지 확인
		vo.setReply("뎃글 테스트");
		vo.setReplyer("user00");
		
		mapper.insert(vo);
		
		try {
			vo.setBno(160L);
			mapper.insert(vo);
			fail();
		}catch(Exception e) {
			
		}
	}
	
	@Test
	public void testRead() {
		Long rno = 3L;
		
		ReplyVO vo = mapper.read(rno);
		
		assertEquals("뎃글테스트1", vo.getReply());
	}
	
	@Test
	public void testDelete() {
		Long rno = 12L;
		mapper.delete(rno);
	}
	
	@Test
	public void testUpdate() {
		ReplyVO vo = new ReplyVO();
		vo.setRno(11L);
		vo.setReply("수정된 댓글");
		mapper.update(vo);
		
		vo = mapper.read(11L);
		
		assertEquals("수정된 댓글", vo.getReply());
	}
	
	@Test
	public void testList() {
		List<ReplyVO> list = mapper.getListWithPaging(null, 125L);
		assertNotEquals(0, list.size());
	}
	
}
