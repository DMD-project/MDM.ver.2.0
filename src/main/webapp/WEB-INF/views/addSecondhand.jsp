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

            margin-top: 50px;
            margin-bottom: 70px;
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

        .secondhand_category, .secondhand_name, .secondhand_price, .secondhand_detail {
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
                <div class="secondhand_category">
                    <span style="color: #868686;">카테고리</span><br/>
                    <select id="categorySelect" style="width: auto; padding: 5px 20px 5px 5px;">
                        <option value="" selected>카테고리 선택</option>
                        <option value="FUR">가구</option>
                        <option value="FAB">패브릭</option>
                        <option value="AD">가전/디지털</option>
                        <option value="STO">수납/정리</option>
                        <option value="DEC">소품</option>
                        <option value="LIT">조명</option>
                        <option value="PLA">식물</option>
                    </select>
                </div>
                <div class="secondhand_name">
                    <span style="color: #868686;">상품명</span><br/>
                    <input type="text" id="name_value" placeholder="상품명을 입력해주세요.">
                </div>
                <div class="secondhand_price">
                    <span style="color: #868686;">희망 거래 가격</span><br/>
                    <input type="text" id="price_value" placeholder="희망 거래 가격을 입력해주세요.">
                </div>
                <div class="secondhand_detail">
                    <span style="color: #868686;">상세 정보</span><br/>
                    <textarea cols="40" id="detail_value" rows="8" style="height: 100px; padding: 15px 10px 10px 10px;" placeholder="신뢰할 수 있는 구매를 위해 상품에 대한 정보를 자세히 적어주세요."></textarea>
                </div>

            </div>

            <div class="content_right">
                <button class="submit" id="sh_submit"><b>상품 등록</b></button>
            </div>
        </div>

        <%@ include file="includes/footer.jsp" %>

        <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
        <script>

            $(document).on('click', '#sh_submit', function() {
                let category = $("option:selected").val();
                let name = $("#name_value").val();
                let price = $("#price_value").val();
                let detail = $("#detail_value").val();

                if (category == "") {
                    alert("카테고리를 선택해 주세요.");
                }
                else if (name.length == 0) {
                    alert("등록할 상품명을 입력해 주세요.");
                }
                else if (price.length == 0 || price == "") {
                    alert("희망 거래 가격을 입력해 주세요.");
                }
                else if(detail.length == 0) {
                    alert("상품 정보를 적어주세요.");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: '/secondhand/add',
                        beforeSend: function(xhr) {
                            var token = getCookie("access_token");
                            console.log("Token:", token);

                            if (!token) {
                                alert("로그인이 필요합니다.");
                                window.location.href='/login';
                            }
                            xhr.setRequestHeader("Authorization", "Bearer " + token);
                        },
                        contentType : 'application/json',
                        data : JSON.stringify (
                            {
                                "name" : name,
                                "cateCode" : category,
                                "price" : price,
                                "imgUrl" : "http",
                                "content" : detail
                            }
                        ),
                        success: function(data) {
                            if(data.statusCode != 200) {
                                alert(data.message);
                                return;
                            } else {
                                alert("상품이 등록되었습니다.");
                                location.href="http://localhost:8080/secondhand/" + data.content + "/view";
                            }
                        }
                    })
                }
            });

        </script>

</body>
</html>