<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>login</title>

    <style>
        body {
            background-color: #F9F1E7;
            text-align: center;
        }

        .logo {
            width: 80px;
            height: 80px;
            padding-top: 100px;
            padding-bottom: 20px;
        }

        .context {
            color: #C2C2C2;
            font-size: 15px;
        }

        .kakao {
            max-width: 25%;
            height: auto;
            padding-top: 10px;
            padding-bottom: 10px;
        }
    </style>

</head>
<body>

<img src="../images/logo.png" class="logo"/>
<br/>
<span class="context">SNS 계정으로 간편 로그인</span>
</br>

<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.6.0/kakao.min.js"
        integrity="sha384-6MFdIr0zOira1CHQkedUqJVql0YtcZA1P0nbPrQYJXVJZUkTk/oX4U9GhUIs3/z8"
        crossorigin="anonymous"></script>

<a href="javascript:loginWithKakao()">
    <img src="../images/kakao_sync_large_wide.png" class="kakao"/>
</a>

<script>
    Kakao.init('6e1fedab86d6b96e90375545f2dc62da');

 function loginWithKakao() {
        Kakao.Auth.authorize({
            redirectUri: 'http://localhost:8080/kakao',
        }, function (authObj) {
            console.log('카카오 로그인 성공:', authObj);
            window.location.href = '/';
        }, function (err) {
            console.error('카카오 로그인 실패:', err);
        });
    }
</script>

</body>
</html>
