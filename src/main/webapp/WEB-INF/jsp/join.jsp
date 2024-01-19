<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>join</title>

  <style>
    body {
        background-color: #F9F1E7;
    }
    div {
         width: 370px;
         margin: 0 auto;
    }
    .top {
        text-align: center;
    }
    .logo {
        width: 80px;
        height: 80px;

        padding-top: 100px;
        padding-bottom: 20px;
    }
    .context {
        color: #616161;
        font-size: 20px;
        font-weight: bold;

    }
    .input_context {
        font-size: 15px;
        font-weight: bold;
    }
    .input_context_email {
        background-color: #F5F5F5;
        border: 1px solid #D9D9D9;

        width: 340px;
        height: 40px;

        color: #BEBEBE;
        font-size: 15px;

        padding-left: 15px;
        margin-bottom: 10px;
    }
    .input_context_nickname {
        border: 1px solid #D9D9D9;

        width: 240px;
        height: 40px;

        font-size: 15px;

        padding-left: 15px;
        margin-bottom: 5px;
    }
    .check_nickname {
        background-color: #FFAB64;
        color: white;
        font-size: 16px;
        text-align: center;

        width: 93px;
        height: 33px;

        border: 1px solid #FFAB64;
        border-radius: 5px;

        margin-left: 7px;
    }
    .context_nickname {
        color: #BEBEBE;
        font-size: 15px;

        padding-left: 5px;
    }
    .button {
        background-color: #FF7500;
        color: white;
        font-size: 24px;
        text-align: center;

        width: 370px;
        height: 50px;

        border: none;
        border-radius: 10px;

        margin-top: 20px;
    }

  </style>
</head>

<script type="text/javascript">
    function check() {
        var userNickname = document.getElementById('userNickname').value;
            	location.href='/kakaoJoin/check/'+userNickname;
    }
</script>

<body>

<div>
    <div class="top">
        <img src="../images/logo.png" class="logo" />
        <br/>
        <span class="context">가입정보 입력</span>
    </div>

    <br/>
    <br/>
     <form method="post" action="/kakaoJoin">

        <span class="input_context">이메일</span>
        </br>
        <input name="email" class="input_context_email" value="${kakaoEmail}" readonly />
        </br>

        <span class="input_context">닉네임</span>
        </br>
        <input name="userNickname" id="userNickname"
               class="input_context_nickname" maxlength='8' placeholder="닉네임" />
        <button type="button" class="check_nickname" onclick="check()">중복확인</button>
        </br>

        <span class="context_nickname">* 최대 8자까지 입력 가능</span>

        <input type="submit" class="button" value="가입하기" />

    </form>
</div>

</body>
</html>