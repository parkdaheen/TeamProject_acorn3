package com.team.project.seller.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.team.project.seller.dto.SellerDto;
import com.team.project.seller.service.SellerService;
import com.team.project.users.service.UsersService;

@Controller
public class SellerController {
	@Autowired
	private SellerService service;
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/seller/spacelist")
	public ModelAndView list(ModelAndView mView) {
		service.getList(mView);
		mView.setViewName("seller/spacelist");
		return mView;
	}
	
	@RequestMapping("/seller/spaceinfo")
	public String spaceinfo(HttpSession session,ModelAndView mView) {
		usersService.getInfo(session, mView);
		return "seller/spaceinfo";
	}
	
	@RequestMapping("/seller/insert")

	public String insert(SellerDto dto, HttpServletRequest request, HttpSession session) {
		service.getUsersNum(request, session);
		dto.setUsers_num((Integer)request.getAttribute("users_num"));
		service.insert(dto, request);
		
		return "seller/insert";
	}
	
	@RequestMapping("/seller/spaceupdate")
	public String spaceUpdate(HttpServletRequest request) {
		service.getData(request);
		return "seller/spaceupdate";
	}
	
	
	@RequestMapping("/seller/update")
	public String update(SellerDto dto, HttpServletRequest request, HttpSession session) {
		service.update(dto, request);
		System.out.println(dto.getmainImagePath());
		System.out.println(dto.getSpace_name());
		
		return "seller/update";		
	}
	
	
	
	@RequestMapping("/seller/delete")
	public String delete(int space_num, HttpServletRequest request) {
		service.delete(space_num, request);
		return "redirect:/seller/spacelist"; //다시 한번 물어보는 것으로 수정예정
	}
	

	//사진 업로드 - ajax
	//json 으로 return 할 것
	@RequestMapping(value = "/seller/ajax_upload")
	@ResponseBody
	public Map<String, Object> ajaxUpload(SellerDto dto, HttpServletRequest request){		
		//form 에서 dto 로 데이터 받아옴
		//dto : MultipartFile image 를 가지고 있다.
		//request : imagePath 만드는데 사용, session 영역의 id 가져오는데 사용
		//return : { "imagePath" : "/upload/123456img_name.png" } 형식의 JSON 응답
		return service.uploadAjaxImage(dto, request);
	}
	
	//ajax 프로필 사진 업로드 요청처리
	@RequestMapping(value = "/users/image_upload", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> imageUpload(HttpServletRequest request, MultipartFile image){
		
		//서비스를 이용해서 이미지를 upload 폴더에 저장하고 리턴되는 Map 을 리턴해서 json 문자열 응답하기
		return service.saveImage(request, image);
	}
}
