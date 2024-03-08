<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
    <title>Main</title>

    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>

    <style>
        body {
            background-color: #F9F1E7;
        }
        .photo_banner {
            background-color: #FFFFFF;
            height: 400px;
        }
        .slick-arrow {
                    position: absolute;
                    top: 50%;
                    transform: translateY(-50%);
                    z-index: 5
        }
        .slick-prev {    left: 0;}
        .slick-next {    right: 0;}
    </style>

    <style>
        .category_bar {
            background-color: #F9F1E7;
            text-align: center;

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
    </style>

</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <div class="slick">
        <div class="photo_banner">
            <img src="" alt="배너_이미지_01"/>
        </div>
        <div class="photo_banner">
            <img src="" alt="배너_이미지_02"/>
        </div>
        <div class="photo_banner">
            <img src="" alt="배너_이미지_03"/>
        </div>
    </div>

    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

    <script>
        $('.slick').slick({
            autoplay: true,
            autoplaySpeed: 2000,
        });
    </script>


    <div class="category_bar">
        <table style="text-align: center; font-size: 14px; margin: auto;">
            <tr>
                <td>
                    <button id="category_btn" value="">
                        <img src="../../images/category_btn_01.png">
                    </button>
                    <br/>전체
                </td>
                <td>
                    <button id="category_btn" value="FUR">
                        <img src="../../images/category_btn_02.png">
                    </button>
                    <br/>가구
                </td>
                <td>
                    <button id="category_btn" value="FAB">
                        <img src="../../images/category_btn_03.png">
                    </button>
                    <br/>패브릭
                </td>
                <td>
                    <button id="category_btn" value="AD">
                        <img src="../../images/category_btn_04.png">
                    </button>
                    <br/>가전/디지털
                </td>
                <td>
                    <button id="category_btn" value="STO">
                        <img src="../../images/category_btn_05.png">
                    </button>
                    <br/>수납/정리
                </td>
                <td>
                    <button id="category_btn" value="DEC">
                        <img src="../../images/category_btn_06.png">
                    </button>
                    <br/>소품
                </td>
                <td>
                    <button id="category_btn" value="LIT">
                        <img src="../../images/category_btn_07.png">
                    </button>
                    <br/>조명
                </td>
                <td>
                    <button id="category_btn" value="PLA">
                        <img src="../../images/category_btn_08.png">
                    </button>
                    <br/>식물
                </td>
            </tr>
        </table>
    </div>

    <div class="shop_banner"></div>
    <div></div>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>
