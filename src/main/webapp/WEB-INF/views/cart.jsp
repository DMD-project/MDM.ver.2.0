<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>cart</title>

    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/3.0.1/js.cookie.min.js"></script>

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
            <div class="cart_content" id="cartItem_list">









            </div>
        </div>

        <div class="content_right">
            <div class="price_info">
                <div class="product_price" style="padding-bottom: 10px;">
                    <div style="float: left;">총 상품 금액</div>
                    <div style="float: right;"><span id="product_price" style="padding-right: 5px;">product_price</span>원</div>
                </div>
                <br/>
                <div class="shipping_fee" style="padding-bottom: 10px;">
                    <div style="float: left;">배송비</div>
                    <div style="float: right;"><span style="padding-right: 5px;">shipping_fee</span>원</div>
                </div>
                <br/>
                <div class="product_qty" style="padding-bottom: 80px;">
                    <div style="float: left;">총 상품수</div>
                    <div style="float: right;"><span id="product_qty" style="padding-right: 5px;">product_qty</span>개</div>
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
                url: '/cart',
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
                        alert("장바구니 불러오기에 실패하였습니다.");
                        location.href="http://localhost:8080";
                    } else {
                        alert("로그인이 필요합니다.");
                        location.href="http://localhost:8080/login";
                    }
                },
                success: function(data) {
                    $("#count").html(data.content.cartItems.length);
                    printCartItems(data);
                }
            });
        });

        function printCartItems(data) {
            let product_price = data.content.price;
            let product_qty = data.content.count;
            let cart_item_info = "";

            cart_item_info += "<ul>";

            for(let i = 0; i < data.content.cartItems.length; i++) {
                cart_item_info += "<li>"
                            + "<img src='" + data.content.cartItems[i].product.imgUrl + " style='width: 50%; height: 100px;'>" + "<br/>"
                            + "<span>" + data.content.cartItems[i].product.name + "</span>" + "<br/>"
                            + "<a href='/cart/view' onclick='decrease_count(" + data.content.cartItems[i].id + ")'> - </a>"
                            + "<span id='item_qty'>" + data.content.cartItems[i].count + "</span>" + "<br/>"
                            + "<a href='/cart/view' onclick='increase_count(" + data.content.cartItems[i].id + ")'> + </a>"
                            + "<span><b>" + data.content.cartItems[i].price + "원</b></span>"
                            + "</li>";
            }


            $("#product_price").html(product_price);
            $("#product_qty").html(product_qty);
            $("#cartItem_list").html(cart_item_info);
        }

        function increase_count(id) {
            $.ajax ({
                type: 'PUT',
                url: '/cartItem/increase/'+id,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                error: function(xhr, status, error){
                    alert("상품 개수 변경에 실패하였습니다.");
                },
                success: function(data) {
                    $("#item_qty").html(data.count);
                }
            });
        }

        function decrease_count(id) {
            $.ajax ({
                type: 'PUT',
                url: '/cartItem/decrease/'+id,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                error: function(xhr, status, error){
                    alert("상품 개수 변경에 실패하였습니다.");
                },
                success: function(data) {
                    $("#item_qty").html(data.count);
                }
            });
        }

        function delete_selected() {
            $.ajax ({

            });
        }

        function delete_all() {
            $.ajax ({
                type: 'DELETE',
                url: '/cart/delete/',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                error: function(xhr, status, error){
                    alert("장바구니 비우기에 실패하였습니다.");
                },
                success: function(data) {

                }
            });
        }

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>