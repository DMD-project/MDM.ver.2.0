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
            border: 1px solid #C2C2C2;
            border-radius: 10px;
            margin-top: 5px;
            padding: 10px;
        }
        input::placeholder {
            color: #C2C2C2;
            text-align: right;
        }
        input:read-only {
            background-color: #EEEEEE;
            color: #C2C2C2;
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
            width: 360px;
            height: 330px;
            padding: 35px 40px 40px 50px;
            margin: auto;
        }
        .button_wrapper {
            width: 450px;
            display : flex;
            justify-content: center;
            margin-top: 30px;
        }
        .bid_btn {
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
        .bid_box {
            width:650px;
            height: 80px;
            padding: 10px 20px 20px 20px;
            margin-bottom: 20px;
            border: 1px solid #EE842A;
            border-radius: 10px;
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
        #bid_accept, #update_bid, #update_bid_submit {
            float: right;
            background-color: #FFEDDE;
            color: #FF7500;
            font-size: 14px;
            padding: 2px 5px;
            border: 1px solid #FF7500;
            border-radius: 7px;
            cursor: pointer;
        }
        #delete_bid {
            float: right;
            background-color: #C2C2C2;
            color: #616161;
            font-size: 14px;
            padding: 2px 5px;
            margin-left: 7px;
            border: 1px solid #616161;
            border-radius: 7px;
            cursor: pointer;
        }
        #new_bid {
            background-color: #FFFFFF;
            width: 50%;
            color: #000000;
            border: 1px solid #C2C2C2;
            border-radius: 5px;
            padding: 5px;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="sample">
        <div class="sh_img">
            <img id="prod_img" src="" alt="" style="width: 500px; height: 500px;">
        </div>
        <div class="product_info_wrapper">
            <div class="product_info">
                <div id="status_bar" style="margin-bottom: 10px;"></div>
                <span id="sh_name" style="font-size: 30px; font-weight: bold;">sh_name</span>
                <div style="margin-top:20px;">
                    <span style="color: #616161; font-size: 15px;">상품 가격</span><br/>
                    <span id="sh_price" style="font-size: 30px; font-weight: bold;"><b>sh_price</b></span>
                    <span style="padding-left: 5px;">원</span>
                </div>
                <div style='overflow: scroll; width: 85%; margin-top: 5px; height: 120px;'>
                    <span style='color: #616161; font-size: 15px;'>요청 내역</span>
                    <span id="bid_count" style='color: #FF7500; font-weight: bold;'>0</span>
                        <div id='shBid_list' style='padding: 13px 10px;'></div>
                </div>
                <div class="sh_bid" style="margin-top: 5px; display: block;">
                    <span style="color: #616161; font-size: 15px;">구매 희망가</span><br/>
                    <input type="text" id="bid_price" placeholder="희망가 입력" style="font-size: 20px;">
                    <span style="padding-left: 5px;">원</span>
                </div>
            </div>
            <div id="submit_btn" class="button_wrapper"></div>
        </div>
    </div>

    <div class="info_nav_bar">
        <div class="info_nav_bar_border">상품 정보</div>
    </div>
    <div style="background-color: #FFFFFF; padding: 50px 160px;">
        <span id="sh_content"></span>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            printSecondhandDetails();
        });

        function printSecondhandDetails() {
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
                    let imgUrl = data.content.imgUrl;
                    let name = data.content.name;
                    let price = data.content.price;
                    let content = data.content.content;
                    let bid_count = data.content.secondHandBidList.length;

                    $("#prod_img").attr('src', '../../images/secondhand/' + imgUrl);
                    $("#prod_img").attr('alt', imgUrl);
                    $("#sh_name").html(name);
                    $("#sh_price").html(price);
                    $("#sh_content").html(content);
                    $("#bid_count").html(bid_count);

                    let isWriter = data.content.userState;
                    let soldout = data.content.state;

                    let bid_btn = "<button class='bid_btn' id='bid_submit'><b>제안하기</b></button>";
                    let soldout_btn = "<button class='bid_btn'><b>판매 완료</b></button>";
                    let seller_btn = "<button class='bid_btn'><b></b></button>"

                    let status = "";
                    if (soldout == 'y') {
                        status += "<span style='background-color: #616161; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-bottom: 5px; border-radius: 7px;'>판매 완료</span>";
                        $("#submit_btn").html(soldout_btn);
                        $(".bid_btn").css("background-color", "#868686");
                        $(".bid_btn").attr("disabled", true);
                        $(".product_info").css('height', '330px');
                        $("#bid_price").attr("placeholder", '판매 완료');
                        $("#bid_price").attr("readonly", true);
                    } else if (soldout == 'n' && isWriter == 'y') {
                        status += "<span style='background-color: #84D444; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-bottom: 5px; border-radius: 7px;'>판매 중</span>"
                                + "<span id='delete_sh'>삭제</span>"
                                + "<span id='update_sh'>수정</span>";
                        $(".product_info").css('height', '425px');
                        $(".sh_bid").css("display", "none");
                    } else if (soldout == 'n' && isWriter == 'n') {
                        status += "<span style='background-color: #84D444; color: #FFFFFF; font-size: 14px; padding: 3px 7px; margin-bottom: 5px; border-radius: 7px;'>판매 중</span>"
                        $("#submit_btn").html(bid_btn);
                        $(".product_info").css('height', '330px');
                    }
                    $("#status_bar").html(status);

                    let bid_arr = data.content.secondHandBidList;
                    let bid_box = "";
                    $.each(bid_arr, function(idx, value) {
                        let isBidUser = value.bidUserState;
                        bid_box += "<div id='" + value.bidId + "' data-price='" + value.price + "' data-state='" + value.bidState + "' style='margin-bottom: 10px;'>"
                                    + "<span id='bid'>" + value.price + "원</span>";
                                if (soldout == 'n') {
                                    if (isWriter == 'y') {
                                        bid_box += "<span id='bid_accept'>거래</span>";
                                    } else if (isBidUser == 'y') {
                                        bid_box += "<span id='delete_bid'>삭제</span>"
                                                    +"<span id='update_bid'>수정</span>";
                                    }
                                }
                        bid_box += "</div>";
                    });
                    $("#shBid_list").html(bid_box);
                }
            })
        }

        $(document).on('click', '#cancel', function() {
            location.reload();
        });

        $(document).on('click', '#delete_sh', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");
            let shId = splitUrl[splitUrl.length - 2];
            $.ajax({
                type: 'DELETE',
                url: '/secondhand/delete/' + shId,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                error: function(xhr, data) {
                    alert(data.message);
                    return;
                },
                success: function(data) {
                    if(data.statusCode != 200) {
                        alert("상품을 삭제할 수 없습니다.");
                        return;
                    } else {
                        alert("삭제가 완료되었습니다.");
                        location.href="/secondhand/list/view";
                    }
                }
            })
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
                                + "<input type='text' id='new_sh_name' style='font-size: 15px;'>"
                                + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>거래 가격</div>"
                                + "<input type='text' id='new_sh_price' style='font-size: 15px;'>"
                                + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 10px;'>상세 정보</div>"
                                + "<textarea id='new_sh_content' style='width: 80%; height: 145px; border: 1px solid #B0B0B0; border-radius: 5px; padding: 15px; margin-top: 5px;'></textarea>"

            $(this).parent().nextAll().hide();
            $(this).parent().after(update_sh_box);

            let status_bar = "";
            status_bar += "<span id='update_sh_submit'>등록</span>"
                        + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px;'>취소</span>";

            $(this).after(status_bar);
            $(this).prev().hide();
            $(this).hide();
        });

        $(document).on('click', '#update_sh_submit', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");
            let shId = splitUrl[splitUrl.length - 2];
            let new_cateCode = $("#new_cateCode option:selected").val();
            let new_sh_name = $("#new_sh_name").val();
            let new_sh_price = $("#new_sh_price").val();
            let new_sh_content = $("#new_sh_content").val();

            if (new_cateCode == "")
                alert('카테고리를 선택해 주세요.');
            else if (new_sh_name.length == 0)
                 alert('등록할 상품명을 입력해 주세요.');
            else if (new_sh_price.length == 0 || new_sh_price == "")
                alert('희망 거래 가격을 입력해 주세요.');
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
                        location.reload();
                    }
                })
            }
        });

        $(document).on('click', '#bid_submit', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let shId = splitUrl[splitUrl.length - 2];
            let bid_price = $("#bid_price").val();

            if(bid_price.length == 0 || bid_price == "0")
                alert("구매 희망가를 입력해 주세요.");
            else {
                $.ajax({
                    type: 'POST',
                    url: '/shBid/' + shId + '/add',
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
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
                            location.href="/secondhand/" + shId + "/view";
                        }
                    }
                })
            }
        });

        $(document).on('click', '#update_bid', function() {
            let input_box = "<input type='text' id='new_bid' placeholder='희망가 입력'>"
            $(this).parent().children("#bid").before(input_box);
            $(this).parent().children("#bid").remove();

            let btn = "";
            btn += "<span id='update_bid_submit' style='margin-top: 5px;'>등록</span>"
                    + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px; margin-top: 5px;'>취소</span>";

            $(this).after(btn);
            $(this).prev().remove();
            $(this).remove();
        });

        $(document).on('click', '#update_bid_submit', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let shId = splitUrl[splitUrl.length - 2];
            let shBidId = $(this).parent().attr('id');
            let new_bid = $('#new_bid').val();

            if (new_bid.length == 0 || new_bid == "")
                alert("구매 희망가를 입력해 주세요.");
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
                            "price" : new_bid
                        }
                    ),
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        location.reload();
                    }
                })
            }
        });

        $(document).on('click', '#bid_accept', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let shId = splitUrl[splitUrl.length - 2];
            let bidId = $(this).parent().attr('id');
            let bidPrice = $(this).parent().data('price');
            let bidState = $(this).parent().data('state');

            $.ajax ({
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
                        location.href="/secondhand/" + shId + "/view";
                    }
                }
            })
        });

        $(document).on('click', '#delete_bid', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let shId = splitUrl[splitUrl.length - 2];
            let bidId = $(this).parent().attr('id');

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
                    location.href="/secondhand/" + shId + "/view";
                }
            })
        });

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>