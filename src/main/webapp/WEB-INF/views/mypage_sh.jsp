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
        #update_sh, #update_sh_submit {
            background-color: #FF7500;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            border-radius: 10px;
            cursor: pointer;
        }
        #delete_sh {
            background-color: #616161;
            color: #FFFFFF;
            float: right;
            font-size: 14px;
            padding: 5px 7px;
            margin-left: 10px;
            border-radius: 10px;
            cursor: pointer;
        }
        #new_sh_name, #new_sh_price {
            font-size: 20px;
            width: 200px;
            height: 31px;
            border: 1px solid #C2C2C2;
            border-radius: 5px;
            padding-left: 10px;
            margin-top: 5px;
        }
        #shBid_accept {
            float: right;
            background-color: #FFEDDE;
            color: #FF7500;
            font-size: 14px;
            padding: 2px 5px;
            border: 1px solid #FF7500;
            border-radius: 7px;
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
        </div>
    </div>

    <div class="under_content">
        <nav class="nav_mypage">
            <ul>
                <li style="font-size: 20px; color: #333333;"><b>나의 쇼핑 활동</b></li>
                <li><span class="link" onclick="location.href='/mypage/view'">구매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/gp/view'">공동구매 참여 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/sh/view'" style="color: #FF7500;">중고거래 판매 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/shBid/view'">중고거래 요청 내역</span></li>
                <li><span class="link" onclick="location.href='/mypage/review/view'">내가 작성한 후기</span></li>
            </ul>
        </nav>
        <div class="under_content_main" style="width: 100%; padding-top: 45px; padding-left: 30px;">
            <span style="padding: 10px; font-size: 25px; font-weight: bold; color: #FF7500;">중고거래 판매 내역</span>
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
            printMySecondhand();
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

        function printMySecondhand() {
            $.ajax ({
                url: '/mypage/sh',
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    let sh_arr = data.content;
                    let sh_box = "";
                    $.each(sh_arr, function(idx, value) {
                        sh_box += "<div class='content_box'>"
                                    + "<a href='/secondhand/" + value.id + "/view'>"
                                    + "<img src='../../images/secondhand/" + value.imgUrl + "' style='width: 110px; height: 130px; margin-right: 30px;'>"
                                    + "</a>"
                                    + "<div style='display: flex; flex-direction: column; width: 680px;'>"
                                        + "<div id='" + value.id + "' style='height: 27px'>" + printState(value.state) + "</div>"
                                        + "<a href='/secondhand/" + value.id + "/view'>"
                                        + "<div style='font-size: 20px; font-weight: bold;'>" + value.name + "</div>"
                                        + "</a>"
                                        + "<div id='sh_price' style='font-size: 22px; font-weight: bold; margin-top: 25px;'>" + value.price + "원</div>"
                                        if (value.state == "n") {
                                            sh_box += "<div id='" + value.id + "'style='width: 50%; margin-top: 5px;'>"
                                                        + "<span id='show_shBid' style='color: #616161; font-size: 15px;'><i class='fa-solid fa-angle-down'></i>&nbsp;요청 내역</span>"
                                                        + "<span style='color: #FF7500; font-weight: bold;'>&nbsp;" + value.bidCnt + "</span>"
                                                        + "<div id='shBid_list' style='padding: 13px 10px;'></div>"
                                                    + "</div>";
                                        }
                                sh_box += "</div>"
                                + "</div>";
                    });
                    $("#list_wrapper").html(sh_box);
                }
            })
        }

        function printState(state) {
            let status = "";
            if (state == 'y')
                status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>판매완료</span>";
            else {
                status += "<span style='background-color: #84D444; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>판매 중</span>"
                            + "<span id='delete_sh'>삭제</span>"
                            + "<span id='update_sh'>수정</span>";
            }
            return status;
        }

        $(document).on('click', '#show_shBid', function() {
            let shId = $(this).parent().attr("id");

            let change = "<span id='hide_shBid' style='color: #616161; font-size: 15px;'><i class='fa-solid fa-angle-up'></i>&nbsp;요청 내역</span>"
            $(this).after(change);
            $(this).remove();

            $.ajax ({
                url: '/shBid/' + shId + '/sort',
                data: {
                    "sortBy" : "highprice"
                },
                success: function(data) {
                    let shBid_arr = data.content;
                    let shBid_list = "";
                    $.each(shBid_arr, function(idx, value) {
                        shBid_list += "<div id='" + value.bidId + "' data-shId='" + value.shId + "' data-price='" + value.price + "' data-status='" + value.bidStatus + "' style='margin-bottom: 10px;'>"
                                        + "<span>" + value.price + "원</span>"
                                        + "<span id='shBid_accept'>거래</span>"
                                    + "</div>"
                    });
                    $("#shBid_list").html(shBid_list);
                }
            })
        });

        $(document).on('click', '#shBid_accept', function() {
            let shId = $(this).parent().data('shId');
            let bidId = $(this).parent().attr('id');
            let bidPrice = $(this).parent().data('price');
            let bidState = $(this).parent().data('state');

            $.ajax ({
                type: 'POST',
                url: '/secondhand/update/' + shId + '/state/' + bidState + '/select/' + bidId,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    if (data.statusCode != 200) {
                        alert(data.message);
                    } else {
                        alert(bidPrice + '원에 거래가 완료되었습니다.');
                        location.href="/secondhand/" + shId + "/view";
                    }
                }
            })
        });


        $(document).on('click', '#hide_shBid', function() {
            let change = "<span id='show_shBid' style='color: #616161; font-size: 15px;'><i class='fa-solid fa-angle-down'></i>&nbsp;요청 내역</span>"
            $(this).after(change);
            $(this).remove();
            $("#shBid_list").empty();
        });

        $(document).on('click', '#update_sh', function() {
            let update_sh_box = "";
            update_sh_box += "<div style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>카테고리</div>"
                                + "<select id='new_cateCode' style='width: 120px; padding: 5px 20px 5px 5px; border: 1px solid #B0B0B0;'>"
                                    + "<option value='null' selected>카테고리 선택</option>"
                                    + "<option value='FUR'>가구</option>"
                                    + "<option value='FAB'>패브릭</option>"
                                    + "<option value='AD'>가전/디지털</option>"
                                    + "<option value='STO'>수납/정리</option>"
                                    + "<option value='DEC'>소품</option>"
                                    + "<option value='LIT'>조명</option>"
                                    + "<option value='PLA'>식물</option>"
                                + "</select>"
                                + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>상품명</div>"
                                + "<input type='text' id='new_sh_name'>"
                                + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>거래 가격</div>"
                                + "<input type='text' id='new_sh_price'>"
                                + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>상세 정보</div>"
                                + "<textarea id='new_sh_content' style='width: 80%; height: 80px; border: 1px solid #B0B0B0; border-radius: 5px; padding: 10px; margin-top: 5px;'></textarea>"

            $(this).parent().nextAll().remove();
            $(this).parent().after(update_sh_box);

            let btn = "";
            btn += "<span id='update_sh_submit'>등록</span>"
                    + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px;'>취소</span>";

            $(this).after(btn);
            $(this).prev().remove();
            $(this).remove();
        });

        $(document).on('click', '#update_sh_submit', function() {
            let shId = $(this).parent().attr("id");
            let new_cateCode = $("#new_cateCode option:selected").val();
            let new_sh_name = $("#new_sh_name").val();
            let new_sh_price = $("#new_sh_price").val();
            let new_sh_content = $("#new_sh_content").val();

            if (new_cateCode == "")
                alert('카테고리를 선택해 주세요.');
            else if (new_sh_name.length == 0)
                alert("등록할 상품명을 입력해 주세요.");
            else if (new_sh_price.length == 0 || new_sh_price == "0")
                alert("희망 거래 가격을 입력해 주세요.");
            else if (new_sh_content.length == 0)
                alert("상품 정보를 적어주세요.");
            else {
                $.ajax ({
                    type: 'PUT',
                    url: '/secondhand/update/' + shId,
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    data: JSON.stringify (
                        {
                            "cateCode" : new_cateCode,
                            "name" : new_sh_name,
                            "price" : new_sh_price,
                            "content" : new_sh_content,
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        printMySecondhand();
                    }
                })
            }
        });

        $(document).on('click', '#delete_sh', function() {
            let shId = $(this).parent().attr('id');

            $.ajax ({
                type: 'DELETE',
                url: '/secondhand/delete/' + shId,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    printMySecondhand();
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
            printMySecondhand();
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>