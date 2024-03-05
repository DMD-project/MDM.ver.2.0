<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>mypage</title>

    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>
    <style>
        span {
            cursor: pointer;
        }
        .mypage_content {
            background-color: #F9F1E7;
            color: #333333;

            height: 200px;
            padding: 20px 60px;
        }
        .mypage_content_header {
            margin-bottom: 20px;
        }
        .mypage_content_main {
            margin-top: 70px;
        }

        .under_content {
            display : flex;
            justify-content: center;
        }
        .nav_mypage {
            width: 20%;
            padding-top: 30px;
        }
        .nav_mypage span {
            color: #616161;
        }
        .nav_mypge span:hover {
            color: #FF7500;
        }
        li {
            list-style: none;
            padding-bottom: 20px;
        }
        .under_content_main {
            width: 80%;
            padding: 30px;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="mypage_content">

        <div class="mypage_content_header">
            <div style="float: left; color: #FF7500; font-size: 20px;"><b>마이페이지</b></div>
            <div style="float: right;"><span class="link">자주 묻는 질문</span></div>
        </div>

        <div class="mypage_content_main">
            <div style="float: left;">
                <div class="user_profile_img" style="float:left; padding-left: 10px; padding-right: 30px;"><i class="fa-solid fa-circle-user fa-6x"></i></div>
                <div style="float:left; padding-top: 20px;">
                    <span id="user_nickname" style="font-size: 40px; margin-top:200px; font-weight: bold;">user_nickname</span>
                    <span id="update_user_info" style="font-size: 13px; margin-left: 10px;">회원정보변경</span>
                    <br/>
                    <span id="user_email" style="margin-left: 5px;">user_email@gmail.com</span>
                </div>
            </div>

            <div style="float: right; padding-top: 30px; ">
                <div class="review" style="float: right; padding-right: 120px; align: right;">
                    <span class="link"><i class="fa-solid fa-pencil fa-2x" style="padding-bottom: 10px;"></i><br/>후기 작성</span></div>
                <div class="favorite" style="float: right; padding-right: 30px;">
                    <span class="link"><i class="fa-regular fa-heart fa-2x" style="padding-bottom: 10px;"></i><br/>관심 상품</span></div>
            </div>
        </div>
    </div>

    <div class="under_content">
        <nav class="nav_mypage">
            <ul>
                <li style="font-size: 20px; color: #333333;"><b>나의 쇼핑 활동</b></li>
                <li><span>관심 상품</span></li>
                <li><span>구매 내역</span></li>
                <li><span>중고거래 판매 내역</span></li>
                <li><span>중고거래 구매 내역</span></li>
                <li><span>공동구매 구매 내역</span></li>
                <li><span>내가 작성한 후기</span></li>
            </ul>
        </nav>

        <div class="under_content_main_userInfo" style="width: 100%; background-color: red; padding-top: 45px; padding-left: 30px;">

        </div>
    </div>

    <%@ include file="includes/footer.jsp" %>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        function getCookie(name) {
            const cookies = document.cookie.split(';');
            for (let i = 0; i < cookies.length; i++) {
                const cookie = cookies[i].trim();
                if (cookie.startsWith(name + '=')) {
                    return cookie.substring(name.length + 1);
                }
            }
            return null;
        }

        $(document).ready(function() {
            $.ajax ({
                type: 'GET',
                url: '/mypage',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);

                    if (!token) {
                        alert("로그인이 필요합니다.");
                        return;
                    }
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                error: function(xhr, status, error){
                    if(status == 404) {
                        alert("존재하지 않는 사용자입니다.");
                        location.href="http://localhost:8080";
                    } else {
                        alert("로그인이 필요합니다.");
                        location.href="http://localhost:8080/login";
                    }
                },
                success: function(data) {
                    $("#user_nickname").html(data.content.nickname);
                    $("#user_email").html(data.content.email);
                }
            });
        });


    </script>
</body>
</html>