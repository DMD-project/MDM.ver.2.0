<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>header</title>

    <script src="https://kit.fontawesome.com/0dff8da39e.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/3.0.1/js.cookie.min.js"></script> <!-- 외부 스크립트 불러오기 -->

    <style>
        .header {
            background-color: #F9F1E7;
            padding: 10px 0;
        }
        .navbar {
            width: 100%;
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
        .navbar_icons i {
            padding: 0px 7px;
        }
        .navbar_icons a:link, a:visited {
            color: black;
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
            <a href="/"><img src="../../images/m2dm.png" style="width: 200px; height: auto;"></a>
        </div>
        <ul class="navbar_menu">
            <li><a href="#"><b>커뮤니티</b></a></li>
            <li><a href="/product/list/view"><b>쇼핑</b></a></li>
            <li><a href="/gp/list/view"><b>공동구매</b></a></li>
            <li><a href="/secondhand/list/view"><b>중고거래</b></a></li>
        </ul>

        <ul class="navbar_icons">
            <a href="#"><i class="fa-solid fa-heart"></i></a>
            <a href="/cart/view"><i class="fa-solid fa-cart-shopping"></i></a>
            <a href="/mypage/view"><i class="fa-solid fa-user"></i></a>

        </ul>

        <ul class="navbar_login">
            <li>
                <span id="login_btn">

                    <button onclick='logout()'>로그아웃</button>
                </span>
            </li>
        </ul>
    </nav>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        var token = getCookie("access_token");

        let login_btn = "<button onclick='login()'>로그인</button>";
        let logout_btn = "<button onclick='logout()>로그아웃</button>";

        if (token == null)
            $("#login_btn").html(login_btn);
        else
            $("#logout_btn").html(logout_btn);

    });

    function login() {
        location.href = '/login';
    }

    function getCookie(name) {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.startsWith(name + '=')) {
                return cookie.substring(name.length + 1);
            }
        }
        return null;
    }

    function logout() {
         var token = getCookie("access_token");
         console.log("Token:", token);

        if (!token) {
            alert("로그인되어 있지 않습니다.");
            return;
        }

        var token = getCookie("access_token");
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/kakao/logout", true);
        xhr.setRequestHeader("Authorization", "Bearer " + token);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    Cookies.remove("access_token");
                    Cookies.remove("refresh_token");

                    alert("로그아웃되었습니다.");

                    location.reload();
                } else {
                    alert("로그아웃에 실패하였습니다.");
                }
            }
        };
        xhr.send();
    }
</script>
</body>
</html>
