<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Grouppurchase Details</title>

    <style>
        body {
          background-color: #F9F1E7;
        }
        .sample {
          display : flex;
          justify-content: center;

          margin-top: 30px;
          margin-bottom: 50px;
        }
        .product_img {
          background-color: white;

          width: 500px;
          height: 500px;

          border: none;
        }

        .product_info_wrapper {
            padding: 0 30px;
        }
        .product_info {
          background-color: white;

          width: 400px;
          height: 330px;

          padding: 35px 0 40px 50px;
          margin: auto;
        }
        .limit_info_wrapper {
            display : flex;

            margin-bottom:20px;
        }
        .limit_info_date {
            text-align: right;
            margin-left: 10px;
        }
        .limit_info_count {
            text-align: right;
            margin-left: 25px;
        }
        .button_wrapper {
          width: 450px;

          display : flex;
          justify-content: center;

          margin-top: 30px;
        }
        .submit {
              background-color: #FF7500;
              color: white;
              font-size: 20px;
              text-align: center;

              width: 450px;
              height: 60px;

              border: none;
              border-radius: 10px;
         }

        .info_nav_bar {
             background-color: #F5F5F5;

             height: 60px;
             line-height: 60px;

             padding-left: 150px;
        }
        .info_nav_bar div {
            width: 150px;

            text-align: center;
            float: left;
        }
        .info_nav_bar_border {
            color: #FF7500;
            border-bottom: 3px solid #FF7500;
        }
    </style>
</head>
<body>

<%@ include file="includes/footer.jsp" %>

<div class="sample">

    <div class="product_img">
        <img src="" alt="sample_img">
    </div>
    <div class="product_info_wrapper">
        <div class="product_info">

            <h1><span id="gp_prod_name">gp_prod_name</span></h1>

            <div class="limit_info_wrapper">
                <div class="limit_info_date">
                    <span>남은 기간</span>
                    <br/>
                    <span style="font-size: 35px;">N</span>
                    <span style="font-size: 13px;">일</span>
                </div>
                <div class="limit_info_count">
                    <span>참여인원</span>
                    <br/>
                    <span style="font-size: 35px;">N</span>
                    <span style="font-size: 13px;">명</span>
                </div>
            </div>

            <div>
                <span style="color: #616161; font-size: 15px;">목표 수량</span>
                <span style="padding-left: 5px;">goal_qty</span>
                <span>개</span>
            </div>
            <div>
                <span style="color: #616161; font-size: 15px;">현재 수량</span>
                <span style="padding-left: 5px;">qty</span>
                <span>개</span>
            </div>
            <div>
                <span style="color: #616161; font-size: 15px;">공동구매 기간</span>
                <span style="padding-left: 10px;">start_date</span>
                <span style="padding-left: 10px;">~</span>
                <span style="padding-left: 10px;">end_date</span>
            </div>
            <div>
                <span style="color: #616161; font-size: 15px;">주문수량</span>
                <input type="number" min="1" max="10" step="1" value="1" style="margin: 10px;">
            </div>
            <br/>
            <div>
                <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>
                <span id="prod_price" style="font-size: 30px;"><b>gp_prod_price</b></span>
                <span style="padding-left: 5px;">원</span>
            </div>
        </div>

        <div class="button_wrapper">
            <button class="submit" onclick=""><b>참여하기</b></button>
        </div>

    </div>
</div>

<div class="info_nav_bar">
    <div class="info_nav_bar_border">상품 정보</div>
</div>

<div style="background-color: #FFFFFF; padding: 50px 160px;">
    <span id="gp_prod_content"></span>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
$(document).ready(function() {
    let url = window.location.href;
    let splitUrl = url.split("/");

    let gpId = splitUrl[splitUrl.length - 2];

    $.ajax ({
        url: '/gp/' + gpId,
        success: function(data) {
            console.log(data);
        }
    });
});

</script>

<%@ include file="includes/footer.jsp" %>

</body>
</html>