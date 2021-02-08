<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />

    <title>Bank</title>
    <meta name="description" content="description of the site">
    <meta name="keywords" content="key 1, key 2, key 3…" />

    <meta name="google" content="notranslate">

    <!-- iOS Safari -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://use.typekit.net/blx4det.css">
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700;900&display=swap" rel="stylesheet">
    <link rel="icon" href="favicon.svg"><link href="resources/css/app.css" rel="stylesheet"></head>

<body>
<div class="container">
    <div class="grid">
        <div class="sidebar">

            <!-- head -->
            <div class="sidebar__head">
                <div class="head">
                    <div class="container-img"><img src="resources/img/content/logo.webp" alt=""></div>
                    <div class="text">
                        <div class="name">${sessionScope.get("user").getFirstName()} ${sessionScope.get("user").getLastName()} </div>
                        <div class="specialty">${sessionScope.get("user").getUserType()}</div>
                    </div>
                </div>
            </div>

            <!-- Menu -->
            <div class="sidebar__menu">
                <div class="title"><img src="resources/img/content/header/exchange-rates.svg" alt="">Exchange Rates</div>

                <a href='login.html' class="link">
                    <div class="icon"><img src="resources/img/content/exchange-rates/dollar.svg" alt=""></div>
                    <div class="title">Dollar</div>
                </a>

                <a class="link stat">
                    <div class="left">
                        <div class="icon"><img src="resources/img/content/exchange-rates/euro.svg" alt=""></div>
                        <div class="title">Euro</div>
                    </div>

                    <div class="right">
                        <span>21</span>
                    </div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/exchange-rates/yen.svg" alt=""></div>
                    <div class="title">Yen</div>
                </a>

                <div class="title"><img src="resources/img/content/menu/settings.svg" alt=""> Settings</div>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/languages.svg" alt=""></div>
                    <div class="title">Languages: EN</div>
                </a>

            </div>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='100'>
            <img src="resources/img/content/header/replenishment.svg" alt="">
            <a href="#">Replenishment</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='200'>
            <img src="resources/img/content/header/translations.svg" alt="">
            <a href="#">Translations</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='300'>
            <img src="resources/img/content/header/payments.svg" alt="">
            <a href="#">Payments</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='400'>
            <img src="resources/img/content/header/gg.svg" alt="">
            <a href="#">Socials</a>
        </div>

        <div class="content" data-aos="fade-up" data-aos-delay='500'>
            <div class="title">Transaction history</div>

            <div class="table overflow-table">
                <div class="item item-head">
                    <div class="id">ID</div>
                    <div class="descr">Description</div>
                    <div class="category">Type</div>
                    <div class="card">Card</div>
                    <div class="date">date</div>
                    <div class="amount">amount</div>
                </div>

                <c:forEach items="${creditCardPayments}" var="item">
                    <div class="item">
                        <div class="id"># 01</div>
                        <div class="descr">
                            <div class="img"><img src="resources/img/content/category/shopping.svg" alt=""></div> Aliexpress
                        </div>
                        <div class="category">Shopping</div>
                        <div class="card">**** 5344</div>
                        <div class="date">07.02.2021</div>
                        <div class="amount plus">+ 200$</div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</div>

<div class="bg-menu"></div>
<script src="resources/js/app.js"></script></body>

</html>
