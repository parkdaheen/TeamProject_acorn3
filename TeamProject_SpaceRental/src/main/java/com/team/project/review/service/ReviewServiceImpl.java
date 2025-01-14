package com.team.project.review.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.project.review.dao.ReviewDao;
import com.team.project.review.dto.ReviewDto;
import com.team.project.users.dto.UsersDto;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired private ReviewDao reviewDao;

	@Override
	public void getList(HttpServletRequest request) {
		//한 페이지에 몇개씩 표시할 것인지
		final int PAGE_ROW_COUNT=5;
		//하단 페이지를 몇개씩 표시할 것인지
		final int PAGE_DISPLAY_COUNT=5;
		
		//보여줄 페이지의 번호를 일단 1이라고 초기값 지정
		int pageNum=1;
		//페이지 번호가 파라미터로 전달되는지 읽어와 본다.
		String strPageNum=request.getParameter("pageNum");
		//만일 페이지 번호가 파라미터로 넘어 온다면
		if(strPageNum != null){
			//숫자로 바꿔서 보여줄 페이지 번호로 지정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		
		//보여줄 페이지의 시작 ROWNUM
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지의 끝 ROWNUM
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		ReviewDto dto=new ReviewDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		//글 목록 얻어오기 
		List<ReviewDto> reviewlist = reviewDao.getList(dto);
		//전체글의 갯수
		int totalRow=reviewDao.getCount(dto);
		
		//하단 시작 페이지 번호 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		

		//전체 페이지의 갯수
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 전체 페이지 갯수보다 크다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다.
		}
		//view page 에서 필요한 값을 request 에 담아준다. 
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("reviewlist", reviewlist);
		request.setAttribute("totalRow", totalRow);
	}

	@Override
	public void getDetail(HttpServletRequest request) {
		//자세히 보여줄 글번호를 읽어온다. 
		int num=Integer.parseInt(request.getParameter("review_num"));
		//조회수 올리기
		reviewDao.addViewCount(num);
		//ReviewDto 객체를 생성해서 
		ReviewDto dto=new ReviewDto();
		//자세히 보여줄 글번호를 넣어준다. 
		dto.setReview_num(num);
		//글하나의 정보를 얻어온다.
		ReviewDto resultDto=reviewDao.getData(dto);
		
		//request scope 에 글 하나의 정보 담기
		request.setAttribute("dto", resultDto);
	}

	@Override
	public void saveContent(ReviewDto dto) {
		reviewDao.insert(dto);
	}

	@Override
	public void updateContent(ReviewDto dto) {
		reviewDao.update(dto);
	}

	@Override
	public void deleteContent(int review_num, HttpServletRequest request) {
		reviewDao.delete(review_num);
	}

	@Override
	public void getData(HttpServletRequest request) {
		//수정할 글번호
		int num=Integer.parseInt(request.getParameter("review_num"));
		//수정할 글의 정보 얻어와서 
		ReviewDto dto=reviewDao.getData(num);
		//request 에 담아준다.
		request.setAttribute("dto", dto);
	}
	
}
