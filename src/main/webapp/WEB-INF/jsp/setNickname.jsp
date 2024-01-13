<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="ko">
<head>

    <title>Kakao Login Test - Set Nickname</title>
</head>
<body>
    <h2>로그인 성공
    <p>
    <div>
        <img src="${kakaoProfileImg}" width="100px" height="100px" />
        <form method="post" action="/kakaojoin">
            <input name="email" value="${kakaoEmail}" readonly />
            <input name="userNickname" />
            <input type="submit" value="회원가입" />
        </form>

    </div>

</body>
</html>