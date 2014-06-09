<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<title>로그인하기</title>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<link href="${ctx}/resources/css/login.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- header -->
		<div class="login-header">
			<h2 class="form-signin-heading">나무 커뮤니티</h2>
		</div>
		<!-- form -->
		<form class="form-signin" action="${ctx}/login" method="post">
			<input type="text" name="loginId" class="form-control" id="inputEmail" placeholder="아이디" required> <input type="password" name="password" class="form-control" id="inputPassword" placeholder="비밀번호" required>
			<div class="row form-btn">
				<button class="btn btn-large btn-warning" type="submit">로그인</button>
				<button class="btn btn-large btn-default" onclick="location.href='${ctx}/join'; return false;">회원가입</button>
			</div>
		</form>
		<!-- footer -->
		<div class="login-footer">
			<p>© NamooSori 2014.</p>
		</div>
	</div>
</body>
</html>