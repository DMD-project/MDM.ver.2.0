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
        #update_shBid, #update_shBid_submit {
            background-color: #FF7500;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            border-radius: 10px;
            cursor: pointer;
        }
        #delete_shBid {
            background-color: #616161;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            margin-left: 10px;
            border-radius: 10px;
            cursor: pointer;
        }
        #new_shBid {
            width: 200px;
            height: 31px;
            font-size: 22px;
            border: 1px solid #616161;
            border-radius: 5px;
            padding: 0 10px;
        }
        #new_shBid::placeholder {
            color: #C2C2C2;
            text-align: right;
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
                <li><span class="link" onclick="location.href='/mypage/shBid/view'" style="color: #FF7500;">중고거래 요청 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/review/view'">내가 작성한 후기</span></li>
            </ul>
        </nav>
        <div class="under_content_main" style="width: 100%; padding-top: 45px; padding-left: 30px;">
            <span style="padding: 10px; font-size: 25px; font-weight: bold; color: #FF7500;">중고거래 요청 내역</span>
            <div id="list_wrapper"></div>
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
            printUserInfo();
            printMyShBid();
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
            })
        }

        function printMyShBid() {
            $.ajax ({
                url: '/mypage/shBid',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    let shBid_arr = data.content;
                    let shBid_box = "";
                    $.each(shBid_arr, function(idx, value) {
                        shBid_box += "<div class='content_box'>"
                                        + "<a href='/secondhand/" + value.secondHand.id + "/view'>"
                                        + "<img src='" + value.secondHand.imgUrl + "' style='width: 110px; height: 130px; margin-right: 30px;'>"
                                        + "</a>"
                                        + "<div style='display:flex; flex-direction: column; width: 680px;'>"
                                            + "<div id='" + value.secondHand.id + "' class='" + value.id + "'>" + printState(value.bidState, value.id, value.secondHand.selectBidId) + "</div>"
                                            + "<a href='/secondhand/" + value.secondHand.id + "/view'>"
                                            + "<div style='font-size: 20px; font-weight: bold'>" + value.secondHand.name + "</div>"
                                            + "</a>"
                                            + "<div style='margin-top: 3px'>" + value.secondHand.price + "원</div>"
                                            + "<div id='shBid_label' style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>구매 희망가</div>"
                                            + "<div id='shBid_price' style='font-size: 25px; font-weight: bold;'>" + value.price + "원</div>"
                                        + "</div>"
                                    + "</div>";
                    });
                    $("#list_wrapper").html(shBid_box);
                }
            })
        }

        function printState(state, bidId, selectBidId) {
            let status = "";
            if (state == 'y' && (bidId == selectBidId))
                status += "<span style='display: inline-block; margin-bottom: 4px; background-color: #84D444; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-bottom: 5px; border-radius: 7px;'>희망가 채택</span>";
            else if (state == 'y' && (bidId != selectBidId)) {
                status += "<span style='display: inline-block; margin-bottom: 4px; background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-bottom: 5px; border-radius: 7px;'>판매 완료</span>";
            }
            else {
                status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-bottom: 5px; border-radius: 7px;'>대기 중</span>"
                            + "<span id='delete_shBid' style='float: right; margin-left: 10px; background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 5px 7px; border-radius: 10px;'>삭제</span>"
                            + "<span id='update_shBid' style='float:right;'>희망가 수정</span>";
            }
            return status;
        }

        $(document).on('click', '#update_shBid', function() {
            let input_box = "<input type='text' id='new_shBid' placeholder='희망가 입력'>"
            $(this).parent().siblings('div#shBid_price').after(input_box);
            $(this).parent().siblings('div#shBid_price').remove();

            let btn = "";
            btn += "<span id='update_shBid_submit'>등록</span>"
                    + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px;'>취소</span>";

            $(this).after(btn);
            $(this).prev().remove();
            $(this).remove();
        });

        $(document).on('click', '#update_shBid_submit', function() {
            let shId = $(this).parent().attr('id');
            let shBidId = $(this).parent().attr('class');
            let new_shBid = $('#new_shBid').val();

            if (new_shBid.length == 0 || new_shBid == "0")
                alert('구매 희망가를 입력해 주세요.');
            else {
                $.ajax ({
                    type: 'POST',
                    url: '/shBid/' + shId + '/update/' + shBidId,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    data: JSON.stringify (
                        {
                            "shId" : shId,
                            "price" : new_shBid
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        printMyShBid();
                    }
                })
            }
        });

        $(document).on('click', '#delete_shBid', function() {
            let shId = $(this).parent().attr('id');
            let shBidId = $(this).parent().attr('class');

            $.ajax ({
                type: 'DELETE',
                url: '/shBid/' + shId + '/delete/' + shBidId,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    printMyShBid();
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
                        printUserInfo();
                    }
                })
            }
        });

        $(document).on('click', '#cancel', function() {
            printUserInfo();
            printMyShBid();
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>