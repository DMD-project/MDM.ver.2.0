<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Secondhand Detail</title>

    <style>
        body {
          background-color: #F9F1E7;
        }
        input {
            background-color: #FFFFFF;

            width: 300px;

            color: #000000;
            font-size: 20px;
            border: 1px solid #C2C2C2;
            border-radius: 10px;

            margin-top: 5px;
            padding: 10px;
        }
        input::placeholder {
            color: #C2C2C2;
            text-align: right;
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
        .button_wrapper {
          width: 450px;

          display : flex;
          justify-content: center;

          margin-top: 30px;
        }

        .bid {
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

<%@ include file="includes/header.jsp" %>

<div class="sample">

    <div class="product_img">
        <img src="" alt="">
    </div>
    <div class="product_info_wrapper">
        <div class="product_info">

            <h1><span id="sh_prod_name">sh_prod_name</span></h1>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>

            <div>
                <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>

                <span id="sh_prod_price" style="font-size: 30px;"><b>sh_prod_price</b></span>
                <span style="padding-left: 5px;">원</span>
            </div>

            <div style="margin-top: 30px;">
                <span style="color: #616161; font-size: 15px;">구매 희망가</span><br/>

                <input type="text" id="prod_price" placeholder="희망가 입력">
                <span style="padding-left: 5px;">원</span>
            </div>

        </div>

        <div class="button_wrapper">
            <button class="bid" onclick=""><b>제안하기</b></button>
        </div>

    </div>
</div>

<div class="info_nav_bar">
    <div class="info_nav_bar_border">상품 정보</div>
</div>

<div style="background-color: #FFFFFF; padding: 50px 160px;">
    <span id="sh_prod_content"></span>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
$(document).ready(function() {
    let url = window.location.href;
    let splitUrl = url.split("/");

    let shId = splitUrl[splitUrl.length - 2];

    $.ajax ({
        url: '/secondhand/' + shId,
        success: function(data) {
            console.log(data);

            let sh_prod_imgUrl = data.content.imgUrl;
            let sh_prod_name = data.content.name;
            let sh_prod_price = data.content.price;
            let sh_prod_content = data.content.content;

            $("img").attr('src', sh_prod_imgUrl);
            $("img").attr('alt', sh_prod_imgUrl);
            $("#sh_prod_name").html(sh_prod_name);
            $("#sh_prod_price").html(sh_prod_price);
            $("#sh_prod_content").html(sh_prod_content);

        }
    });
});
</script>

<%@ include file="includes/footer.jsp" %>

</body>
</html>