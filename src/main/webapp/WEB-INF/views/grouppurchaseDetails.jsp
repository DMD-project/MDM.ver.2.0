<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Grouppurchase Details</title>

    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/3.0.1/js.cookie.min.js"></script>

    <style>
        body {
            background-color: #F9F1E7;
        }
        .sample {
            display : flex;
            justify-content: center;
            margin-top: 30px;
            margin-bottom: 50px;
        }
        .gp_img {
            background-color: white;
            width: 500px;
            height: 500px;
            border: none;
        }

        .gp_info_wrapper {
            padding: 0 30px;
        }
        .gp_info {
            background-color: white;
            width: 400px;
            height: 330px;
            padding: 40px 0 35px 50px;
            margin: auto;
        }
        .limit_info_wrapper {
            display : flex;
            margin-bottom:20px;
        }
        .limit_info_date {
            text-align: right;
            margin-left: 10px;
        }
        .limit_info_count {
            text-align: right;
            margin-left: 25px;
        }
        .button_wrapper {
            width: 450px;
            display : flex;
            justify-content: center;
            margin-top: 30px;
        }
        .submit {
              background-color: #FF7500;
              color: white;
              font-size: 20px;
              text-align: center;
              width: 450px;
              height: 60px;
              border: none;
              border-radius: 10px;
         }
        .info_nav_bar {
             background-color: #F5F5F5;
             height: 60px;
             line-height: 60px;
             padding-left: 150px;
        }
        .info_nav_bar div {
            width: 150px;
            text-align: center;
            float: left;
        }
        .info_nav_bar_border {
            color: #FF7500;
            border-bottom: 3px solid #FF7500;
        }
        .count_wrapper {
            margin-top: 10px;
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
    </style>
</head>
<body>

<%@ include file="includes/header.jsp" %>

<div class="sample">

    <div class="gp_img">
        <img id="prod_img" src="" alt="">
    </div>
    <div class="gp_info_wrapper">
        <div class="gp_info">
            <div id="status_bar" style="margin-bottom: 10px;"></div>
            <span id="gp_name" style="font-size: 30px; font-weight: bold">gp_name</span>

            <div class="limit_info_wrapper" style="margin: 20px 0;">
                <div class="limit_info_date">
                    <span>남은 기간</span>
                    <br/>
                    <span id="gp_DDay" style="font-size: 35px;">N</span>
                    <span style="font-size: 13px;">일</span>
                </div>
                <div class="limit_info_count">
                    <span>참여인원</span>
                    <br/>
                    <span id="gp_participants" style="font-size: 35px;">0</span>
                    <span style="font-size: 13px;">명</span>
                </div>
            </div>

            <div>
                <span style="color: #616161; font-size: 15px;">현재수량/목표 수량</span>
                <span id="qty" style="padding-left: 5px;">goal_qty</span>
                <span>개 /</span>
                <span id="goal_qty" style="padding-left: 5px;">qty</span>
                <span>개</span>
            </div>
            <div>
                <span style="color: #616161; font-size: 15px;">공동구매 기간</span>
                <span id="start_date" style="padding-left: 10px;">start_date</span>
                <span style="padding-left: 10px;">~</span>
                <span id="end_date" style="padding-left: 10px;">end_date</span>
            </div>
            <div>
                <span style="color: #616161; font-size: 15px;">주문수량</span>
                <div class="count_wrapper">
                    <button type="button" id="count_minus">-</button>
                    <input type="text" id="count_value" value="1">
                    <button type="button" id="count_plus">+</button>
                </div>
            </div>
            <br/>
            <div>
                <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>
                <span id="gp_price" style="font-size: 30px; font-weight: bold;"><b>gp_price</b></span>
                <span style="padding-left: 5px;">원</span>
            </div>
        </div>

        <div class="button_wrapper">
            <button class="submit" id="gp_join"><b>참여하기</b></button>
        </div>

    </div>
</div>

<div class="info_nav_bar">
    <div class="info_nav_bar_border">상품 정보</div>
</div>

<div style="background-color: #FFFFFF; padding: 50px 160px;">
    <span id="gp_content"></span>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>

    let maxQty = 0;
    $(document).ready(function() {
        printGPDetails();
    });

    function printGPDetails() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let gpId = splitUrl[splitUrl.length - 2];
        $.ajax ({
            url: '/gp/' + gpId,
            success: function(data) {
                console.log(data);

                let gp_imgUrl = data.content.groupPurchase.imgUrl;
                let gp_name = data.content.groupPurchase.name;
                let gp_participants_qty = data.content.participantCount;
                let gp_goal_qty = data.content.groupPurchase.goalQty;
                let gp_now_qty = data.content.groupPurchase.nowQty;

                maxQty = data.content.groupPurchase.maxQty;

                $("#prod_img").attr('src', gp_imgUrl);
                $("#prod_img").attr('alt', gp_imgUrl);
                $("#gp_name").html(gp_name);
                $("#gp_participants").html(gp_participants_qty);
                $("#goal_qty").html(gp_goal_qty);
                $("#qty").html(gp_now_qty);

                let gp_start = data.content.groupPurchase.start;
                let gp_end = data.content.groupPurchase.end;
                let gp_price = data.content.groupPurchase.price;
                let gp_content = data.content.groupPurchase.content;

                $("#start_date").html(gp_start);
                $("#end_date").html(gp_end);
                $("#gp_price").html(gp_price);
                $("#gp_content").html(gp_content);

                let end_date = new Date(gp_end);
                let today = new Date();
                let diff = today - end_date;
                let result = -(Math.ceil(diff / (1000 * 60 * 60 * 24)) - 1);

                if (result <= 0)
                    result = 0;

                $("#gp_DDay").html(result);

                let status = "";
                let state = data.content.groupPurchase.state;
                if (state == "ONGOING")
                    status += "<span style='background-color: #84D444; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>진행 중</span>";
                else if (state == "URGENT")
                    status += "<span style='background-color: #FF6666; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>마감 임박</span>";
                else if (state == "ACHIEVED") {
                    status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>실패</span>";
                            + "<span style='background-color: #FF7500; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-left: 5px; border-radius: 7px;'>성공</span>";
                    $(".submit").css("background-color", "#868686");
                    $(".submit").attr("disabled", true);
                    $(".submit").text("마감");
                } else {
                    status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; border-radius: 7px;'>마감</span>"
                            + "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-left: 5px; border-radius: 7px;'>실패</span>";
                    $(".submit").css("background-color", "#868686");
                    $(".submit").attr("disabled", true);
                    $(".submit").text("마감");
                }
                $("#status_bar").html(status);
            }
        })
    }

    $(document).on('click', '#count_minus', function() {
        let count_value = $('#count_value').val();
        if (count_value == 1)
            alert('최소 수량 입니다.');
        else
            count_value--;

        $("#count_value").attr('value', count_value);
    });

    $(document).on('click', '#count_plus', function() {
        let count_value = $('#count_value').val();
        count_value++;

        if (maxQty < count_value)
            alert('1인당 최대 구매 갯수는 ' + maxQty + '개 입니다.');
        else
            $("#count_value").attr('value', count_value);
    });

    $(document).on('click', '#gp_join', function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let gpId = splitUrl[splitUrl.length - 2];
        let purchasedQty = $('#count_value').val();
        $.ajax ({
            type: 'POST',
            url: '/gp/order/' + gpId + "/" + purchasedQty,
            data: {
                "gpId" : gpId,
                "purchasedQty" : purchasedQty
            },
            beforeSend: function(xhr) {
                var token = getCookie("access_token");
                console.log("Token:", token);

                if(!token) {
                    alert("로그인이 필요합니다.");
                    location.href="http://localhost:8080/login";
                    return;
                }
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function(data) {
                if(data.statusCode != 200) {
                    alert(data.message);
                    return;
                }
                else {
                    if(confirm("참여가 완료되었습니다.\n마이페이지에서 확인하기") == true) {
                        location.href="http://localhost:8080/mypage/gp/view";
                    } else {
                        location.reload();
                    }
                }
            }
        })
    });

</script>

<%@ include file="includes/footer.jsp" %>

</body>
</html>