<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Kakao Login Test</title>
    <style>
        body {
            background-color: #F9F1E7;
            text-align: center;
        }
        .gray {
            color: gray;
        }
        .logo {
             max-width: 10%;
             height: auto;
        }
        .kakao {
             max-width: 30%;
             height: auto;
        }
    </style>
</head>
<body>
    <div>
        <img src="../images/logo.png" class="logo" />
        <br/>
        <span class="gray">SNS 계정으로 간편 로그인</span>
        <br/>
        <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=bbb0d5e603062dd02da05a9fe89b0c1e&redirect_uri=http://localhost:8080/kakao">
            <img src="../images/kakao_sync_large_wide.png" class="kakao" />
        </a>
    </div>
</body>
</html>
