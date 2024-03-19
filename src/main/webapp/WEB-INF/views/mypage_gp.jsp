<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>mypage</title>

    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>
    <style>
        a {
            color: black;
            text-decoration: none;
        }
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
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="mypage_content">

        <div class="mypage_content_header">
            <div style="float: left; color: #FF7500; font-size: 20px;"><b>마이페이지</b></div>
            <div style="float: right;"><span class="link" onclick="location.href='/mypage/faq/view'">자주 묻는 질문</span></div>
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

            <div style="float: right; padding-top: 30px; ">
                <div class="review" style="float: right; padding-right: 120px; align: right;">
                    <span class="link"><i class="fa-solid fa-pencil fa-2x" style="padding-bottom: 10px;"></i><br/>후기 작성</span></div>
                <div class="favorite" style="float: right; padding-right: 30px;">
                    <span class="link"><i class="fa-regular fa-heart fa-2x" style="padding-bottom: 10px;"></i><br/>관심 상품</span></div>
            </div>
        </div>
    </div>

    <div class="under_content">
        <nav class="nav_mypage">
            <ul>
                <li style="font-size: 20px; color: #333333;"><b>나의 쇼핑 활동</b></li>
                <li><span class="link">관심 상품</span></li>
                <li><span class="link" onclick="location.href='/mypage/view'">구매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/gp/view'" style="color: #FF7500;">공동구매 참여 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/sh/view'">중고거래 판매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/shBid/view'">중고거래 요청 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/review/view'">내가 작성한 후기</span></li>
            </ul>
        </nav>

        <div class="under_content_main" style="width: 100%; padding-top: 45px; padding-left: 30px;">
            <span style="padding: 10px; font-size: 25px; font-weight: bold; color: #FF7500;">공동구매 참여 내역</span>
            <div id="list_wrapper">


            </div>
        </div>
    </div>

    <%@ include file="includes/footer.jsp" %>

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
            printUserInfo();
            printMyGP();
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

        function printMyGP() {
            $.ajax ({
                url: '/mypage/gp',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    console.log(data);

                    let order_arr = data.content;
                    let order_box = "";
                    $.each(order_arr, function(idx, value) {
                        order_box += "<div class='content_box'>"
                                        + "<a href='/gp/" + value.groupPurchase.id + "/view'>"
                                        + "<img src='../../images/grouppurchase/" + value.groupPurchase.imgUrl + "' style='width: 115px; height: 135px; margin-right: 30px;'>"
                                        + "</a>"
                                        + "<div style='display: flex; flex-direction: column; width: 680px;'>"
                                            + "<div>" + printStatus(value.groupPurchase.state) + "</div>"
                                            + "<a href='/gp/" + value.groupPurchase.id + "/view'>"
                                            + "<div style='font-size: 20px; font-weight: bold; margin-top: 10px;'>" + value.groupPurchase.name + "</div>"
                                            + "</a>"
                                            + "<div style='font-size: 14px; color: #B0B0B0; margin-top: 3px;'>"
                                                + "<span>" + value.groupPurchase.start + "</span>"
                                                + "<span>~</span>"
                                                + "<span>" + value.groupPurchase.end + "</span>"
                                            + "</div>"
                                            + "<div style='font-size: 15px; margin-top: 20px;'>" + value.qty + "개</div>"
                                            + "<div style='font-size: 25px; font-weight: bold;'>" + value.price + "원</div>"
                                        + "</div>"
                                    + "</div>";
                    });
                    $("#list_wrapper").html(order_box);
                }
            });
        }

        function printStatus(state) {
            let status = "";
            if (state == "ONGOING")
                status += "<span style='background-color: #84D444; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>진행 중</span>";
            else if (state == "URGENT")
                status += "<span style='background-color: #FF6666; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>마감 임박</span>";
            else if (state == "ACHIEVED") {
                status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>마감</span>"
                            + "<span style='background-color: #FF7500; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-left: 5px; border-radius: 7px;'>성공</span>";
            } else {
                status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>마감</span>"
                            + "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-left: 5px; border-radius: 7px;'>실패</span>";
            }

            return status;
        }

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
                });
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
                        printUserInfo();
                    }
                });
            }
        });

        $(document).on('click', '#cancel', function() {
            printUserInfo();
            printMyGP();
        });

    </script>
</body>
</html>