<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>cart</title>

    <style>
        body {
            background-color: #F9F1E7;
        }
        span {
            padding-left: 5px;
        }
        .content_wrapper {
            display : flex;
            justify-content: center;
        }
        .content_left {
            background-color: #FFFFFF;

            width: 600px;
            padding: 25px;
        }
        .content_right {
            margin-left: 50px;

            display: inline;
        }
        input[type="checkbox"] {
            width: 15px;
            height: 15px;
        }
        .price_info {
            background-color: #FFFFFF;

            width: 360px;
            height: 160px;
            padding: 30px 40px 30px 40px;
        }
        .button_wrapper {
            margin-top: 30px;
        }
        .submit {
            background-color: #FF7500;
            color: white;
            font-size: 20px;
            text-align: center;

            width: 440px;
            height: 60px;

            border: none;
            border-radius: 10px;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <h2 style="color: #666666; text-align: center; margin-bottom: 25px;">장바구니</h2>
    <div class="content_wrapper">

        <div class="content_left">
            <div class="cart_header">
                <div style="float: left; color: #616161;">
                    <input type="checkbox"><span>모두 선택</span>
                </div>
                <div style="float: right; color: #616161;">선택 삭제</div>
            </div>
            <div class="cart_content">







            </div>
        </div>

        <div class="content_right">
            <div class="price_info">
                <div class="product_price" style="padding-bottom: 10px;">
                    <div style="float: left;">총 상품 금액</div>
                    <div style="float: right;"><span style="padding-right: 5px;">product_price</span>원</div>
                </div>
                <br/>
                <div class="shipping_fee" style="padding-bottom: 10px;">
                    <div style="float: left;">배송비</div>
                    <div style="float: right;"><span style="padding-right: 5px;">shipping_fee</span>원</div>
                </div>
                <br/>
                <div class="product_qty" style="padding-bottom: 80px;">
                    <div style="float: left;">총 상품수</div>
                    <div style="float: right;"><span style="padding-right: 5px;">product_qty</span>개</div>
                </div>

                <div class="total_price">
                    <div style="float: left; font-size: 18px;"><b>결제 금액</b></div>
                    <div style="float: right;"><span style="font-size: 25px; padding-right: 5px;"><b>total_price</b></span>원</div>
                </div>
            </div>

            <div class="button_wrapper">
                <button class="submit" onclick=""><b>주문하기</b></button>
            </div>
        </div>
    </div>


    <%@ include file="includes/footer.jsp" %>

</body>
</html>