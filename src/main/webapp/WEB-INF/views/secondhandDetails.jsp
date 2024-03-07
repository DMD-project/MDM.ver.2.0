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
    <style>
        .bid_box {
            width:650px;
            height: 80px;
            padding: 10px 20px 20px 20px;
            margin-bottom: 20px;

            border: 1px solid #EE842A;
            border-radius: 10px;
        }

    </style>
</head>
<body>

<%@ include file="includes/header.jsp" %>

<div class="sample">

    <div class="sh_img">
        <img id="prod_img" src="" alt="">
    </div>
    <div class="product_info_wrapper">
        <div class="product_info">

            <h1><span id="sh_name">sh_name</span></h1>
            <span id="sh_update_submit">수정하기</span>
            <span id="sh_delete_submit">삭제하기</span>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>

            <div>
                <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>

                <span id="sh_price" style="font-size: 30px; font-weight: bold;"><b>sh_price</b></span>
                <span style="padding-left: 5px;">원</span>
            </div>

            <div style="margin-top: 30px;">
                <span style="color: #616161; font-size: 15px;">구매 희망가</span><br/>

                <input type="text" id="bid_price" placeholder="희망가 입력">
                <span style="padding-left: 5px;">원</span>
            </div>

        </div>

        <div id="submit_btn" class="button_wrapper">
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
            type: 'GET',
            url: '/secondhand/' + shId,
            beforeSend: function(xhr) {
                var token = getCookie("access_token");

                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            success: function(data) {
                console.log(data);

                let imgUrl = data.content.imgUrl;
                let name = data.content.name;
                let price = data.content.price;
                let content = data.content.content;
                let state = data.content.state;
                let userState = data.content.userState;

                $("#prod_img").attr('src', imgUrl);
                $("#prod_img").attr('alt', imgUrl);
                $("#sh_name").html(name);
                $("#sh_price").html(price);
                $("#sh_content").html(content);

                let bid_btn = "<button class='bid' id='bid_submit'><b>제안하기</b></button>";
                let soldout_btn = "<button class='bid'><b>판매완료</b></button>";

                if(state == "n") {
                    $("#submit_btn").html(bid_btn);
                } else {
                    $("#submit_btn").html(soldout_btn);
                }

                let bid_arr = data.content.secondHandBidList;
                let bid_box = "";

                $.each(bid_arr, function(idx, value) {
                    let str = "";
                    if(value.bidUserState == 'y' && state == 'n') {
                        str += "<div id='delete_bid_submit' style='float: right; margin-left: 10px; color: #FF7500;'><i class='fa-solid fa-xmark'></i></div>"
                              + "<div id='update_bid_submit' style='float: right; font-size: 14px; color: #FF7500;'>수정</div>";
                    }

                    let btnStr = "";
                    if(state == "n") {
                        if(userState == "y") {
                            btnStr += "<button id='select_bid_submit' data-price='" + value.price + "' data-state='" +value.bidState+ "' style='float: right; font-size: 14px; color: #FF7500;'>거래하기</button>";
                        }
                    }

                    bid_box += "<div class='bid_box' id='select_id' data-bid='" + value.bidId +"'>"
                                + str
                                + "<div id='select_price'><b>" + value.price + "</b> 원 </div>"
                                + btnStr
                            + "</div>";
                });

                $("#bid_list").html(bid_box);
            }
        });
    });

    $(document).on('click', '#sh_delete_submit', function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let shId = splitUrl[splitUrl.length - 2];

        alert(shId);

        $.ajax({
            type: 'DELETE',
            url: '/secondhand/delete/' + shId,
            beforeSend: function(xhr) {
                var token = getCookie("access_token");

                if (!token) {
                    alert("로그인이 필요합니다.");
                }
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            error: function(xhr, data) {
                alert(data.message);
                return;
            },
            success: function(data) {
                if(data.statusCode != 200) {
                    alert(data.message);
                    return;
                } else {
                    alert("삭제가 완료되었습니다.");
                    location.href="http://localhost:8080/secondhand/list/view";
                }
            }
        });
    });

    $(document).on('click', '#sh_update_submit', function() {

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

    $(document).on('click', '#select_bid_submit', function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let shId = splitUrl[splitUrl.length - 2];

        let bidId = $(this).parent().data('bid');
        let bidPrice = $(this).data('price');
        let bidState = $(this).data('state');

        $.ajax({
            type: 'POST',
            url: '/secondhand/update/' + shId + '/state/' + bidState + '/select/' + bidId,
            beforeSend: function(xhr) {
                var token = getCookie("access_token");

                if (!token) {
                    alert("로그인이 필요합니다.");
                }
                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            error: function(xhr, data) {
                alert(data.message);
            },
            success: function(data) {
                if (data.statusCode != 200) {
                    alert(data.message);
                } else {
                    alert(bidPrice + '원에 거래가 완료되었습니다.');
                    location.href="http://localhost:8080/secondhand/" + shId + "/view";
                }
            }
        });
    });

    $(document).on('click', '#update_bid_submit', function() {
        let bidId = $(this).parent().data('bid');
    });

    $(document).on('click', '#delete_bid_submit', function() {
        let url = window.location.href;
        let splitUrl = url.split("/");

        let shId = splitUrl[splitUrl.length - 2];

        let bidId = $(this).parent().data('bid');

        $.ajax({
            type:'DELETE',
            url: '/shBid/' + shId + '/delete/' +bidId,
            beforeSend: function(xhr) {
                var token = getCookie("access_token");

                if (!token) {
                    alert("로그인이 필요합니다.");
                    window.location.href='/login';
                }

                xhr.setRequestHeader("Authorization", "Bearer " + token);
            },
            error: function(xhr, data) {
                alert(data.message);
                return;
            },
            success: function(data) {
                alert("요청 삭제가 완료되었습니다.");
                location.href="http://localhost:8080/secondhand/" + shId + "/view";
            }
        });
    });

</script>

<%@ include file="includes/footer.jsp" %>

</body>
</html>