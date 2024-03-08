<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>order</title>

    <style>
        body {
            background-color: #F9F1E7;
        }
        .content_wrapper {
            display : flex;
            justify-content: center;
        }
        .content_left {
            width: 600px;
            padding: 20px;
        }
        .content_left span {
            color: #868686;
            display:inline-block;
            width: 80px;
            padding-left: 5px;
        }
        .order_address, .order_product, .order_payment {
            margin-top: 50px;
        }
        .order_user_phone, .order_user_email,
        .order_address_phone, .order_address_address {
            margin-top: 7px;
        }
        .content_right {
            margin-left: 50px;

            display: inline;
        }
        input, textarea, select {
            border: 1px solid #C2C2C2;

            height: 40px;
            padding-left: 10px;
            margin-top: 7px;
        }
        #zipcode {
            background-color: #FFFFFF;
            color: #FFAB64;

            font-size: 15px;
            width: 80px;
            height: 35px;

            border: 1px solid #FFAB64;
            border-radius: 7px;
            margin-left: 10px;
        }

        .price_info {
            background-color: #FFFFFF;

            width: 360px;
            height: 170px;
            padding: 30px 40px 30px 40px;
        }
        .button_wrapper {
            margin-top: 30px;
        }
        #submit {
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

    <h2 style="color: #666666; text-align: center; margin-bottom: 25px;">주문/결제</h2>
    <div class="content_wrapper">

        <div class="content_left">

            <div class="order_user_info">
                <span style="font-size: 17px; color: #616161;"><b>주문자</b></span>
                <hr style="border: 1px solid #FFAB64;">

                <div class="order_user_name">
                    <span>이름</span>
                    <input id="order_user_name" type="text" style="width: 300px;">
                </div>
                <div class="order_user_phone">
                    <span>전화번호</span>
                    <select id="order_user_phone_01" style="width: 100px;">
                        <option>010</option>
                    </select>
                    <input id="order_user_phone_02" type="text" style="width: 195px;">
                </div>
                <div class="order_user_email">
                    <span>이메일</span>
                    <input id="order_user_email" type="text" style="width: 300px;">
                </div>
            </div>
            <div class="order_address">
                <span style="font-size: 17px; color: #616161;"><b>배송지</b></span>
                <hr style="border: 1px solid #FFAB64;">

                <div class="order_address_name">
                    <span>받는사람</span>
                    <input id="order_address_name" type="text" style="width: 300px;">
                </div>
                <div class="order_address_phone">
                    <span>전화번호</span>
                    <select id="order_address_phone_01" style="width: 100px;">
                        <option>010</option>
                    </select>
                    <input id="order_address_phone_02"type="text" style="width: 195px;">
                </div>
                <div class="order_address_address">
                    <span>주소</span>
                    <input id="order_address_zipcode"type="text"><button id="zipcode">우편번호</button><br/>
                    <span></span>
                    <input id="order_address_address_01"type="text" style="width: 300px;"><br/>
                    <span></span>
                    <input id="order_address_address_02"type="text" style="width: 300px;">
                </div>
            </div>
            <div class="order_product">
                <span style="font-size: 17px; color: #616161;"><b>주문 상품</b></span>
                <hr style="border: 1px solid #FFAB64;">
            </div>
            <div class="order_payment">
                <span style="font-size: 17px; color: #616161;"><b>결제 수단</b></span>
                <hr style="border: 1px solid #FFAB64;">
            </div>

        </div>

        <div class="content_right">
            <div class="price_info">
                <span style="font-size: 20px;"><b>결제 금액</b></span>
                <div class="product_price" style="padding-top: 15px; padding-bottom: 10px;">
                    <div style="float: left;">총 상품 금액</div>
                    <div style="float: right;"><span style="padding-right: 5px;">product_price</span>원</div>
                </div>
                <br/>
                <div class="shipping_fee" style="padding-bottom: 10px;">
                    <div style="float: left;">배송비</div>
                    <div style="float: right;"><span style="padding-right: 5px;">shipping_fee</span>원</div>
                </div>
                <br/>

                <div class="total_price" style="padding-top: 50px;">
                    <div style="float: left; font-size: 18px;"><b>최종 결제 금액</b></div>
                    <div style="float: right;"><span style="font-size: 25px; padding-right: 5px;"><b>total_price</b></span>원</div>
                </div>
            </div>

            <div class="button_wrapper">
                <button id="submit"><b>결제하기</b></button>
            </div>
        </div>
    </div>

    <%@ include file="includes/footer.jsp" %>

    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        $(document).ready(function() {
            printProductInfo();
        });

        $(document).on('click', '#zipcode', function() {
            new daum.Postcode({
                oncomplete: function(data) {
                    var addr = '';

                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;
                    } else {
       핑                 addr = data.jibunAddress;
                    }

                    $('#order_address_zipcode').val(data.zonecode);
                    $('#order_address_address_01').val(addr);
                    $('#order_address_zipcode').attr('readonly', true);
                    $('#order_address_address_01').attr('readonly', true);
                    $('#order_address_address_02').focus();
                }
            }).open();
        });

        function printProductInfo() {
            let urlParams = new URL(location.href).searchParams;
            let prodId = urlParams.get('prodId');
            let count = urlParams.get('count');

            console.log(prodId);
            console.log(count);
        }
    </script>

</body>
</html>