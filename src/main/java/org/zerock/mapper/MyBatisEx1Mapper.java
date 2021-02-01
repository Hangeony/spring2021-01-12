package org.zerock.mapper;

import org.zerock.domain.Ex1Criteria;

public interface MyBatisEx1Mapper {

	public int select1(Ex1Criteria cri);
	
	public int select2(Ex1Criteria cri);
	
	public int select3(Ex1Criteria cri);
	
	public int select4(Ex1Criteria cri);
	
	public int select5(Ex1Criteria cri);
	
	//forEach list나 map set 같은놈이 있을때 유용하게 쓰임
	public int select6(Ex1Criteria cri);
	//forEach map을활용 .
	public int select7(Ex1Criteria cri);
	//trim suffix
	public int select8(Ex1Criteria cri);
	//trim suffixOverrides
	public int select9(Ex1Criteria cri);
	//trim prefix
	public int select10(Ex1Criteria cri);
	//trim prefixOverrides
	public int select11(Ex1Criteria cri);
}
