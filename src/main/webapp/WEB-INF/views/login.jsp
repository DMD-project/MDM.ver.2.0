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

    <!-- 회원가입 폼 -->
    <form id="signupForm">
        <input type="text" id="email" name="email" placeholder="이메일">
        <input type="password" id="password" name="password" placeholder="비밀번호">
        <button type="button" onclick="signup()">회원가입</button>
    </form>

    <!-- 로그인 폼 -->
    <form id="loginForm">
        <input type="text" id="loginEmail" name="email" placeholder="이메일">
        <input type="password" id="loginPassword" name="password" placeholder="비밀번호">
        <button type="button" onclick="login()">로그인</button>
    </form>

    <script>
        Kakao.init('6e1fedab86d6b96e90375545f2dc62da');

        function loginWithKakao() {
                Kakao.Auth.authorize({
                    redirectUri: 'http://localhost:8080/kakao',
                });
        }

      function signup() {
             var email = document.getElementById('email').value;
             var password = document.getElementById('password').value;

             var xhr = new XMLHttpRequest();
             xhr.open('POST', '/signup', true);
             xhr.setRequestHeader('Content-Type', 'application/json');

             xhr.onreadystatechange = function() {
                 if (xhr.readyState === 4) {
                     if (xhr.status === 200) {
                         console.log('Signup successful');
                     } else {
                         console.error('Error in signup:', xhr.statusText);
                         document.getElementById('error-message').innerText = 'Signup failed. Please try again.';
                     }
                 }
             };

             xhr.send(JSON.stringify({ email: email, password: password }));
         }

         function login() {
             var email = document.getElementById('loginEmail').value;
             var password = document.getElementById('loginPassword').value;

             var xhr = new XMLHttpRequest();
             xhr.open('POST', '/login', true);
             xhr.setRequestHeader('Content-Type', 'application/json');

             xhr.onreadystatechange = function() {
                 if (xhr.readyState === 4) {
                     if (xhr.status === 200) {
                         console.log('Login successful');
                     } else {
                         console.error('Error in login:', xhr.statusText);
                         document.getElementById('error-message').innerText = 'Login failed. Please check your credentials and try again.';
                     }
                 }
             };

             xhr.send(JSON.stringify({ email: email, password: password }));
         }
    </script>

</body>
</html>
