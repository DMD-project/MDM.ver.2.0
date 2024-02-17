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

    <div><h5>총 <span id="count">product_count</span> 개</h5></div>

    <div class="sort">
        <div class="sort_type">
            <select id="sortSelect">
                <option value="newest" selected>최신순</option>
                <option value="lowprice">낮은 가격순</option>
                <option value="highprice">높은 가격순</option>
            </select>
        </div>
    </div>
    <div id="productList"></div>
</div>


<script>
    window.onload = function() {
        getProductList();
        getCount();
    };

     async function getProductList() {
         try {
             const response = await fetch('http://localhost:8080/product/list');
             const data = await response.json();
             const productListDiv = document.getElementById('productList');
            data.content.forEach(product => {
            const productItem = document.createElement('div');
            productItem.classList.add('product-item');
            productItem.innerHTML = '<h3>' + product.name + '</h3>' +
                                    '<p>' + product.content + '</p>' +
                                    '<p>가격: ' + product.price + '</p>';
            productListDiv.appendChild(productItem);
        });
         } catch (error) {
             console.error('Error fetching product list:', error);
         }
     }


    async function getCount() {
        try {
            const response = await fetch('http://localhost:8080/product/count');
            const data = await response.json();
            console.log(data.content);
            document.getElementById("count").innerHTML = data.content;
        } catch (error) {
            console.error('Error fetching product count:', error);
        }
    }
</script>


<%@ include file="includes/footer.jsp" %>



</body>
</html>
