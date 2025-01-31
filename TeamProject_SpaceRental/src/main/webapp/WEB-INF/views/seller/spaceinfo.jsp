<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SpaceInfo</title>
</head>
<body>
	<%-- 네비바 --%>
	<c:choose>
		<c:when test="${empty sessionScope.id }">
        	<jsp:include page="/WEB-INF/include/navbar_sidebar_SessionX.jsp"/>
      	</c:when>
      	<c:otherwise>
	      	<c:choose>
	      		<c:when test="${dto.code eq 2 }">
	      	  		<jsp:include page="/WEB-INF/include/navbar_sessionO_seller.jsp"/>
	         		<jsp:include page="/WEB-INF/include/sidebar_seller.jsp"/>
	      		</c:when>
	      		<c:otherwise>
					<jsp:include page="/WEB-INF/include/navbar_sessionO_users.jsp"/>
			      	<jsp:include page="/WEB-INF/include/sidebar_user.jsp"/>
	      		</c:otherwise>
	      	</c:choose>
      	</c:otherwise>
   	</c:choose> 
	   
	<div class="container">
		<h1>공간을 등록해주세요.</h1>
		<form action="${pageContext.request.contextPath}/seller/insert" method="post" id="insertForm">
			<div>
				<label for="space_name">공간명</label><br />
            	<input type="text" name="space_name" id="space_name"/>
			</div>
			<div>
				카테고리
				<select name="cate_name">
					<option name="cate_name" value="파티룸">파티룸</option>
					<option name="cate_name" value="연습실">연습실</option>
					<option name="cate_name" value="스터디룸">스터디룸</option>
					<option name="cate_name" value="강의실">강의실</option>
					<option name="cate_name" value="공유주방">공유주방</option>	
				</select>
			</div>
			<div>
				<label for="oneliner">공간 한 줄 소개</label><br />
				<input type="text" name="oneliner" id="oneliner" />
			</div>
			<div>
				<label for="intro">공간 상세 소개</label><br />
				<textarea name="intro" id="intro" cols="30" rows="10"></textarea>
			</div>
			<input type="hidden" id="mainImagePath" name="mainImagePath" />
			<div>
				<label for="addr">주소</label><br />
				<input type="text" name="addr" id="addr" />
			</div>
		</form>
		<form action="${pageContext.request.contextPath}/seller/ajax_upload" method="post" id="ajaxForm"enctype="multipart/form-data">
		    <div>
		    	<label for="image">이미지</label>
		    	<input type="file" name="image" id="image" 
		    		accept=".jpg, .jpeg, .png, .JPG, .JPEG"/>
		    </div>
		</form>
  		<div class="img-wrapper">
     		<img />
  		</div>

		<button id="submitBtn" class="btn btn-primary">저장</button>
	</div>
	
	<!-- footer include -->
	<jsp:include page="/WEB-INF/include/footer.jsp"/>	  
	  
	<script src="${pageContext.request.contextPath}/js/gura_util.js"></script>
	<script>
		if(${userCode}!=2){
			alert("판매자 공간입니다.");
			location.href="${pageContext.request.contextPath}/";
		}
	</script>
	<script>
		//이미지를 선택했을 때, 실행할 함수 등록
		document.querySelector("#image").addEventListener("change", function(){
			//id 가 ajaxForm 인 form 을 ajax 전송 시킨다.
			const form = document.querySelector("#ajaxForm");
			//util 함수를 이용해서 ajax 전송
			ajaxFormPromise(form)
			.then(function(response){
				return response.json();
			})
			.then(function(data){
				//data : {mainImagePath:"/upload/xxx.jpg"} 형식의 obj
				console.log(data);
				//이미지 경로에 context Path 추가하기
				const path = "${pageContext.request.contextPath}" + data.mainImagePath;
				//img 태그에 경로 추가
				document.querySelector(".img-wrapper img").setAttribute("src", path);
				//위의 form 의 input hidden 요소에 value 로 넣어서 db 에 저장
				document.querySelector("#mainImagePath").value = data.mainImagePath;
			});
		});		


		document.querySelector("#submitBtn").addEventListener("click", function(){
			document.querySelector("#insertForm").submit();
		});
	</script>
	
	


</body>
</html>