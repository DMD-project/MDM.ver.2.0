<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>login</title>

    <style>
        body {
                background-color: #F9F1E7;
                text-align: center;
            }
        .logo {
                width: 80px;
                height: 80px;

                padding-top: 100px;
                padding-bottom: 20px;
        }
        .context {
                color: #C2C2C2;
                font-size: 15px;
        }
        .kakao {
                max-width: 25%;
                height: auto;

                padding-top: 10px;
                padding-bottom: 10px;
        }
    </style>

</head>
<body>

<img src="../images/logo.png" class="logo" />
<br/>
<span class="context">SNS 계정으로 간편 로그인</span>
</br>
<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=bbb0d5e603062dd02da05a9fe89b0c1e&redirect_uri=http://localhost:8080/kakao">
    <img src="../images/kakao_sync_large_wide.png" class="kakao" />
</a>


</body>
</html>
