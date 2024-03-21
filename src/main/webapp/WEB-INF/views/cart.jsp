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
            margin-bottom: 100px;
        }
        .content_left {
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
        .cartItem_wrapper {
            background-color: #FFFFFF;
            display: flex;
            float: left;
            width: 570px;
            height: 130px;
            padding: 15px;
            margin: 10px 0;
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
        a {
            color: black;
            text-decoration: none;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <h2 style="color: #666666; text-align: center; margin-bottom: 25px;">장바구니</h2>
    <div class="content_wrapper">
        <div class="content_left">
            <div class="cart_header" style="padding: 0 15px;">
                <div style="float: left; color: #616161;">
                    <input type="checkbox" class="check_all" checked><span>모두 선택</span>
                </div>
                <div class="delete_item" style="float: right; color: #616161;">선택 삭제</div>
            </div>
            <div class="cart_content" id="cartItem_list"></div>
        </div>

        <div class="content_right">
            <div class="price_info">
                <div class="product_total" style="padding-bottom: 10px;">
                    <div style="float: left;">총 상품 금액</div>
                    <div style="float: right;"><span id="product_total" style="padding-right: 5px;">0</span>원</div>
                </div>
                <br/>
                <div class="shipping_fee" style="padding-bottom: 10px;">
                    <div style="float: left;">배송비</div>
                    <div style="float: right;"><span id="shipping_fee" style="padding-right: 5px;">0</span>원</div>
                </div>
                <br/>
                <div class="product_qty" style="padding-bottom: 80px;">
                    <div style="float: left;">총 상품수</div>
                    <div style="float: right;"><span id="product_qty" style="padding-right: 5px;">0</span>개</div>
                </div>
                <div class="total_price">
                    <div style="float: left; font-size: 18px;"><b>결제 금액</b></div>
                    <div style="float: right;"><span id="total_price" style="font-size: 25px; font-weight: bold; padding-right: 5px;">0</span>원</div>
                </div>
            </div>

            <div class="button_wrapper">
                <button id="submit"><b>주문하기</b></button>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>

        function getCookie(name) {
            const cookies = document.cookie.split(';');
            for (let i = 0; i < cookies.length; i++) {
                const cookie = cookies[i].trim();
                if (cookie.startsWith(name + '='))
                    return cookie.substring(name.length + 1);
            }
            return null;
        }

        $(document).ready(function() {
            printCartItems();
        });

        function printCartItems() {
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
                error: function(xhr, status, error){
                    if(status == 404) {
                        alert("장바구니 불러오기에 실패하였습니다.");
                        location.href="/";
                    }
                },
                success: function(data) {
                    if (data.message = "OK") {
                        let product_total = data.content.price;
                        let product_qty = data.content.count;
                        let shipping_fee = 0;

                        if (0 < product_total && product_total < 50000)
                            shipping_fee = 3000;

                        let total_price = product_total + shipping_fee;

                        $("#product_total").html(product_total);
                        $("#shipping_fee").html(shipping_fee);
                        $("#product_qty").html(product_qty);
                        $("#total_price").html(total_price);

                        let cart_item_info = "";
                        for(let i = 0; i < data.content.cartItems.length; i++) {
                            cart_item_info += "<div class='cartItem_wrapper' value='" + data.content.cartItems[i].id +"'>"
                                                + "<input type='checkbox' class='cartItem_checkbox' style='margin-right: 15px;' checked='checked' value='" + data.content.cartItems[i].id + "'>"
                                                + "<a href='/product/" + data.content.cartItems[i].product.id + "/view'>"
                                                + "<img src='../../images/product/" + data.content.cartItems[i].product.imgUrl + "' style='width: 110px; height: 130px; margin-right: 30px;'>"
                                                + "<div style='display: flex; flex-direction: column;'>"
                                                    + "<div style='font-size: 22px; margin-top: 20px;'><b>" + data.content.cartItems[i].product.name + "</b></div>"
                                                    + "</a>"
                                                    + "<div class='setPriceInfo' style='width: 380px; margin-top: 35px;'>"
                                                        + "<div class='count_wrapper' style='float:left;'>"
                                                            + "<button type='button' id='count_minus' value='" + data.content.cartItems[i].id + "'> - </button>"
                                                            + "<input type='text' id='count_value' value='" + data.content.cartItems[i].count + "' readonly>"
                                                            + "<button type='button' id='count_plus' value='" + data.content.cartItems[i].id + "'> + </button>"
                                                        + "</div>"
                                                        + "<div style='float: right; margin-left: 0; font-size: 25px;'><b>원</b></div>"
                                                        + "<div id='product_price' style='float: right; font-size: 25px;'><b>" + data.content.cartItems[i].price + "</b></div>"
                                                    +"</div>"
                                                + "</div>"
                                            + "</div>";
                        }
                        $("#cartItem_list").html(cart_item_info);
                    }
                }
            })
        }

        $(document).on('click', '#submit', function() {
            let item_list = [];
            $(".cartItem_wrapper").each(function(idx, element) {
                if ($(element).find('input').is(':checked')) {
                    item_id = $(element).find('.cartItem_checkbox').val();
                    item_list.push(item_id);
                }
            });

            if (item_list.length == 0)
                alert('구매할 상품을 선택해 주세요.');
            else
                location.href = "/order/view?from=cart&item_list=" + item_list;

        });

        $(document).on('click', '.check_all', function() {
            if ($('.check_all').is(':checked'))
                $('.cartItem_checkbox').prop('checked', true);
            else
                $('.cartItem_checkbox').prop('checked', false);

            setPriceInfo();
        });

        $(document).on('click', '.cartItem_checkbox', function() {
            let selected_count = $(".cartItem_checkbox:checked").length;
            let checkbox_count = $(".cartItem_checkbox").length;

            if (selected_count == checkbox_count)
                $('.check_all').prop('checked', true);
            else
                $('.check_all').prop('checked', false);

            setPriceInfo();
        });

        function setPriceInfo() {
            let product_total = 0;
            let product_qty = 0;
            let shipping_fee = 3000;

            $(".cartItem_wrapper").each(function(idx, element) {
                if ($(element).find('input').is(':checked')) {
                    let price = parseInt($(element).find('#product_price').text());
                    let count_value = parseInt($(element).find('#count_value').val());

                    product_total += price;
                    product_qty += count_value;
                }
            });

            if (50000 <= product_total)
                shipping_fee = 0;

            let total_price = product_total + shipping_fee;

            $("#product_total").html(product_total);
            $("#shipping_fee").html(shipping_fee);
            $("#product_qty").html(product_qty);
            $("#total_price").html(total_price);
        }

        $(document).on('click', '.delete_item', function() {
            let itemsId = [];
            $(".cartItem_checkbox:checked").each(function(){
                itemsId.push($(this).val());
            });

            if (itemsId.length == 0)
                alert('삭제할 상품을 선택해 주세요.');
            else {
                $.ajax ({
                    type: 'DELETE',
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    url: '/cartItem/delete/selected',
                    data: {
                        "itemsId" : itemsId,
                    },
                    success: function() {
                        printCartItems();
                    }
                })
            }
        });

        $(document).on('click', '#count_minus', function() {
            let id = $(this).val();
            let count_value = $(this).next().val();

            if (count_value == 1)
                alert('최소 수량 입니다.');
            else {
                count_value--;
                $(this).next().attr('value', count_value);

                $.ajax ({
                    type: 'PUT',
                    url: '/cartItem/decrease/' + id,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    error: function(xhr, status, error){
                        alert("상품 개수 변경에 실패하였습니다.");
                    },
                    success: function(data) {
                        printCartItems();
                    }
                })
            }
        });

        $(document).on('click', '#count_plus', function() {
            let id = $(this).val();
            let count_value = $(this).prev().val();

            count_value++;
            $(this).prev().attr('value', count_value);

            $.ajax ({
                type: 'PUT',
                url: '/cartItem/increase/' + id,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                 error: function(xhr, status, error){
                    alert("상품 개수 변경에 실패하였습니다.");
                 },
                 success: function(data) {
                    printCartItems();
                 }
            })
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>