<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>mypage</title>
    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>
    <style>
        .link {
            cursor: pointer;
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
        .nav_mypage {
            width: 20%;
            padding-top: 30px;
        }
        .nav_mypage span {
            color: #616161;
        }
        .nav_mypge span:hover {
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
        #submit_user_nickname {
            background-color: #FF7500;
            color: #FFFFFF;
            font-size: 14px;
            margin-left: 10px;
            display: inline-block;
            height: 25px;
            border-radius: 10px;
            vertical-align: middle;
            text-align: center;
            line-height: 25px;
        }
        #new_user_nickname {
            width: 260px;
            height: 38px;
            font-size: 30px;
            margin-bottom: 4px;
            color: #333333;
            outline: none;
        }
        .content_box {
            display: flex;
            float: left;
            width: 80%;
            padding: 30px;
            margin-top: 20px;
        }
        #update_order, #update_order_submit {
            background-color: #FF7500;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            border-radius: 10px;
            cursor: pointer;
        }
        #delete_order {
            background-color: #616161;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            margin-left: 10px;
            border-radius: 10px;
            cursor: pointer;
        }
        .new_order_address input {
            border: 1px solid #C2C2C2;
            height: 30px;
            padding-left: 10px;
            margin-top: 7px;
        }
        .new_order_address input:read-only {
            background-color: #EEEEEE;
            color: #C2C2C2;
        }
        #zipcode {
            background-color: #FFEDDE;
            color: #FF7500;
            font-size: 15px;
            border: 1px solid #FF7500;
            border-radius: 7px;
            padding: 5px 10px;
            margin-left: 10px;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="mypage_content">
        <div class="mypage_content_header">
            <div style="float: left; color: #FF7500; font-size: 20px;"><b>마이페이지</b></div>
            <div style="float: right;"><span class="link">자주 묻는 질문</span></div>
        </div>
        <div class="mypage_content_main">
            <div style="float: left;">
                <div class="user_profile_img" style="float:left; padding-left: 10px; padding-right: 30px;"><i class="fa-solid fa-circle-user fa-6x"></i></div>
                <div style="float:left; padding-top: 8px;">
                    <span id="user_nickname" style="font-size: 40px; font-weight: bold;">user_nickname</span>
                    <span id="update_user_info" style="font-size: 13px; margin-left: 10px;">회원정보변경</span>
                    <br/>
                    <span class="nickname_empty" style="font-size: 14px; margin-left: 5px; color: #FF7171; display: none;">변경할 닉네임을 입력해 주세요.</span>
                    <span class="nickname_used" style="font-size: 14px; margin-left: 5px; color: #FF7171; display: none;">이미 존재하는 닉네임입니다.</span>
                    <span class="nickname_possible" style="font-size: 14px; margin-left: 5px; color: #12DB1A; display: none;">사용 가능한 닉네임입니다. </span>
                    <br/>
                    <span id="user_email" style="margin-left: 5px;">user_email@gmail.com</span>
                </div>
            </div>
        </div>
    </div>

    <div class="under_content">
        <nav class="nav_mypage">
            <ul>
                <li style="font-size: 20px; color: #333333;"><b>나의 쇼핑 활동</b></li>
                <li><span class="link" onclick="location.href='/mypage/view'" style="color: #FF7500;">구매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/gp/view'">공동구매 참여 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/sh/view'">중고거래 판매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/shBid/view'">중고거래 요청 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/review/view'">내가 작성한 후기</span></li>
            </ul>
        </nav>
        <div class="under_content_main" style="width: 100%; padding-top: 45px; padding-left: 30px;">
            <span style="padding: 10px; font-size: 25px; font-weight: bold; color: #FF7500;">구매 내역</span>
            <div id="list_wrapper"></div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
            printUserInfo();
            printMyOrder();
        });

        function printUserInfo() {
            $.ajax ({
                url: '/mypage',
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
                        alert("존재하지 않는 사용자입니다.");
                        location.href="http://localhost:8080";
                    }
                },
                success: function(data) {
                    $("#user_nickname").html(data.content.nickname);
                    $("#user_email").html(data.content.email);
                }
            });
        }

        function printMyOrder() {
            $.ajax ({
                url: '/mypage/order',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    let order_arr = data.content;
                    let order_box = "";
                    $.each(order_arr, function(idx, value) {
                        if (value.groupPurchase == null) {

                            order_box += "<div class='content_box'>";
                                    if (value.product == null) {
                                        order_box += "<img src='../../images/product/" + value.cartItems[0].product.imgUrl + "' style='width: 110px; height: 130px; margin-right: 30px;'>";
                                    } else {
                                        order_box += "<img src='../../images/product/" + value.product.imgUrl + "' style='width: 110px; height: 130px; margin-right: 30px;'>";
                                    }
                                        order_box += "<div style='display: flex; flex-direction: column; width: 680px;'>"
                                                   + "<div id=" + value.id + ">";
                                    if (value.product == null && value.cartItems.length == 1 ) {
                                        order_box += "<span style='font-size: 20px; font-weight: bold;'>" + value.cartItems[0].product.name + "</span>";
                                    } else if (value.product == null && 1 < value.cartItems.length) {
                                        order_box += "<span style='font-size: 20px; font-weight: bold;'>" + value.cartItems[0].product.name + "&nbsp;&nbsp;외&nbsp;" + (value.cartItems.length - 1) + "</span>";
                                    } else {
                                        order_box += "<span style='font-size: 20px; font-weight: bold;'>" + value.product.name + "</span>";
                                    }
                                        order_box += "<span id='delete_order'>주문 취소</span>"
                                                    + "<span id='update_order'>배송지 수정</span>"
                                                + "</div>"
                                                + "<div style='color: #B0B0B0; font-size: 13px;'>배송지</div>"
                                                + "<div style='font-size: 14px;'>(" + value.zipcode + ")</div>"
                                                + "<div style='font-size: 14px;'>" + value.streetAddr + ", " + value.detailAddr + "</div>"
                                                + "<div style='font-size: 15px; margin-top: 10px;'>총 " + value.qty + "개</div>"
                                                + "<div style='font-size: 25px; font-weight: bold;'>" + value.price + "원</div>"
                                            + "</div>"
                                        + "</div>";
                        }
                    });
                    $("#list_wrapper").html(order_box);
                }
            });
        }

        $(document).on('click', '#update_order', function() {
            let update_order_box = "";
            update_order_box += "<div class='new_order_address'>"
                                    + "<input id='new_zipcode' type='text' style='width: 80px;' readonly>" + "<button id='zipcode'>우편번호</button>" + "<br/>"
                                    + "<input id='new_address_01' type='text' style='width: 220px;' readonly>" + "<br/>"
                                    + "<input id='new_address_02' type='text' style='width: 220px;'>"
                              + "</div>";

            $(this).parent().nextAll().remove();
            $(this).parent().after(update_order_box);

            let btn = "";
            btn += "<span id='update_order_submit'>등록</span>"
                    + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px;'>취소</span>";

            $(this).after(btn);
            $(this).prev().remove();
            $(this).remove();
        });

        $(document).on('click', '#update_order_submit', function() {
            let orderId = $(this).parent().attr("id");
            let new_zipcode = $("#new_zipcode").val();
            let new_addr_01 = $("#new_address_01").val();
            let new_addr_02 = $("#new_address_02").val();

            if (new_zipcode == "" || new_addr_01 == "" || new_addr_02 == "")
                alert('배송지 정보를 입력해 주세요');
            else {
                $.ajax ({
                    type: 'PUT',
                    url: '/order/update/' + orderId,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    data: JSON.stringify (
                        {
                            "zipcode" : new_zipcode,
                            "streetAddr" : new_addr_01,
                            "detailAddr" : new_addr_02
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        alert('배송지 정보가 수정되었습니다.');
                        printMyOrder();
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

                    $('#new_zipcode').val(data.zonecode);
                    $('#new_address_01').val(addr);
                    $('#new_address_02').focus();
                }
            }).open();
        });

        $(document).on('click', '#delete_order', function() {
            let conf = confirm('주문을 취소하시겠습니까?');

            if(conf == true) {
                let orderId = $(this).parent().attr('id');

                $.ajax ({
                    type: 'DELETE',
                    url: '/order/cancel/' + orderId,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    success: function(data) {
                        alert('주문을 취소하였습니다.');
                        printMyOrder();
                    }
                })
            } else if(conf == false) {
                return;
            }
        });

        $(document).on('click', '#update_user_info', function() {
            let new_content = "";
            new_content += "<input id='new_user_nickname' type='text' onInput='javascript:check_nickname()' style='width: 260px; height: 38px; font-size: 30px; margin-bottom: 4px; color: #333333;'>"
                            + "<span id='submit_user_nickname' class='link' style='width: 40px;'>등록</span>"
                            + "<span id='cancel' class='link' style='font-size: 13px; margin-left: 10px;'>취소</span>";

            $(this).prev().remove();
            $(this).before(new_content);
            $(this).remove();
        });

        function check_nickname() {
            let userNickname = $('#new_user_nickname').val();

            if (userNickname == "") {
                $('#new_user_nickname').css('border', '1.5px solid #FF7171');
                $('.nickname_empty').css('display', 'inline-block');
                $('.nickname_used').css('display', 'none');
                $('.nickname_possible').css('display', 'none');
            } else {
                $.ajax ({
                    url: '/mypage/check/' + userNickname,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    success: function(data) {
                        if (data) {
                            $('#new_user_nickname').css('border', '1.5px solid #FF7171');
                            $('.nickname_empty').css('display', 'none');
                            $('.nickname_used').css('display', 'inline-block');
                            $('.nickname_possible').css('display', 'none');
                        } else {
                            $('#new_user_nickname').css('border', '1.5px solid #12DB1A');
                            $('.nickname_empty').css('display', 'none');
                            $('.nickname_used').css('display', 'none');
                            $('.nickname_possible').css('display', 'inline-block');
                        }
                    }
                })
            }
        }

        $(document).on('click', '#submit_user_nickname', function() {
            if ($('.nickname_possible').css('display') == "inline-block") {
                let userNickname = $('#new_user_nickname').val();

                $.ajax ({
                    type: 'POST',
                    url: '/mypage/nickname/' + userNickname,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    success: function(data) {
                        alert('닉네임을 수정하였습니다.');
                        location.href="http://localhost:8080/mypage/view";
                    }
                })
            }
        });

        $(document).on('click', '#cancel', function() {
            printUserInfo();
            printMyOrder()
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>