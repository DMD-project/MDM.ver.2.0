<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Add Secondhand form</title>

    <style>
        body {
            background-color: #F9F1E7;
        }
        span {
            padding-left: 5px;
        }
        .content_wrapper {
            display : flex;
            justify-content: center;
        }
        .content_left {
            width: 600px;
            padding: 20px;
        }
        .content_right {
            width: 400px;

            text-align: center;
            margin-left: 50px;
            padding: 20px;
        }

        .secondhand_prod_category, .secondhand_prod_name, .secondhand_prod_price, .secondhand_prod_detail {
            margin-top: 12px;
        }
        input, textarea, select {
            border: 1px solid #C2C2C2;

            width: 100%;
            height: 40px;
            padding-left: 10px;
            margin-top: 7px;
        }
        input::placeholder, textarea::placeholder {
            color: #C2C2C2;
        }
        select {
            border-color: #C2C2C2;
        }

        .submit {
              background-color: #FF7500;
              color: #F9F1E7;
              font-size: 20px;
              text-align: center;

              width: 80%;
              height: 60px;

              border: 1px solid #FF7500;
              border-radius: 10px;
        }

    </style>
</head>
<body>

    <%@ include file="includes/header.jsp" %>

    <h2 style="color: #666666; text-align: center; margin-bottom: 25px;">중고거래 상품 등록</h2>
        <div class="content_wrapper">

            <div class="content_left">
                <span style="font-size: 17px; color: #616161;"><b>상품 정보</b></span>
                <hr style="border: 1px solid #FFAB64;">
                <div class="secondhand_prod_category">
                    <span style="color: #868686;">카테고리</span><br/>
                    <select style="width: auto; padding: 5px 20px 5px 5px;">
                        <option value="null" selected>카테고리 선택</option>
                        <option value="FUR">가구</option>
                        <option value="FAB">패브릭</option>
                        <option value="AD">가전/디지털</option>
                        <option value="STO">수납/정리</option>
                        <option value="DEC">소품</option>
                        <option value="LIT">조명</option>
                        <option value="PLA">식물</option>
                    </select>
                </div>
                <div class="secondhand_prod_name">
                    <span style="color: #868686;">상품명</span><br/>
                    <input type="text" placeholder="상품명을 입력해주세요.">
                </div>
                <div class="secondhand_prod_price">
                    <span style="color: #868686;">희망 거래 가격</span><br/>
                    <input type="text" placeholder="희망 거래 가격을 입력해주세요.">
                </div>
                <div class="secondhand_prod_detail">
                    <span style="color: #868686;">상세 정보</span><br/>
                    <textarea cols="40" rows="8" style="height: 100px; padding: 15px 10px 10px 10px;" placeholder="신뢰할 수 있는 구매를 위해 상품에 대한 정보를 자세히 적어주세요."></textarea>
                </div>

            </div>

            <div class="content_right">
                <button class="submit"><b>상품 등록</b></button>
            </div>
        </div>

    <%@ include file="includes/footer.jsp" %>
</body>
</html>