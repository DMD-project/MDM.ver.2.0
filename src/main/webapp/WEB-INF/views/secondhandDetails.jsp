<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Secondhand Detail</title>

    <style>
        body {
          background-color: #F9F1E7;
        }
        input {
            background-color: #FFFFFF;

            width: 300px;

            color: #000000;
            font-size: 20px;
            border: 1px solid #C2C2C2;
            border-radius: 10px;

            margin-top: 5px;
            padding: 10px;
        }
        input::placeholder {
            color: #C2C2C2;
            text-align: right;
        }

        .sample {
          display : flex;
          justify-content: center;

          margin-top: 30px;
          margin-bottom: 50px;
        }
        .sh_img {
          background-color: white;

          width: 500px;
          height: 500px;

          border: none;
        }

        .product_info_wrapper {
            padding: 0 30px;
        }
        .product_info {
          background-color: white;

          width: 400px;
          height: 330px;

          padding: 35px 0 40px 50px;
          margin: auto;
        }
        .button_wrapper {
          width: 450px;

          display : flex;
          justify-content: center;

          margin-top: 30px;
        }

        .bid {
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

    <div class="sh_img">
        <img src="" alt="">
    </div>
    <div class="product_info_wrapper">
        <div class="product_info">

            <h1><span id="sh_name">sh_name</span></h1>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>

            <div>
                <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>

                <span id="sh_price" style="font-size: 30px;"><b>sh_price</b></span>
                <span style="padding-left: 5px;">원</span>
            </div>

            <div style="margin-top: 30px;">
                <span style="color: #616161; font-size: 15px;">구매 희망가</span><br/>

                <input type="text" id="bid_price" placeholder="희망가 입력">
                <span style="padding-left: 5px;">원</span>
            </div>

        </div>

        <div class="button_wrapper">
            <button class="bid" id="bid_submit"><b>제안하기</b></button>
        </div>

    </div>
</div>

<div class="info_nav_bar">
    <div class="info_nav_bar_border">상품 정보</div>
</div>

<div style="background-color: #FFFFFF; padding: 50px 160px;">
    <span id="sh_content"></span>
</div>

<div>
    <span id="bid_list"></span>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let shId = splitUrl[splitUrl.length - 2];

        $.ajax ({
            url: '/secondhand/' + shId,
            success: function(data) {
                console.log(data);

                let imgUrl = data.content.imgUrl;
                let name = data.content.name;
                let price = data.content.price;
                let content = data.content.content;

                $("img").attr('src', imgUrl);
                $("img").attr('alt', imgUrl);
                $("#sh_name").html(name);
                $("#sh_price").html(price);
                $("#sh_content").html(content);

                let bid_arr = data.content.secondHandBidList;
                let bid_box = "";
                $.each(bid_arr, function(idx, value) {
                    bid_box += "" + value.bidId
                                + "" + value.shId
                                + "" + value.bidUserId
                                + "" + value.price
                                + "" + value.bidUserState;
                });
                $("#bid_list").html(bid_box);
            }
        });
    });

    $(document).on('click', '#bid_submit', function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let shId = splitUrl[splitUrl.length - 2];

        let bid_price = $("#bid_price").val();

        if(bid_price.length == 0) {
            alert("구매 희망가를 입력해 주세요.");
        }

        $.ajax({
            type: 'POST',
            url: '/shBid/' + shId + '/add',
            beforeSend: function(xhr) {
                var token = getCookie("access_token");
                console.log("Token:", token);

                if(!token) {
                    alert("로그인이 필요합니다.");
                    window.location.href='/login';
                }
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            contentType : 'application/json',
            data : JSON.stringify (
                {
                    "shId" : shId,
                    "price" : bid_price
                }
            ),
            success: function(data) {
                if(data.statusCode != 200) {
                    alert(data.message);
                    return;
                } else {
                    alert("구매 희망가가 등록되었습니다.");
                    location.href="http://localhost:8080/secondhand/" + data.content.secondHand.id + "/view";
                }
            }
        });
    });
</script>

<%@ include file="includes/footer.jsp" %>

</body>
</html>