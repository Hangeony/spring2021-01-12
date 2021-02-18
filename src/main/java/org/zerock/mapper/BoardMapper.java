package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
//	@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();

	public void insert(BoardVO board);
	
	/* 기존에 했던 작업.(대충적음)
	public void insert(BoardVO board) {
		String sql = "INSERT INTO tbl_board(bno, title, content, writer, regdate, updatedate)"
				+"VALUES(seq_board.nextval,?,?,?,sysdate, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, board.getTitle());
		pstmt.setString(2, board.getContent());
		pstmt.setString(1, board.getWriter());
		
		pstmt.updateQuery();
		
		close();

	}
	*/
	
	public void insertSelectKey(BoardVO board);
		//1. seq_board의 nextval를 먼저 조회(select) 2가지일을 하게 만들어야함.
		//2. 조회된 nextval를 insert에서 사용
	
	public BoardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVO board);

	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	//게시판이 몇개인지 확인하는 메소드
	//sql = SELECT count(*) FROM tbl_board

	public void updateReplyCnt(@Param("bno")Long bno, @Param("amount") int amount);
}
