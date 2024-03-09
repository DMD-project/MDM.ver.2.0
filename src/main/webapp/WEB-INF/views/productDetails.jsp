<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Product Details</title>

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
        .product_img {
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

        #add_cart {
              background-color: #F9F1E7;
              color: #FF7500;
              font-size: 20px;
              text-align: center;

              width: 240px;
              height: 60px;

              border: 1px solid #FF7500;
              border-radius: 10px;
        }
        #order {
              background-color: #FF7500;
              color: white;
              font-size: 20px;
              text-align: center;

              width: 250px;
              height: 60px;

              border: none;
              border-radius: 10px;
              margin-left: 5%;
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
        .review_avg {
            background-color: #FFF6EA;

            width: 600px;
            height: 80px;

            border-radius: 10px;
            padding: 30px 50px;
            margin-top: 10px;
            margin-bottom: 30px;
        }
        textarea {
            border: 1px solid #B0B0B0;
            margin: 10px 0;
        }
        .ratingStar, .update_ratingStar {
            display: inline-block;
            width: 20px;
            height: 20px;
            color: transparent;
            text-shadow: 0 0 0 #F0F0F0;
            font-size: 1.5em;
            box-sizing: border-box;
            cursor: pointer;
        }
        .ratingStar .star:hover, .update_ratingStar .star:hover {
            text-shadow: 0 0 0 #CCC;
        }
        .ratingStar .star.on, .update_ratingStar .star.on {
            text-shadow: 0 0 0 #FFBC00;
        }
        #review_submit {
            background-color: #FF7500;
            color: #FFFFFF;

            padding: 6px 13px;
            border: none;
            border-radius: 10px;
        }
        .review_sortBy span:hover {
            color: #FF7500;
        }
        .review_box {
            width:650px;
            height: 80px;
            padding: 10px 20px 20px 20px;
            margin-bottom: 20px;

            border: 1px solid #EE842A;
            border-radius: 10px;
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
        .review_avg_star_real {
            width: 181px;
            height: 40px;
            display: inline-block;
            overflow: hidden;
            background: url(../../images/star.png) no-repeat;
        }
        .review_avg_star_background {
            width: 181px;
            height: 40px;
            display: inline-block;
            overflow: hidden;
            background: url(../../images/star-background.png) no-repeat;
        }
        .review_star {
            color: transparent;
            text-shadow: 0 0 0 #F0F0F0;
        }
        .review_star_on {
            color: transparent;
            text-shadow: 0 0 0 #FFBC00;
        }
        button, .review_sortBy span,
        #update_review, #update_review_submit,
        #delete_review {
            cursor: pointer;
        }
        #update_review_submit {
            float: right;
            background-color: #FFEDDE;
            color: #FF7500;
            font-size: 14px;
            padding: 2px 5px;
            border: 1px solid #FF7500;
            border-radius: 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="sample">
        <div class="product_img">
            <img id="prod_img" src="" alt="">
        </div>
        <div class="product_info_wrapper">
            <div class="product_info">
                <h1><span id="prod_name">prod_name</span></h1>
                <div style="height: 100px;"></div>
                <div>
                    <span style="color: #616161; font-size: 15px;">배송 </span>
                    <span style="padding-left: 10px;"><b>3000원</b></span>
                    <span style="padding-left: 5px;">(50,000원 이상 구매시 무료배송)</span>
                </div>
                <div style="margin-top: 5px;">
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
                    <span id="prod_price" style="font-size: 30px; padding-right: 5px; font-weight: bold;"><b>prod_price</b></span>
                    <span>원</span>
                </div>
            </div>

            <div class="button_wrapper">
                <button id="add_cart"><b>장바구니</b></button>
                <button id="order"><b>결제하기</b></button>
            </div>
        </div>
    </div>

    <div class="info_nav_bar">
        <div class="info_nav_bar_border">상품 정보</div>
        <div>리뷰</div>
    </div>

    <div style="background-color: #FFFFFF; padding: 50px 160px;">
        <span id="prod_content"></span>
    </div>


    <div class="info_nav_bar">
        <div>상품 정보</div>
        <div class="info_nav_bar_border">리뷰</div>
    </div>

    <div style="background-color: white; padding: 50px 160px;">
        <div class="review_wrapper" style="width: 700px; margin: auto;">
            <div><span style="color: #616161;"><b>후기</span><span id="review_count" style="color: #FF7500; padding-left: 5px;">review_count</span></b></div>
            <div class="review_avg">
                <b><div id="review_avg" style="color: #FF7500; font-size: 30px; text-align: center;">review_avg</div></b>
                <div class="stars" style="position: relative; font-size: 25px; width: 180px; margin:auto;">
                    <span class="review_avg_star_background">
                         <span class="review_avg_star_real" style="width: 100%"></span>
                    </span>
                </div>
            </div>

            <div style="margin-bottom: 50px;">
                <span style="color: #616161;"><b>후기 작성</b></span>
                <div class="ratingStar" style="width: 300px; margin: 5px;">
                    <span class="star" data-value="1">⭐</span>
                    <span class="star" data-value="2">⭐</span>
                    <span class="star" data-value="3">⭐</span>
                    <span class="star" data-value="4">⭐</span>
                    <span class="star" data-value="5">⭐</span>
                </div>
                <textarea id="textarea" style="width:650px; height: 100px; padding: 20px;" placeholder="후기를 작성해주세요."></textarea>
                <button id="review_submit" style="float: right; margin-right: 10px;">등록</button>
            </div>

            <div>
                <div class="review_sortBy" style="margin-left: 480px; margin-bottom: 10px;">
                    <span data-value="newest" style="padding-right: 10px; font-size: 15px;">최신순</span>
                    <span data-value="highstar" style="padding-right: 10px; font-size: 15px;">평점 높은순</span>
                    <span data-value="lowstar" style="font-size: 15px;">평점 낮은순</span>
                </div>

                <div id="review_list">

                </div>
            </div>
        </div>
    </div>

    <%@ include file="includes/footer.jsp" %>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            printProductDetails();
        });

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

            $("#count_value").attr('value', count_value);
        });

        $(document).on('click', '#add_cart', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];
            let count = $('#count_value').val();
            $.ajax ({
                type: 'POST',
                url: '/cartItem/add/' + prodId + '/' + count,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);

                    if (!token) {
                        alert("로그인이 필요합니다.");
                        window.location.href='/login';
                    }
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(result) {
                    console.log(result);
                    alert('장바구니에 상품이 추가되었습니다.');
                }

            });
        });

        $(document).on('click', '#order', function() {
            var token = getCookie("access_token");
            if (!token) {
                alert('로그인이 필요합니다.');
                location.href = "/login";
            } else {
                let url = window.location.href;
                let splitUrl = url.split("/");

                let prodId = splitUrl[splitUrl.length - 2];
                let count = $('#count_value').val();

                location.href='/order/view?from=product&prodId=' + prodId + '&count=' + count;
            }
        });

        let star = 0;
        $(document).on('click', '.ratingStar span', function() {
            $(this).parent().children('span').removeClass('on');
            $(this).addClass('on').prevAll('span').addClass('on');

            star = $(this).data('value');
        });

        let update_star = 0;
        $(document).on('click', '.update_ratingStar span', function() {
            $(this).parent().children('span').removeClass('on');
            $(this).addClass('on').prevAll('span').addClass('on');

            update_star = $(this).data('value');
        });

        $(document).on('click', '#review_submit', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];
            let today = new Date();
            let content = $('#textarea').val();

            if (star == 0) {
                alert('별점을 체크해 주세요.');
            } else if (content == "") {
                alert('후기를 작성해 주세요.');
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/review/' + prodId + '/add',
                    beforeSend: function(xhr) {
                        var token = getCookie("access_token");
                        console.log("Token:", token);

                        if (!token) {
                            alert("로그인이 필요합니다.");
                            window.location.href='/login';
                        }
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    },
                    contentType: 'application/json',
                    data: JSON.stringify(
                        {
                            "prodId" : prodId,
                            "star" : star,
                            "date" : today,
                            "content" : content
                        }
                    ),
                    success: function(result) {
                        $(".ratingStar").children().removeClass('on');
                        $('#textarea').val('');
                        printProductDetails();
                    }
                });
            }

        });

        $(document).on('click', '.review_sortBy span', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];
            let sortBy = $(this).data('value');
            $.ajax ({
                url: '/review/' + prodId + '/sort',
                data: {
                    "sortBy" : sortBy
                },
                success: function(data) {
                    console.log(data);

                    let review_arr = data.content;
                    let review_box = "";
                    $.each(review_arr, function(idx, value) {
                        review_box += "<div class='review_box' value='" + value.id +"'>"
                                        + "<div id='delete_review' style='float: right; margin-left: 10px; color: #FF7500;'><i class='fa-solid fa-xmark'></i></div>"
                                        + "<div id='update_review' style='float: right; font-size: 14px; color: #FF7500;'>수정</div>"
                                        + "<div>" + printStar(value.star) + "</div>"
                                        + "<div style='color: #B0B0B0; font-size: 13px;'>작성일자 " + value.date + "</div>"
                                        + "<div>" + value.content + "</div>"
                                    + "</div>";
                    });

                    $("#review_list").empty();
                    $("#review_list").html(review_box);
                }
            });
        });

        function printProductDetails() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];

            $.ajax ({
                url: '/product/' + prodId,
                success: function(data) {
                    let prod_imgUrl = data.content.imgUrl;
                    let prod_name = data.content.name;
                    let prod_price = data.content.price;
                    let prod_content = data.content.content;

                    $("#prod_img").attr('src', prod_imgUrl);
                    $("#prod_img").attr('alt', prod_imgUrl);
                    $("#prod_name").html(prod_name);
                    $("#prod_price").html(prod_price);
                    $("#prod_content").html(prod_content);

                    let review_count = data.content.reviewCnt;
                    let review_avg = parseFloat(data.content.reviewStarAvg).toFixed(1);

                    let review_star_avg = ((review_avg / 5) * 100);

                    $("#review_count").html(review_count);
                    $("#review_avg").html(review_avg);
                    $(".review_avg_star_real").css("width", review_star_avg + "%");


                    let review_arr = data.content.reviewList;
                    let review_box = "";
                    $.each(review_arr, function(idx, value) {
                        console.log(value);
                        review_box += "<div class='review_box' data-value='" + value.id +"'>"
                                        + "<div id='delete_review' style='float: right; margin-left: 10px; color: #FF7500;'><i class='fa-solid fa-xmark'></i></div>"
                                        + "<div id='update_review' style='float: right; font-size: 14px; color: #FF7500;'>수정</div>"
                                        + "<div>" + printStar(value.star) + "</div>"
                                        + "<div style='color: #B0B0B0; font-size: 13px; margin-top: 5px;'>작성일자 " + value.date + "</div>"
                                        + "<div style='margin-top: 10px;'>" + value.content + "</div>"
                                    + "</div>";
                    });

                    $("#review_list").html(review_box);
                }
            });
        }

        $(document).on('click', '#update_review', function() {
            $(this).parent().css('height', '115px');

            let update_review_box = "";
            update_review_box += "<div class='update_ratingStar' style='float: left; width:650px; font-size: 15px;'>"
                                    + "<span class='star' data-value='1'>⭐</span>"
                                    + "<span class='star' data-value='2'>⭐</span>"
                                    + "<span class='star' data-value='3'>⭐</span>"
                                    + "<span class='star' data-value='4'>⭐</span>"
                                    + "<span class='star' data-value='5'>⭐</span>"
                                + "</div>"
                                + "<textarea id='update_review_content' style='width: 100%; height: 60px; margin: 10px 0 5px 0;'></textarea>"
                                + "<span id='update_review_submit'>등록</span>"
                                + "<span id='cancel' style='float: right;color: #FF7500; font-size: 14px; padding: 5px 0px; margin-right: 10px;'>취소</span>";

            $(this).parent().html(update_review_box);
        });

        $(document).on('click', '#cancel', function() {
            location.reload();
        });

        $(document).on('click', '#update_review_submit', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];
            let reviewId = $(this).parent().data('value');
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

                        if (!token) {
                            alert("로그인이 필요합니다.");
                        }
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
                        if (data.statusCode == 405)
                            alert('해당 리뷰를 수정할 수 없는 사용자입니다.');
                        else
                            printProductDetails();
                    }
                });
            }
        });

        $(document).on('click', '#delete_review', function() {
            let url = window.location.href;
            let splitUrl = url.split("/");

            let prodId = splitUrl[splitUrl.length - 2];
            let reviewId = $(this).parent().data('value');

            $.ajax ({
                type: 'DELETE',
                url: '/review/' + prodId + '/delete/' + reviewId,
                beforeSend: function(xhr) {
                    var token = getCookie("access_token");
                    console.log("Token:", token);

                    if (!token) {
                        alert("로그인이 필요합니다.");
                    }
                    xhr.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function(data) {
                    if (data.statusCode == 405)
                        alert('해당 리뷰를 삭제할 수 없는 사용자입니다.');
                    else
                        printProductDetails();
                }
            })
        });

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

    </script>

</body>
</html>