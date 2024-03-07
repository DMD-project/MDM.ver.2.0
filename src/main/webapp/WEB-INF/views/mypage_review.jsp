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
        a {
            color: black;
            text-decoration: none;
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
            cursor: pointer;
        }
        .content_box {
            display: flex;
            float: left;
            width: 80%;
            padding: 30px;
            margin-top: 20px;
        }
        #delete_review {
            background-color: #616161;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            margin-left: 10px;
            padding: 5px 7px;
            border-radius: 10px;
            cursor: pointer;
        }
        #update_review, #update_review_submit {
            background-color: #FF7500;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            border-radius: 10px;
            cursor: pointer;
        }
        .review_star {
            color: transparent;
            text-shadow: 0 0 0 #F0F0F0;
        }
        .review_star_on {
            color: transparent;
            text-shadow: 0 0 0 #FFBC00;
        }
        .update_ratingStar {
            display: inline-block;
            width: 20px;
            height: 20px;
            color: transparent;
            text-shadow: 0 0 0 #F0F0F0;
            font-size: 1.5em;
            box-sizing: border-box;
            cursor: pointer;
        }
        .update_ratingStar .star.on {
            text-shadow: 0 0 0 #FFBC00;
        }
        .update_ratingStar .star:hover {
            text-shadow: 0 0 0 #CCC;
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
                <li><span class="link" onclick="location.href='/mypage/gp/view'">공동구매 참여 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/sh/view'">중고거래 판매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/shBid/view'">중고거래 요청 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/review/view'"  style="color: #FF7500;">내가 작성한 후기</span></li>
            </ul>
        </nav>

        <div class="under_content_main" style="width: 100%; padding-top: 45px; padding-left: 30px;">
            <span style="padding: 10px; font-size: 25px; font-weight: bold; color: #FF7500;">내가 작성한 후기</span>
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
            printMyReview();
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

        function printMyReview() {
            $.ajax ({
                url: '/mypage/review',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    console.log(data);

                    let review_arr = data.content;
                    let review_box = "";
                    $.each(review_arr, function(idx, value) {
                        review_box += "<div class='content_box'>"
                                        + "<a href='/product/" + value.product.id + "/view'>"
                                        + "<img src='" + value.product.imgUrl + "' style='width: 110px; height: 130px; margin-right: 30px;'>"
                                        + "<div style='display:flex; flex-direction: column; width: 680px; margin-top: 10px;'>"
                                            + "<div id='" + value.product.id + "' class='" + value.id +"'>"
                                                + "<span style='font-size: 20px; font-weight: bold;'>" + value.product.name + "</span>"
                                                + "</a>"
                                                + "<span id='delete_review'>삭제</span>"
                                                + "<span id='update_review'>수정<span>"
                                            + "</div>"
                                        + "<div style='margin-top: 10px;'>" + printStar(value.star) + "</div>"
                                        + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 5px;'>작성일자 " + value.date + "</div>"
                                        + "<div style='margin-top:10px;'>" + value.content + "</div>"
                                        + "</div>"
                                    + "</div>";
                    });
                    $("#list_wrapper").html(review_box);
                }
            });
        }

        function printStar(star) {
            let total = 5;
            let star_print = "";
            for (let i = 0; i < star; i++)
                star_print += "<span class='review_star_on'>⭐</span>";

            let left = total - star;
            for (let i = 0; i < left; i++)
                star_print += "<span class='review_star'>⭐</span>";

            return star_print;
        }

        let update_star = 0;
        $(document).on('click', '.update_ratingStar span', function() {
            $(this).parent().children('span').removeClass('on');
            $(this).addClass('on').prevAll('span').addClass('on');

            update_star = $(this).data('value');
        });

        $(document).on('click', '#update_review', function() {
            $(this).parent().nextAll().remove();

            let update_review_box = "";
            update_review_box += "<div class='update_ratingStar' style='float: left; width:650px; font-size: 15px;'>"
                                    + "<span class='star' data-value='1'>⭐</span>"
                                    + "<span class='star' data-value='2'>⭐</span>"
                                    + "<span class='star' data-value='3'>⭐</span>"
                                    + "<span class='star' data-value='4'>⭐</span>"
                                    + "<span class='star' data-value='5'>⭐</span>"
                                + "</div>"
                                + "<textarea id='update_review_content' style='width: 100%; height: 70px; margin-top: 12px;'></textarea>";

            $(this).parent().after(update_review_box);

            let btn = "";
            btn += "<span id='update_review_submit'>등록</span>"
                    + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px;'>취소</span>";

            $(this).after(btn);
            $(this).prev().remove();
            $(this).remove();
        });

        $(document).on('click', '#update_review_submit', function() {
            let prodId = $(this).parent().attr('id');
            let reviewId = $(this).parent().attr('class');
            let content = $('#update_review_content').val();

            if (update_star == 0) {
                alert('별점을 수정해 주세요.');
            } else if (content == "") {
                alert('후기를 수정해 주세요.');
            } else {
                $.ajax ({
                    type: 'POST',
                    url: '/review/' + prodId + '/update/' + reviewId,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        console.log("Token:", token);
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    contentType: 'application/json',
                    data: JSON.stringify(
                        {
                            "star" : update_star,
                            "content" : content
                        }
                    ),
                    success: function(data) {
                        printMyReview();
                    }
                });
            }
        });

        $(document).on('click', '#delete_review', function() {
            let prodId = $(this).parent().attr('id');
            let reviewId = $(this).parent().attr('class');

            $.ajax ({
                type: 'DELETE',
                url: '/review/' + prodId + '/delete/' + reviewId,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    printMyReview();
                }
            })
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
            printMyReview();
        });

    </script>
</body>
</html>