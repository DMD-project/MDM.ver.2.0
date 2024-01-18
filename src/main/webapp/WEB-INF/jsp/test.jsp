<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>

    <title>(Test)Main</title>
</head>
<body>
    <h4> Hi m2dm
    <div>
    ${session.nickname}
        <c:if test="${empty nickname}">
            로그인
        </c:if>
        <c:if test="${!empty nickname}">
            <a href="https://kauth.kakao.com/oauth/logout?client_id=bbb0d5e603062dd02da05a9fe89b0c1e&logout_redirect_uri=http://localhost:8080/logout">로그아웃</a>
            <a href="<c:url value="/deleteKakao"></c:url>">회원탈퇴</a>
        </c:if>
    </div>
</body>
</html>