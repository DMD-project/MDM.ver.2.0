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
        }
        span {
            color: #616161;
            font-size: 15px;
        }
        div {
            margin-bottom: 10px;
        }
        .logo {
            width: 80px;
            height: 80px;
            padding-bottom: 20px;
        }
        .context {
            color: #C2C2C2;
            font-size: 15px;
        }
        input {
            width: 300px;
            height: 40px;
            border: 1px solid #C2C2C2;
            padding-left: 10px;
            margin-top: 2px;
        }
        input::placeholder {
            color: #C2C2C2;
        }
        #login {
            background-color: #FF7500;
            color: #FFFFFF;
            font-size: 20px;
            text-align: center;
            width: 315px;
            height: 50px;
            border: none;
            border-radius: 10px;
            margin-top: 10px;
        }
        #join {
            background-color: #FFD6A4;
            color: #FF7500;
            font-size: 20px;
            text-align: center;
            width: 315px;
            height: 50px;
            border: 1px solid #FF7500;
            border-radius: 10px;
            margin-top: 10px;
        }
        .kakao {
            width: 315px;
            margin-top: 3px;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div style="width: 315px; margin: auto; margin-bottom: 100px;">
        <div style="text-align: center;">
            <img src="../images/logo.png" class="logo">
            <div>
                <span style="font-size: 14px; color: #C2C2C2;">SNS계정으로 간편 로그인</span>
                <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.6.0/kakao.min.js"
                        integrity="sha384-6MFdIr0zOira1CHQkedUqJVql0YtcZA1P0nbPrQYJXVJZUkTk/oX4U9GhUIs3/z8"
                        crossorigin="anonymous"></script>
                <a href="javascript:loginWithKakao()"><img src="../images/kakao_sync_large_wide.png" class="kakao"/></a>
            </div>
        </div>
        <div style="margin-top: 20px;">
            <span>이메일</span><br/>
            <input id="user_email" type="text" placeholder="이메일을 입력해 주세요.">
        </div>
        <div>
            <span>비밀번호</span><br/>
            <input id="user_password" type="password" placeholder="비밀번호를 입력해 주세요.">
        </div>
        <div style="text-align: center;">
            <button id="login">로그인</button>
            <button id="join">회원가입</button>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        Kakao.init('6e1fedab86d6b96e90375545f2dc62da');
        function loginWithKakao() {
            Kakao.Auth.authorize({
                redirectUri: 'http://localhost:8080/kakao',
            });
        }

        $(document).on('click', '#login', function() {
            var email = $('#user_email').val();
            var password = $('#user_password').val();
            alert(email + " " + password);
            if (email == "" || password == "")
                alert('가입 정보를 입력해 주세요.');
            else {
                $.ajax ({
                    type: 'POST',
                    url: '/general/login',
                    data: JSON.stringify (
                        {
                            "email" : email,
                            "password" : password
                        }
                    ),
                    contentType : 'application/json',
                    error: function(request, status, error) {
                        alert('로그인 정보가 일치하지 않습니다.');
                    },
                    success: function(data) {
                        location.href="/";
                    }
                })
            }
        });

        $(document).on('click', '#join', function() {
            location.href="/join";
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>
