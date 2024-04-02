<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>login</title>

    <style>
        body {
            font-family: 'Apple SD Gothic Neo';
            font-style: normal;
            font-weight: 150;
            background-color: #F9F1E7;
            text-align: center;
        }

        .logo {
            width: 80px;
            height: 80px;
            padding-top: 100px;
            padding-bottom: 20px;
        }

        .button_wrapper {
            padding-top: 20px;
            justify-content: center;
        }

        #home_btn {
              background-color: #F9F1E7;
              color: #FF7500;
              font-size: 17px;
              text-align: center;

              width: 220px;
              height: 50px;

              border: 1px solid #FF7500;
              border-radius: 10px;
        }

        #mypage_btn {
              background-color: #FF7500;
              color: white;
              font-size: 17px;
              text-align: center;

              width: 220px;
              height: 50px;

              border: none;
              border-radius: 10px;

              margin-left: 10px;
         }
    </style>
</head>
<body>

<img src="../../images/m2dm_mini_logo.png" class="logo"/>
<div>
    <div>
        <span class="context" style="color: #616161; font-size: 20px; font-weight: bold;">주문이 성공적으로 <span style="color: #FF7500;">완료</span>되었습니다.</span>
    </div>
    </br>
    <div>
        <div>
            <span class="button_wrapper">
                <button id='home_btn' onclick='home()'><b>홈으로 이동</b></button>
                <button id='mypage_btn' onclick='mypage()'><b>마이페이지로 이동</b></button>
            </span>
        </div>
    </div>
</div>

<script>
    function home() {
        location.href = '/';
    }

    function mypage() {
        location.href = '/mypage/view';
    }
</script>

</body>
</html>