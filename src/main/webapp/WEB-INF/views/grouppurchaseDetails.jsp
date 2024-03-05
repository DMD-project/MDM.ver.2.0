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

          padding: 35px 0 40px 50px;
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
    </style>
</head>
<body>

<%@ include file="includes/header.jsp" %>

<div class="sample">

    <div class="gp_img">
        <img src="" alt="">
    </div>
    <div class="gp_info_wrapper">
        <div class="gp_info">

            <h1><span id="gp_name">gp_name</span></h1>

            <div class="limit_info_wrapper">
                <div class="limit_info_date">
                    <span>남은 기간</span>
                    <br/>
                    <span style="font-size: 35px;">N</span>
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
                <span style="color: #616161; font-size: 15px;">목표 수량</span>
                <span id="goal_qty" style="padding-left: 5px;">goal_qty</span>
                <span>개</span>
            </div>
            <div>
                <span style="color: #616161; font-size: 15px;">현재 수량</span>
                <span id="qty" style="padding-left: 5px;">qty</span>
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
                <input id="purchasedQty" type="number" min="1" max="10" step="1" value="1" style="margin: 10px;">
            </div>
            <br/>
            <div>
                <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>
                <span id="gp_price" style="font-size: 30px;"><b>gp_price</b></span>
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

    $(document).ready(function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let gpId = splitUrl[splitUrl.length - 2];

        $.ajax ({
            url: '/gp/' + gpId,
            success: function(data) {

                let gp_imgUrl = data.content.imgUrl;
                let gp_name = data.content.name;
                let gp_participants_qty = data.content.participantsQty;
                let gp_goal_qty = data.content.goalQty;
                let gp_now_qty = data.content.nowQty;

                $("img").attr('src', gp_imgUrl);
                $("img").attr('alt', gp_imgUrl);
                $("#gp_name").html(gp_name);
                $("#gp_participants").html(gp_participants_qty);
                $("#goal_qty").html(gp_goal_qty);
                $("#qty").html(gp_now_qty);

                let gp_start = data.content.start;
                let gp_end = data.content.end;
                let gp_price = data.content.price;
                let gp_content = data.content.content;

                $("#start_date").html(gp_start);
                $("#end_date").html(gp_end);
                $("#gp_price").html(gp_price);
                $("#gp_content").html(gp_content);
            }
        });
    });

    $(document).on('click', '#gp_join', function() {

        let url = window.location.href;
        let splitUrl = url.split("/");

        let gpId = splitUrl[splitUrl.length - 2];
        let purchasedQty = $('#purchasedQty').val();

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
                        console.log("취소되었습니다");
                    }
                }
            }
        });
    });

</script>

<%@ include file="includes/footer.jsp" %>

</body>
</html>