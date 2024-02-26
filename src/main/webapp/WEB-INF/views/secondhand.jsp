<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>All product</title>

    <style>
        body {
            background-color: #F9F1E7;
        }
    </style>

    <style>
        .category_bar {
            background-color: #F9F1E7;
            text-align: center;

            height: 250px;

            padding: 10px 50px 10px 50px;
        }
        .category_bar button {
                    background-color: white;

                    width: 90px;
                    height: 90px;

                    border: none;
                    border-radius: 50%;
                    margin: 20px;
        }
        .category_bar img {
            width: 50px;
            height: 50px;
        }

        .search_bar input {
            width: 50%;
            height: 40px;

            border: none;
            border-radius: 25px;

            padding-left: 15px;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .content_wrapper {
            background-color: #FFFFFF;

            padding-top: 30px;
            padding-left: 150px;
            padding-right: 150px;
        }

        .addSecondhand {
            background-color: #FF7500;
            color: white;
            font-size: 13px;
            text-align: center;

            width: 80px;
            height: 25px;

            border: none;
            border-radius: 10px;
        }
    </style>

</head>

<body>

<%@ include file="includes/header.jsp" %>

<div class="category_bar">
    <div><h3>중고거래</h3></div>

    <div class="search_bar">
        <input type="text" id="keyword" placeholder="검색어를 입력하세요." />
    </div>

    <button id="category_btn" value="">
        <img src="../../images/category_btn_01.png"><br/>전체
    </button>
    <button id="category_btn" value="FUR">
        <img src="../../images/category_btn_02.png"><br/>가구
    </button>
    <button id="category_btn" value="FAB">
        <img src="../../images/category_btn_03.png"><br/>패브릭
    </button>
    <button id="category_btn" value="AD">
        <img src="../../images/category_btn_04.png"><br/>가전/디지털
    </button>
    <button id="category_btn" value="STO">
        <img src="../../images/category_btn_05.png"><br/>수납/정리
    </button>
    <button id="category_btn" value="DEC">
        <img src="../../images/category_btn_06.png"><br/>소품
    </button>
    <button id="category_btn" value="LIT">
        <img src="../../images/category_btn_07.png"><br/>조명
    </button>
    <button id="category_btn" value="PLA">
        <img src="../../images/category_btn_08.png"><br/>식물
    </button>
</div>


${data.content[1].name}
<span id="test"></span>

<div class="content_wrapper">

    <div class="sort">
        <div class="sort_type">
            <select id="sortSelect">
                <option value="newest" selected>최신순</option>
                <option value="lowprice">낮은 가격순</option>
                <option value="highprice">높은 가격순</option>
            </select>
        </div>
    </div>

    <button class="addSecondhand" onclick="">상품 등록</button>

    <div id="secondhand_list">

</div>



<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        printSecondHand();

        $("#keyword").keydown(function(e) {
            if(e.keyCode == 13) {
                let keyword = $(this).val();
                console.log(keyword);

                if (keyword == ""){
                    alert('상품명을 입력해주세요.');
                } else {
                    $.ajax({
                        url: '/secondhand/search/' + keyword,
                        data: {
                            "keyword" : keyword
                        },
                        success: function(data) {
                            if(data.content.length == 0) {
                                alert('존재하지 않는 상품입니다.');
                            } else {
                                console.log(data);
                                $("#count").html(data.content.length);

                                let secondhand_info = "";
                                secondhand_info += "<ul>";

                                for(let i = 0; i < data.content.length; i++) {
                                    secondhand_info += "<li>"
                                                    + "<a href='/secondhand/" + data.content[i].id + "/view'>" + "<p>"
                                                    + "<span>" + data.content[i].name + "</span>" + "<br/>"
                                                    + "<span><b>" + data.content[i].price + "원</b></span>"
                                                    + "</a>"
                                                    + "</li>";

                                    if (i % 4 == 3) {
                                        product_info += "</ul>";
                                        product_info += "<br/><ul>";
                                    }
                                }
                                $("#secondhand_list").empty();
                                $("#secondhand_list").html(secondhand_info);
                            }
                        }
                    });
                }
            }
        });
    });

    $(document).on('click', '#category_btn', function() {
        let cateCode = $(this).val();
        console.log(cateCode);

        let sortBy = $("option:selected", this).val();

        printSecondHand(sortBy, cateCode);
    });

    $("#sortSelect").change(function() {
        let sortBy = $("option:selected", this).val();
        printSecondHand(sortBy);
    });

    function printSecondHand(sortBy, cateCode) {
        $.ajax ({
            url: '/secondhand/sort/category',
            data: {
                "sortBy" : sortBy,
                "cateCode" : cateCode
            },
            success: function(data) {
                console.log(data);
                $("#count").html(data.content.length);

                let secondhand_info = "";

                secondhand_info += "<ul>";
                for(let i = 0; i < data.content.length; i++) {
                    secondhand_info += "<li>"
                                        + "<a href='/secondhand/" + data.content[i].id + "/view'>" + "<p>"
                                        + "<span>" + data.content[i].name + "</span>" + "<br/>"
                                        + "<span><b>" + data.content[i].price + "원</b></span>"
                                        + "</a>"
                                        + "</li>";

                    let secondhand_state = data.content[i].state;

                    if(i % 4 == 3) {
                        secondhand_info += "</ul>";
                        secondhand_info += "<br/><ul>";
                    }
                }
                $("secondhand_list").empty();
                $("#secondhand_list").html(secondhand_info);

            }
        });
    }

</script>

<%@ include file="includes/footer.jsp" %>


</body>
</html>
