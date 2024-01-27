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

    <style>
        .banner {
            background-color: #FFFFFF;

            height: 400px;
        }
    </style>

    <style>
        .category_bar {
            background-color: #F9F1E7;
            text-align: center;

            height: 100px;
            padding: 10px 50px 10px 50px;
        }
        .category_bar button {
            margin: 30px;
        }
    </style>

</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="banner"></div>


    <div class="category_bar">
            <button id="category_btn_01" onclick="location.href='<c:url value='/product/list'/>'">
                <img src="01"><br/>전체
            </button>
            <button id="category_btn_02" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='FUR'/></c:url>'">
                <img src="01"><br/>가구
            </button>
            <button id="category_btn_03" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='FAB'/></c:url>'">
                <img src="01"><br/>패브릭
            </button>
            <button id="category_btn_04" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='AD'/></c:url>'">
                <img src="01"><br/>가전/디지털
            </button>
            <button id="category_btn_05" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='STO'/></c:url>'">
                <img src="01"><br/>수납/정리
            </button>
            <button id="category_btn_06" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='DEC'/></c:url>'">
                <img src="01"><br/>소품
            </button>
            <button id="category_btn_07" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='LIT'/></c:url>'">
                <img src="01"><br/>조명
            </button>
            <button id="category_btn_08" onclick="location.href='<c:url value='/product/sort'><c:param name='cateCode' value='PLA'/></c:url>'">
                <img src="01"><br/>식물
            </button>
        </div>
</body>
</html>