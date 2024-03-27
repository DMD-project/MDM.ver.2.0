<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>Main</title>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <style>
        body {
            background-color: #F9F1E7;
        }
        .category_bar {
            background-color: #F9F1E7;
            text-align: center;
            padding: 10px 50px 10px 50px;
        }
        .category_bar button {
            background-color: #FFFFFF;
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
        .content_wrapper {
            background-color: #FFFFFF;
            padding: 30px 150px 50px 150px;
            margin-top: 20px;
        }
        .top_wrapper {
            padding: 0 50px;
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
            padding: 20px;
            margin: 1px;
        }
        a:link, a:visited {
            text-decoration: none;
            color: black;
        }
    </style>

</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="slick">
        <div class="photo_banner">
            <img src="../../images/banner/banner1.png" alt="배너_이미지_01"/>
        </div>
        <div class="photo_banner">
            <img src="../../images/banner/banner2.png" alt="배너_이미지_02"/>
        </div>
        <div class="photo_banner">
            <img src="../../images/banner/banner3.png" alt="배너_이미지_03"/>
        </div>
    </div>

    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <script>
        $('.slick').slick({
            autoplay: true,
            autoplaySpeed: 1500,
            arrows: false
        });
    </script>

    <div class="category_bar">
        <table style="text-align: center; font-size: 14px; margin: auto;">
            <tr>
                <td>
                    <button id="category_btn" value="">
                        <img src="../../images/category/category_btn_01.png">
                    </button>
                    <br/>전체
                </td>
                <td>
                    <button id="category_btn" value="FUR">
                        <img src="../../images/category/category_btn_02.png">
                    </button>
                    <br/>가구
                </td>
                <td>
                    <button id="category_btn" value="FAB">
                        <img src="../../images/category/category_btn_03.png">
                    </button>
                    <br/>패브릭
                </td>
                <td>
                    <button id="category_btn" value="AD">
                        <img src="../../images/category/category_btn_04.png">
                    </button>
                    <br/>가전/디지털
                </td>
                <td>
                    <button id="category_btn" value="STO">
                        <img src="../../images/category/category_btn_05.png">
                    </button>
                    <br/>수납/정리
                </td>
                <td>
                    <button id="category_btn" value="DEC">
                        <img src="../../images/category/category_btn_06.png">
                    </button>
                    <br/>소품
                </td>
                <td>
                    <button id="category_btn" value="LIT">
                        <img src="../../images/category/category_btn_07.png">
                    </button>
                    <br/>조명
                </td>
                <td>
                    <button id="category_btn" value="PLA">
                        <img src="../../images/category/category_btn_08.png">
                    </button>
                    <br/>식물
                </td>
            </tr>
        </table>
    </div>

    <div class="content_wrapper">
        <div class="top_wrapper">
            <span style="color: #616161; font-weight: bold;">상품 둘러보기</span>
            <a href="/product/list/view"><span style="float: right; color: #FF7500; font-weight: bold;">더보기</span></a>
        </div>

        <div id="product_list">

        </div>
    </div>

    <script>
        $(document).ready(function() {
            printProduct();
        });

        $(document).on('click', '#category_btn', function() {
            cateCode = $(this).val();
            location.href = "/product/list/view?cateCode=" + cateCode;
        });

        function printProduct(sortBy, cateCode) {
            $.ajax ({
                url: '/product/sort/category',
                data: {
                    "sortBy" : sortBy,
                    "cateCode" : cateCode
                },
                success: function(data) {
                    let product_info = "";

                    product_info += "<ul>";
                    for (let i = 0; i < 8; i++) {

                        product_info += "<li>"
                                        + "<a href='/product/" + data.content[i].id + "/view'>" + "<p>"
                                        + "<img src='../../images/product/" + data.content[i].imgUrl + "' style='width: 100%; height: 214px;'>" + "<br/>"
                                        + "<span>" + data.content[i].name + "</span>" + "<br/>"
                                        + "<span><b>" + data.content[i].price + "원</b></span>"
                                        + "</a>"
                                        + "</li>";

                        if (i % 4 == 3) {
                            product_info += "</ul>";
                            product_info += "<br/><ul>";
                        }
                    }
                    $("#product_list").empty();
                    $("#product_list").html(product_info);
                }
            })
        }

    </script>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>
