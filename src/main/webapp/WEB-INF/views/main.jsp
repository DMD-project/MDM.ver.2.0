<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
    <title>Main</title>

    <style>
        body {
            background-color: #F9F1E7;
        }
    </style>

    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

    <style>
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

    ${param.jwtAccess}
    <%= request.getParameter("jwtAccess") %>
    <%= request.getAttribute("jwtAccess") %>

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

    <script>
        $('.slick').slick({
            autoplay: true,
            autoplaySpeed: 2000,
        });
    </script>


    <div class="category_bar">
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

    <div class="shop_banner"></div>
    <div ></div>

    <%@ include file="includes/footer.jsp" %>

</body>
</html>
