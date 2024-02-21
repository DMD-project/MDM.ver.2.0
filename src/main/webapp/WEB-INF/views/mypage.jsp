<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>mypage</title>

    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>

    <style>
        a:link {
            text-decoration: none;
            color: #616161;
        }
        a:visited {
            color: #616161;
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
        nav {
            width: 20%;
            padding-top: 30px;
        }
        nav a:hover {
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
                <div style="float: right;"><a href="/FAQ">자주 묻는 질문</a></div>
            </div>

            <div class="mypage_content_main">
                <div style="float: left;">
                    <div class="user_profile_img" style="float:left; padding-left: 10px; padding-right: 30px;"><i class="fa-solid fa-circle-user fa-6x"></i></div>
                    <div style="float:left; padding-top: 20px;">
                        <span id="user_nickname" style="font-size: 40px; margin-top:200px;"><b>user_nickname</b></span>
                        <span id="user_info_update" style="font-size: 13px; margin-left: 10px;"><a href="">회원정보변경</a></span>
                        <br/>
                        <span id="user_email" style="margin-left: 5px;">user_email@gmail.com</span>
                    </div>
                </div>

                <div style="float: right; padding-top: 30px; ">
                    <div class="review" style="float: right; padding-right: 120px; align: right;">
                          <a href=""><i class="fa-solid fa-pencil fa-2x" style="padding-bottom: 10px;"></i><br/>후기 작성</a></div>
                    <div class="favorite" style="float: right; padding-right: 30px;">
                          <a href=""><i class="fa-regular fa-heart fa-2x" style="padding-bottom: 10px;"></i><br/>관심 상품</a></div>
                </div>
            </div>
      </div>

      <div class="under_content">
          <nav>
              <ul>
                  <li style="font-size: 20px; color: #333333;"><b>나의 쇼핑 활동</b></li>
                  <li><a href="">관심 상품</a></li>
                  <li><a href="">구매 내역</a></li>
                  <li><a href="">중고거래 판매 내역</a></li>
                  <li><a href="">중고거래 구매 내역</a></li>
                  <li><a href="">공동구매 구매 내역</a></li>
                  <li><a href="">내가 작성한 후기</a></li>
              </ul>
          </nav>

          <div class="under_content_main">

          </div>
      </div>


      <%@ include file="includes/footer.jsp" %>

</body>
</html>