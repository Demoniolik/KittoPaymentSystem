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

        <!-- Sidebar -->
        <div class="sidebar">

            <!-- head -->
            <div class="sidebar__head">
                <div class="head">
                    <div class="container-img"><img src="resources/img/content/menu/logo.webp" alt=""></div>
                    <div class="text">
                        <div class="name">${user.getFirstName()} ${user.getLastName()}</div>
                        <div class="specialty">${user.getLogin()}</div>
                    </div>
                </div>
            </div>

            <!-- Menu -->
            <div class="sidebar__menu">
                <div class="title"><img src="resources/img/content/menu/exchange-rates.svg" alt="">Exchange Rates</div>

                <a href='#' class="link">
                    <div class="icon"><img src="resources/img/content/menu/dollar.svg" alt=""></div>
                    <div class="title">Dollar</div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/euro.svg" alt=""></div>
                    <div class="title">Euro</div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/yen.svg" alt=""></div>
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
            <img src="resources/img/content/header/card-managment.svg" alt="">
            <a href="#">Card management</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='200'>
            <img src="resources/img/content/header/replenishment.svg" alt="">
            <a href="#" class="open-replenishment">Replenishment</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='300'>
            <img src="resources/img/content/header/translations.svg" alt="">
            <a href="#" class="open-transaction">Transactions</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='400'>
            <img src="resources/img/content/header/payments.svg" alt="">
            <a href="#" class="open-payments">Payments</a>
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
                        <div class="id"># <c:out value="${item.getId()}"/></div>
                        <div class="descr">
                            <div class="img"><img src="resources/img/content/category/shopping.svg" alt=""></div> Aliexpress
                        </div>
                        <div class="category">Shopping</div>
                        <div class="card">**** 5344</div>
                        <div class="date">07.02.2021</div>
                        <c:choose>
                            <c:when test="${item.getMoney() > 0}">
                                <div class="amount plus"><c:out value="${item.getMoney()}" />$</div>
                            </c:when>
                            <c:otherwise>
                                <div class="amount minus"><c:out value="${item.getMoney()}" />$</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>


            </div>
        </div>
    </div>
</div>

<!-- popup-replenishment -->
<div class="popup popup-replenishment">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title">Replenishment</div>

    <div class="descr">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Quae, fuga! Enim sunt suscipit velit
    </div>

    <form action="${pageContext.request.contextPath}/replenishCreditCard" method="post">

        <label for="chosenCreditCard">Select card</label>

        <select name="chosenCreditCard" required id="chosenCreditCard">
            <c:forEach items="${userCreditCards}" var="item">
                <option><c:out value="${item.getNumber()}" /></option>
            </c:forEach>
        </select>

        <label for="replenishMoney">Replenish money</label>

        <input type="number" min="0" name="replenishMoney" placeholder="0" required id="replenishMoney">

        <button type="submit">Replenish</button>
    </form>

</div>

<!-- popup-transaction -->
<div class="popup popup-transaction">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title">Create transaction</div>

    <div class="descr">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Quae, fuga! Enim sunt suscipit velit
    </div>

    <form action="${pageContext.request.contextPath}/createTransfer" method="post">

        <label for="destinationNumber">destinationNumber</label>
        <input type="number" name="destinationNumber" placeholder="destination" required id="destinationNumber">

        <label for="moneyToPay">moneyToPay</label>
        <input type="number" name="moneyToPay" placeholder="money to pay" required id="moneyToPay">

        <label for="sourceNumber">sourceNumber</label>
        <select name="sourceNumber" id="sourceNumber">
            <c:forEach items="${userCreditCards}" var="item">
                <option><c:out value="${item.getNumber()}" /></option>
            </c:forEach>
        </select>

        <button type="submit">Create transaction</button>

    </form>

</div>

<!-- popup-payments 01 -->
<div class="popup popup-payments">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title">Payments</div>

    <div class="descr">01 or 02</div>

    <div class="select-category">
        <a href="#" class="payments-01" id="mobile" onclick="readId(this.id)">
            <div class="img"><img src="resources/img/content/category/mobile.svg" alt=""></div> Mobile
        </a>
        <a href="#" class="payments-01" id="utilities" onclick="readId(this.id)">
            <div class="img"><img src="resources/img/content/category/utilities.svg" alt=""></div> Utilities
        </a>
        <a href="#" class="payments-01" id="requisite" onclick="readId(this.id)">
            <div class="img"><img src="resources/img/content/category/requisite.svg" alt=""></div> Requisite
        </a>
        <a href="#" class="payments-01" id="charity" onclick="readId(this.id)">
            <div class="img"><img src="resources/img/content/category/charity.svg" alt=""></div> Charity
        </a>
    </div>
</div>

<!-- popup-payments or 02 -->
<div class="popup popup-payments-02">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title">Payments</div>

    <div class="descr">02 or 02</div>

    <form action="${pageContext.request.contextPath}/createPayment" method="post" id="createPayment">

        <label for="chosenCategory">category</label>
        <select name="chosenCategory" id="chosenCategory">
            <c:forEach items="${categories}" var="item">
                <option>
                    <c:out value="${item}" />
                </option>
            </c:forEach>
        </select>

        <label for="destination">destination</label>
        <input type="number" min="0" name="destination" placeholder="requisite number" id="destination">

        <label for="moneyToPay">moneyToPay</label>
        <input type="number" min="0" name="moneyToPay" placeholder="0" id="moneyToPay">

        <label for="sourceNumber">sourceNumber</label>
        <select name="sourceNumber" id="sourceNumber">
            <c:forEach items="${userCreditCards}" var="item">
                <option>
                    <c:out value="${item.getNumber()}" />
                </option>
            </c:forEach>
        </select>

        <button type="submit" id="pay_button">Pay</button>
    </form>
</div>

<div class="bg-menu"></div>
<script src="resources/js/app.js"></script>

</body>

</html>
