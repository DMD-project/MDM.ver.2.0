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
            display: inline-block;
            width: 80px;
            padding-left: 5px;
        }
        .order_user_info, .order_address, .order_product, .order_payment {
            margin-bottom: 40px;
        }
        .order_user_phone, .order_user_email,
        .order_address_phone, .order_address_address {
            margin-top: 7px;
        }
        .order_address_address input:read-only {
            background-color: #EEEEEE;
            color: #C2C2C2;
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
        .order_item {
            background-color: #FFFFFF;
            display: flex;
            padding: 25px 30px;
            margin-top: 20px;
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
                    <input id="order_address_zipcode"type="text" readonly><button id="zipcode">우편번호</button><br/>
                    <span></span>
                    <input id="order_address_address_01"type="text" style="width: 300px;" readonly><br/>
                    <span></span>
                    <input id="order_address_address_02"type="text" style="width: 300px;">
                </div>
            </div>
            <div class="order_product">
                <span style="font-size: 17px; color: #616161;"><b>주문 상품</b></span>
                <hr style="border: 1px solid #FFAB64;">
                <div id="order_item_wrapper">

                </div>
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
                    <div style="float: right;"><span id="product_price" style="padding-right: 5px;">product_price</span>원</div>
                </div>
                <br/>
                <div class="shipping_fee" style="padding-bottom: 10px;">
                    <div style="float: left;">배송비</div>
                    <div style="float: right;"><span id="shipping_fee" style="padding-right: 5px;">shipping_fee</span>원</div>
                </div>
                <br/>

                <div class="total_price" style="padding-top: 50px;">
                    <div style="float: left; font-size: 18px;"><b>최종 결제 금액</b></div>
                    <div style="float: right;"><span id="total_price" style="font-size: 25px; font-weight: bold; padding-right: 5px;">total_price</span>원</div>
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

        $(document).on('click', '#submit', function() {
            let urlParams = new URL(location.href).searchParams;
            let from = urlParams.get('from');

            let user_name = $('#order_user_name').val();
            let user_phone = $('#order_user_phone_01 option:selected').val() + $('#order_user_phone_02').val();

            let order_name = $('#order_address_name').val();
            let order_phone = $('#order_address_phone_01 option:selected').val() + $('#order_address_phone_02').val();
            let zipcode = $('#order_address_zipcode').val();
            let addr_01 = $('#order_address_address_01').val();
            let addr_02 = $('#order_address_address_02').val();

            if (user_name == "" || user_phone == "010")
                alert('주문자 정보를 입력해 주세요.');
            else if (order_name == "" || order_phone == "010" || zipcode == "" || addr_01 == "" || addr_02 == "")
                alert('배송지 정보를 입력해 주세요');

            if (from == "product") {
                let productId = urlParams.get('prodId');
                let purchasedQty = urlParams.get('count');

                $.ajax ({
                    type: 'POST',
                    url: '/order/product/' + productId + '/' + purchasedQty,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        if (!token) {
                            alert("로그인이 필요합니다.");
                            location.href="/login";
                        }
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    data: JSON.stringify (
                        {
                            "name": order_name,
                            "contact": order_phone,
                            "zipcode": zipcode,
                            "streetAddr": addr_01,
                            "detailAddr": addr_02
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        console.log(data);
                        alert('주문이 성공적으로 완료 되었습니다.');
                        location.href="/product/list/view";
                    }
                })
            } else if (from == "gp") {
                let gpId = urlParams.get('gpId');
                let purchasedQty = urlParams.get('purchasedQty');

                $.ajax ({
                    type: 'POST',
                    url: '/gp/order/' + gpId + "/" + purchasedQty,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        if (!token) {
                            alert("로그인이 필요합니다.");
                            location.href="/login";
                        }
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    data: JSON.stringify (
                        {
                            "name": order_name,
                            "contact": order_phone,
                            "zipcode": zipcode,
                            "streetAddr": addr_01,
                            "detailAddr": addr_02
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        console.log(data);
                        alert('공동구매 참여가 성공적으로 완료 되었습니다.');
                        location.href="/gp/list/view";
                    }
                })

            } else if (from == "cart") {
                let param_list = urlParams.get('item_list');
                let item_list = param_list.split(",");

                $.ajax ({
                    type: 'POST',
                    url: '/order/cart/add',
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        if (!token) {
                            alert("로그인이 필요합니다.");
                            location.href="/login";
                        }
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    data: JSON.stringify (
                        {
                            "itemIds" : item_list,
                            "orderDto" : {
                                "name": order_name,
                                "contact": order_phone,
                                "zipcode": zipcode,
                                "streetAddr": addr_01,
                                "detailAddr": addr_02
                            }
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        console.log(data);
                        alert('주문이 성공적으로 완료 되었습니다.');
                        location.href="/product/list/view";
                    }
                })
            }
        });

        $(document).on('click', '#zipcode', function() {
            new daum.Postcode({
                oncomplete: function(data) {
                    var addr = '';

                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;
                    } else {
                        addr = data.jibunAddress;
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
            let from = urlParams.get('from');

            if (from == "product") {
                let prodId = urlParams.get('prodId');
                let count = urlParams.get('count');

                $.ajax ({
                    url: '/product/' + prodId,
                    success: function(data) {
                        let product_price = data.content.price * count;
                        let shipping_fee = 3000;

                        let item_box = "";
                        item_box += "<div class='order_item'>"
                                        + "<img src='../../images/product/" + data.content.imgUrl + "' style='width: 90px; height: 100px; margin-right: 30px;'>"
                                        + "<div>"
                                            + "<div style='font-size: 20px; font-weight: bold; margin-top: 5px;'>" + data.content.name + "</div>"
                                            + "<div style='margin-top: 20px;'>" + count + "개</div>"
                                            + "<div style='font-size: 25px; font-weight: bold;'>" + product_price + "원</div>"
                                        + "</div>"
                                    + "</div>";
                        $("#order_item_wrapper").html(item_box);

                        if (50000 <= product_price)
                            shipping_fee = 0;

                        let total_price = product_price + shipping_fee;

                        $("#product_price").html(product_price);
                        $("#shipping_fee").html(shipping_fee);
                        $("#total_price").html(total_price);
                    }
                })
            } else if (from == "gp") {
                let gpId = urlParams.get('gpId');
                let purchasedQty = urlParams.get('purchasedQty');

                $.ajax ({
                    url: '/gp/' + gpId,
                    success: function(data) {
                        console.log(data);

                        let product_price = data.content.groupPurchase.price * purchasedQty;
                        let shipping_fee = 3000;

                        let item_box = "";
                        item_box += "<div class='order_item'>"
                                        + "<img src='../../images/grouppurchase/" + data.content.groupPurchase.imgUrl + "' style='width: 90px; height: 100px; margin-right: 30px;'>"
                                        + "<div>"
                                            + "<div style='font-size: 20px; font-weight: bold; margin-top: 5px;'>" + data.content.groupPurchase.name + "</div>"
                                            + "<div style='margin-top: 20px;'>" + purchasedQty + "개</div>"
                                            + "<div style='font-size: 25px; font-weight: bold;'>" + product_price + "원</div>"
                                        + "</div>"
                                    + "</div>";
                        $("#order_item_wrapper").html(item_box);

                        if (50000 <= product_price)
                            shipping_fee = 0;

                        let total_price = product_price + shipping_fee;

                        $("#product_price").html(product_price);
                        $("#shipping_fee").html(shipping_fee);
                        $("#total_price").html(total_price);
                    }
                })

            } else if (from == "cart") {
                let param_list = urlParams.get('item_list');
                let item_list = param_list.split(",");

                $.ajax ({
                    url: '/cart',
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        if (!token) {
                            alert("로그인이 필요합니다.");
                            location.href="/login";
                        }
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    success: function(data) {
                        let cart_item = data.content.cartItems;
                        let order_item = cart_item.filter(x1 => item_list.some(x2 => x1.id == x2));

                        let product_price = 0;
                        let shipping_fee = 3000;

                        let item_box = "";
                        $.each(order_item, function(idx, value) {
                            product_price += value.price;

                            item_box += "<div class='order_item'>"
                                            + "<img src='../../images/product/" + value.product.imgUrl + "' style='width: 90px; height: 100px; margin-right: 30px;'>"
                                            + "<div>"
                                                + "<div style='font-size: 20px; font-weight: bold; margin-top: 5px;'>" + value.product.name + "</div>"
                                                + "<div style='margin-top: 20px;'>" + value.count + "개</div>"
                                                + "<div style='font-size: 25px; font-weight: bold;'>" + value.price + "원</div>"
                                            + "</div>"
                                        + "</div>";
                        });
                        $("#order_item_wrapper").html(item_box);

                        if (50000 <= product_price)
                            shipping_fee = 0;

                        let total_price = product_price + shipping_fee;

                        $("#product_price").html(product_price);
                        $("#shipping_fee").html(shipping_fee);
                        $("#total_price").html(total_price);
                    }
                })
            }
        }

    </script>

</body>
</html>