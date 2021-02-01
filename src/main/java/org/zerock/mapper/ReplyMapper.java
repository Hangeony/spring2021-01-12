package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {

	public int insert(ReplyVO vo); //추가
	
	public ReplyVO read(Long rno); //읽고
	
	public int delete(Long rno); // 삭제
	
	public int update(ReplyVO reply); //수정 getRno #{rno} 파라미터가 하나일경우에는 명확히 알수있는데
	
	public List<ReplyVO> getListWithPaging(@Param("cri")Criteria cri, @Param("bno")Long bno);
	// 몇페이지에있는 게시물의 뎃글을 가져오는지 게시물 페이징처리랑 확인
	//두개일경우에 정확히@param으로 명시해줄 필요가 있음 
	//#{cri.rno}, #{bno.rno}로 명확히 알수있음
}
