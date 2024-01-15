<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="ko">
<head>
    <title>Kakao Login Test - Set Nickname</title>
     <style>
            body {
                background-color: #F9F1E7;
            }
            .gray {
                color: gray;
            }
            .logo {
                 max-width: 10%;
                 height: auto;
            }
            .button {
                background-color: #FF7500;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
            }
            .bold {
                font-weight: bold;
            }
        </style>
</head>
<body>
    <div>
        <img src="../images/logo.png" class="logo" />
        <br/>
        <span class="gray bold">가입정보 입력</span>
        <br/>
        <img src="${kakaoProfileImg}" width="100px" height="100px" />
        <form method="post" action="/kakaoJoin">
            <span class="bold">이메일</span>
            <br/>
            <input name="email" value="${kakaoEmail}" readonly />
            <br/>
            <span class="bold">닉네임</span>
            <br/>
            <input name="userNickname" />
            <br/>
            <span class="gray">*최대 8자까지 입력 가능</span>
            <br/>
            <input type="submit" class="button" value="가입하기" />
        </form>
    </div>
</body>
</html>
