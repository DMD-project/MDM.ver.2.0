<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Product Details</title>

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
    .button_wrapper {
      width: 450px;

      display : flex;
      justify-content: center;

      margin-top: 30px;
    }

    #add_cart {
          background-color: #F9F1E7;
          color: #FF7500;
          font-size: 20px;
          text-align: center;

          width: 240px;
          height: 60px;

          border: 1px solid #FF7500;
          border-radius: 10px;
    }
    #order {
          background-color: #FF7500;
          color: white;
          font-size: 20px;
          text-align: center;

          width: 250px;
          height: 60px;

          border: none;
          border-radius: 10px;
          margin-left: 5%;
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
<style>
      .review_avg {
          background-color: #FFF6EA;
          text-align: center;

          width: 600px;
          height: 80px;

          border-radius: 10px;
          padding: 30px 50px;
          margin-top: 10px;
          margin-bottom: 30px;
      }
      textarea {
          border: 1px solid #B0B0B0;
          margin: 10px 0;
      }
      #review_submit {
          background-color: #FF7500;
          color: #FFFFFF;

          padding: 6px 13px;
          border: none;
          border-radius: 10px;

      }

    .count_wrapper {
        margin-top: 10px;
    }
    .count_wrapper button {
        background-color: #FF7500;
        color: #FFFFFF;
        width: 25px;
        height: 25px;
        border: none;
    }
    .count_wrapper input {
        width: 25px;
        font-size: 15px;
        text-align: center;
        border: none;
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

      <h1><span id="prod_name">prod_name</span></h1>
      <div style="height: 100px;"></div>

      <div>
        <span style="color: #616161; font-size: 15px;">배송 </span>
        <span style="padding-left: 10px;"><b>3000원</b></span>
        <span style="padding-left: 5px;">(50,000원 이상 구매시 무료배송)</span>
      </div>
      <div style="margin-top: 5px;">
        <span style="color: #616161; font-size: 15px;">주문수량</span>
        <div class="count_wrapper">
            <button type="button" id="count_minus">-</button>
            <input type="text" id="count_value" value="1">
            <button type="button" id="count_plus">+</button>
        </div>
      </div>
        <br/>
        <br/>
      <div>
        <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>
        <span id="prod_price" style="font-size: 30px; padding-right: 5px;"><b>prod_price</b></span>
        <span>원</span>
      </div>
    </div>

    <div class="button_wrapper">
      <button id="add_cart"><b>장바구니</b></button>
      <button id="order"><b>결제하기</b></button>
    </div>

  </div>
</div>

<div class="info_nav_bar">
  <div class="info_nav_bar_border">상품 정보</div>
  <div>리뷰</div>
</div>

<div style="background-color: #FFFFFF; padding: 50px 160px;">
    <span id="prod_content"></span>
</div>


<div class="info_nav_bar">
  <div>상품 정보</div>
  <div class="info_nav_bar_border">리뷰</div>
</div>

<div style="background-color: white; padding: 50px 160px;">
    <div class="review_wrapper" style="width: 700px; margin: auto;">
      <div><span style="color: #616161;"><b>후기</span><span id="review_count" style="color: #FF7500; padding-left: 5px;">review_count</span></b></div>
      <div class="review_avg">
        <b><span id="review_avg" style="color: #FF7500; font-size: 30px;">review_avg</span></b>
      </div>

      <div style="margin-bottom: 50px;">
        <span style="color: #616161;"><b>후기 작성</b></span>
        <textarea id="textarea" style="width:650px;height: 100px; padding: 20px;" placeholder="후기를 작성해주세요."></textarea>
        <button id="review_submit" style="float: right; margin-right: 10px;">등록</button>
      </div>

      <div>
        <div>
          <span style="padding-right: 10px; font-size: 15px;">최신순</span>
          <span style="padding-right: 10px; font-size: 15px;">평점 높은순</span>
          <span style="font-size: 15px;">평점 낮은순</span>
        </div>

        <div>



        </div>

      </div>
    </div>

</div>

<%@ include file="includes/footer.jsp" %>


    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {

            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];

            $.ajax ({
                url: '/product/' + prodId,
                success: function(data) {
                    console.log(data);
                    console.log(data.content.cateCode);

                    let prod_imgUrl = data.content.imgUrl;
                    let prod_name = data.content.name;
                    let prod_price = data.content.price;
                    let prod_content = data.content.content;

                    $("img").attr('src', prod_imgUrl);
                    $("img").attr('alt', prod_imgUrl);
                    $("#prod_name").html(prod_name);
                    $("#prod_price").html(prod_price);
                    $("#prod_content").html(prod_content);

                    let review_count = data.content.reviewCnt;
                    let review_avg = data.content.reviewStarAvg;

                    $("#review_count").html(review_count);
                    $("#review_avg").html(review_avg);

                    let arr_review = data.content.reviewList;
                    console.log(data.content.reviewList);

                    for (let i = 0; i < arr_review.length; i++) {
                        console.log(arr_review[i].star);
                        console.log(arr_review[i].content);
                    }

                }
            });
        });

        $(document).on('click', '#count_minus', function() {
            let count_value = $('#count_value').val();
            console.log(count_value);

            if (count_value == 1)
                alert('최소 수량 입니다.');
            else
                count_value--;

            console.log(count_value);
            $("#count_value").attr('value', count_value);
        });

        $(document).on('click', '#count_plus', function() {
            let count_value = $('#count_value').val();
            console.log(count_value);

            count_value++;
            console.log(count_value);

            $("#count_value").attr('value', count_value);
        });

        $(document).on('click', '#add_cart', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];
            let count = $('#count_value').val();
            $.ajax ({
                type: 'POST',
                url: '/cartItem/add/' + prodId + '/' + count,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);

                    if (!token) {
                        alert("로그인이 필요합니다.");
                        return;
                    }
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(result) {
                    console.log(result);
                    alert('장바구니에 상품이 추가되었습니다.');
                }

            });
        });

        $(document).on('click', '#review_submit', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];

            let content = $('#textarea').val();
            console.log(content);

            let today = new Date();

            $.ajax({
                type: 'POST',
                url: '/review/' + prodId + '/add',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);

                    if (!token) {
                        alert("로그인이 필요합니다.");
                        return;
                    }
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                data: JSON.stringify(
                    {
                        "prodId" : prodId,
                        "star" : 3,
                        "date" : today,
                        "content" : content
                    }
                ),
                contentType: 'application/json',
                success: function(result) {
                    console.log(result);
                }
            });


        });


    </script>

</body>
</html>