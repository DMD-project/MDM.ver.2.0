<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>join</title>
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
            color: #616161;
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 30px;
        }
        input {
            width: 100%;
            height: 40px;
            border: 1px solid #C2C2C2;
            padding-left: 10px;
            margin-top: 2px;
        }
        input::placeholder {
            color: #C2C2C2;
        }
        button {
            background-color: #FF7500;
            color: white;
            font-size: 20px;
            text-align: center;
            width: 315px;
            height: 50px;
            border: none;
            border-radius: 10px;
            margin-top: 10px;
        }
    </style>
</head>

<body>

    <%@ include file="includes/header.jsp" %>

    <div style="width: 300px; margin: auto; margin-bottom: 100px;">
        <div style="text-align: center;">
            <img src="../images/logo.png" class="logo">
            <div class="context">회원가입</div>
        </div>
        <div>
            <span>이메일</span><br/>
            <input id="user_email" type="text" placeholder="사용할 이메일을 입력해 주세요.">
        </div>
        <div>
            <span>비밀번호</span><br/>
            <input id="user_password" type="password" placeholder="사용할 비밀번호를 입력해 주세요.">
            <span style="font-size: 13px; color: #BEBEBE;">* 영문, 숫자 조합 8자리~16자리 </span>
        </div>

        <div style="text-align: center;">
            <button id="submit">가입하기</button>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>

        function validateEmail(sEmail) {
            var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
            if (filter.test(sEmail))
                return true;
            else
                return false;
        }

        function validatePassword(sPassword) {
            var filter = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$/;
            if (filter.test(sPassword))
                return true;
            else
                return false;
        }

        $(document).on('click', '#submit', function() {
            var email = $('#user_email').val();
            var password = $('#user_password').val();

            if (email == "" || password == "")
                alert('가입 정보를 입력해 주세요.');
            else if (!validateEmail(email))
                alert('잘못된 이메일 입니다.');
            else if (!validatePassword(password))
                alert('잘못된 비밀번호 입니다.');
            else {
                $.ajax ({
                    type: 'POST',
                    url: '/general/signup',
                    data: JSON.stringify (
                        {
                            "email" : email,
                            "password" : password
                        }
                    ),
                    contentType : 'application/json',
                    success: function(data) {
                        alert('회원가입이 성공적으로 이루어졌습니다.\n로그인 페이지로 이동합니다.');
                        location.href="/login";
                    }
                })
            }
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>