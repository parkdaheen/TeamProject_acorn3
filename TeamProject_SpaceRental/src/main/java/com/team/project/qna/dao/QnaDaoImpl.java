package com.team.project.qna.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team.project.qna.dto.QnaDto;

@Repository
public class QnaDaoImpl implements QnaDao{
	
	@Autowired private SqlSession session;
	
	@Override //글 목록
	public List<QnaDto> getList(QnaDto dto) {
		return session.selectList("qna.getList", dto);
	}

	@Override //글의 갯수
	public int getCount(QnaDto dto) {
		return session.selectOne("qna.getCount", dto);
	}

	@Override //글 추가
	public void insert(QnaDto dto) {
		session.insert("qna.insert", dto);
	}

	@Override //글 정보 얻어오기
	public QnaDto getData(int num) {
		return session.selectOne("qna.getData", num);
	}

	@Override
	public QnaDto getData(QnaDto dto) {
		return session.selectOne("qna.getData2", dto);
	}

	@Override //조회수 올리는 메소드
	public void addViewCount(int num) {
		session.update("qna.addViewCount", num);
	}

	@Override
	public void delete(int num) {
		session.delete("qna.delete", num);
	}

	@Override
	public void update(QnaDto dto) {
		session.update("qna.update",dto);
	}

}
