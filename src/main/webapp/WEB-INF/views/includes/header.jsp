<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>header</title>

    <style>
        .header {
            background-color: #F9F1E7;
        }

        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: var(--background-color);

        }

        .navbar_logo {
            display: flex;
            font-size: 35px;

            padding-left: 15px;
            padding-right: 30px;
        }
        .navbar_logo a:link {
            text-decoration: none;
        }
        .navbar_logo a:link {
            color: black;
       }
       .navbar_logo a:visited {
            color: black;
       }

        .navbar_menu {
          list-style: none;
          display: flex;
          margin: 0;
          padding-left: 0;
        }
        .navbar_menu li {
          padding: 0px 20px;
        }

       .navbar_menu a {
            text-decoration: none;
       }
       .navbar_menu a:link {
            color: black;
       }
       .navbar_menu a:visited {
            color: black;
       }
       .navbar_menu a:hover {
            color: #FF7500;
       }

        .navbar_search {
            list-style: none;

            padding-left: 100px;
        }
        .navbar_search input {
            width: 300px;
            height: 30px;

            border: none;
            border-radius: 15px;

            padding-left: 15px;
        }

        .navbar_icons {
          list-style: none;
          display: flex;
          margin: 0;
          padding-left: 400px;
        }
        .navbar_icons li {
            padding: 0px 10px;
        }

        .navbar_login {
            list-style: none;
            display: flex;
        }
        .navbar_login li {
            padding-right: 50px;
        }
        .navbar_login button {
            background-color: #FF7500;
            color: white;
            font-size: 15px;
            text-align: center;

            width: 80px;
            height: 30px;

            border: none;
            border-radius: 10px;
        }


    </style>
</head>

<body>

<div class="header">
    <nav class="navbar">

        <div class="navbar_logo">
            <a href="#"><b>&nbsp;m2dm</b></a>
        </div>

        <ul class="navbar_menu">
            <li><a href="#"><b>커뮤니티</b></a></li>
            <li><a href="/product/list"><b>쇼핑</b></a></li>
            <li><a href="/gp/list"><b>공동구매</b></a></li>
            <li><a href="/secondhand/list"><b>중고거래</b></a></li>
        </ul>



        <ul class="navbar_icons">
            <li>관심목록</li>
            <li>장바구니</li>
            <li>마이페이지</li>

        </ul>

        <ul class="navbar_login">
            <li>
                <button onclick="location.href='/login'">로그인</button>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>
