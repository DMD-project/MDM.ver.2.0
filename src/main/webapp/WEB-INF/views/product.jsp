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
    </style>

    <style>
        .top_wrapper {
            padding-bottom: 50px;
        }
        .count {
            padding-left: 50px;
         }
         .sort {
            padding-right: 50px;
        }
        #product_list ul {
            list-style: none;
            width: 1032px;

            display: flex;
            flex-flow: wrap;

            margin: auto;
            padding: 0;
        }
        #product_list li {
            list-style: none;
            text-align: right;

            width: 214px;

            border: 1px solid;
            padding: 20px;
            margin: 1px;
        }

    </style>

</head>

<body>

<%@ include file="includes/header.jsp" %>

<div class="category_bar">
    <div><h3>쇼핑</h3></div>

    <div class="search_bar">
        <form action="<c:url value='/product/search'/>" method="GET">
            <input type="text" name="keyword" placeholder="검색어를 입력하세요." />
        </form>
    </div>

    <button id="category_btn_01" onclick="location.href='<c:url value='/product/list'/>'">
        <img src="../../images/category_btn_01.png"><br/>전체
    </button>
    <button id="category_btn_02" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='FUR'/></c:url>'">
        <img src="../../images/category_btn_02.png"><br/>가구
    </button>
    <button id="category_btn_03" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='FAB'/></c:url>'">
        <img src="../../images/category_btn_03.png"><br/>패브릭
    </button>
    <button id="category_btn_04" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='AD'/></c:url>'">
        <img src="../../images/category_btn_04.png"><br/>가전/디지털
    </button>
    <button id="category_btn_05" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='STO'/></c:url>'">
        <img src="../../images/category_btn_05.png"><br/>수납/정리
    </button>
    <button id="category_btn_06" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='DEC'/></c:url>'">
        <img src="../../images/category_btn_06.png"><br/>소품
    </button>
    <button id="category_btn_07" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='LIT'/></c:url>'">
        <img src="../../images/category_btn_07.png"><br/>조명
    </button>
    <button id="category_btn_08" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='PLA'/></c:url>'">
        <img src="../../images/category_btn_08.png"><br/>식물
    </button>
</div>


<span id="test"></span>

<div class="content_wrapper">
    <div class="top_wrapper">
        <div class="count" style="float: left;"><b>총<span id="count" style="padding-left: 8px; padding-right: 3px;">product_count</span>개</b></div>

        <div class="sort" style="float: right;">
            <div class="sort_type">
                <select id="sortSelect">
                    <option value="newest" selected>최신순</option>
                    <option value="lowprice">낮은 가격순</option>
                    <option value="highprice">높은 가격순</option>
                </select>
            </div>
        </div>
    </div>

    <div id="product_list">
    </div>

</div>


    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            $.ajax ({
                url: 'http://localhost:8080/product/list',
                success: function(data) {

                    console.log(data);
                    console.log(data.content.length);
                    document.getElementById("count").innerHTML = data.content.length;

                    let product_info = "";

                    product_info += "<ul>";

                    for (let i = 0; i < data.content.length; i++) {

                        product_info += "<li>"
                                        + "<img src='" + data.content[i].imgUrl + "' style='width: 100%; height: 214px;'>" + "<br/>"
                                        + "<span>" + data.content[i].name + "</span>" + "<br/>"
                                        + "<span><b>" + data.content[i].price + "원</b></span>"
                                        + "</li>";

                        if (i % 4 == 3) {

                            product_info += "</ul>";
                            product_info += "<br/><ul>";
                        }

                    }

                    $("#product_list").html(product_info);


                }
            });
        });
    </script>


<%@ include file="includes/footer.jsp" %>



</body>
</html>
