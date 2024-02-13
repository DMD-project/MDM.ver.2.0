<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Product Detail</title>

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
    .product_info {
      background-color: white;

      width: 400px;
      height: 300px;

      border: none;
      padding: 50px;
      margin-left: 5%;
    }
    .button_wrapper {
      display : flex;
      justify-content: center;

      padding:  20px;
      margin-left: 10px;

    }

    .cart {
          background-color: #F9F1E7;
          color: #FF7500;
          font-size: 15px;
          text-align: center;

          width: 240px;
          height: 60px;

          border: 1px solid #FF7500;
          border-radius: 10px;
    }
    .order {
          background-color: #FF7500;
          color: white;
          font-size: 15px;
          text-align: center;

          width: 250px;
          height: 60px;

          border: none;
          border-radius: 10px;
          margin-left: 5%;
     }

    .info_nav_bar {
         background-color: #FFFFFF;

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
        border-bottom: 3px solid #FF7500;
    }
  </style>
</head>
<body>

<div class="sample">

  <div class="product_img">
    <img src="" alt="sample_img">
  </div>
  <div class="product_info_wrapper">
    <div class="product_info">

      <h1><span>prod_name</span></h1>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      배송 <b>3000원</b> (50,000원 이상 구매시 무료배송)
      <br/>
      주문수량
      <input type="number" min="1" max="10" step="1" value="1" style="margin: 10px;"> 개
      <br/>
      <br/>
      <div><span style="font-size: 30px;"><b>prod_price</b></span> 원</div>
    </div>

    <div class="button_wrapper">
      <button class="cart" onclick="">장바구니</button>
      <button class="order" onclick="">결제하기</button>
    </div>

  </div>
</div>

<div class="info_nav_bar">
  <div class="info_nav_bar_border">상품 정보</div>
  <div>리뷰</div>
</div>

<div style="height:500px;"></div>


<div class="info_nav_bar">
  <div>상품 정보</div>
  <div class="info_nav_bar_border">리뷰</div>
</div>



</body>
</html>