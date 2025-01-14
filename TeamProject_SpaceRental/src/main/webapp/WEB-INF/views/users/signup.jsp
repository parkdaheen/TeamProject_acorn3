<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>/views/users/signup</title>
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
		<p class="alert alert-success">
			<c:choose>
				<c:when test="${code eq 1 }">
					관리자님 가입되었습니다.
				</c:when>
				<c:when test="${code eq 2 }">
					판매자님 가입되었습니다.
				</c:when>
				<c:otherwise>
					회원님 가입되었습니다.
				</c:otherwise>
			</c:choose>
			<a href="${pageContext.request.contextPath}/users/loginform">로그인 하러가기</a>
		</p>
	</div>
	
	  <!-- footer include -->
	  <jsp:include page="/WEB-INF/include/footer.jsp"/>
</body>
</html>