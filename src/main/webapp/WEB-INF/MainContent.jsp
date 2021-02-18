<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>

    <title>Bank</title>
    <meta name="description" content="description of the site">
    <meta name="keywords" content="key 1, key 2, key 3…"/>

    <meta name="google" content="notranslate">

    <!-- iOS Safari -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://use.typekit.net/blx4det.css">
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700;900&display=swap" rel="stylesheet">
    <link rel="icon" href="resources/favicon.svg">
    <link href="resources/css/app.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="grid">

        <!-- Sidebar -->
        <div class="sidebar">

            <!-- head -->
            <div class="sidebar__head">
                <div class="head">
                    <a href="${pageContext.request.contextPath}/goToPersonalCabinet">
                        <div class="container-img"><img src="resources/img/content/menu/logo.webp" alt=""></div>
                        <div class="text">
                            <div class="name">${user.getFirstName()} ${user.getLastName()}</div>
                            <div class="specialty">${user.getLogin()}</div>
                        </div>
                    </a>
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

                <a class="link logout">
                    <div class="icon"><img src="resources/img/content/menu/logout.svg" alt=""></div>
                    <div class="title">Logout</div>
                </a>

            </div>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='100'>
            <img src="resources/img/content/header/card-managment.svg" alt="" class="img">
            <a href="#" class="open-card-management">Card management <img src="resources/img/content/header/show.svg"
                                                                          alt=""></a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='200'>
            <img src="resources/img/content/header/replenishment.svg" alt="" class="img">
            <a href="#" class="open-replenishment">Replenishment</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='300'>
            <img src="resources/img/content/header/translations.svg" alt="" class="img">
            <a href="#" class="open-transaction">Transactions</a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='400'>
            <img src="resources/img/content/header/payments.svg" alt="" class="img">
            <a href="#" class="open-payments">Payments</a>
        </div>

        <div class="card-management" data-aos="fade-up" data-aos-delay='500'>
            <div class="head">
                <div class="left">
                    <div class="title">Card management</div>

                    <%--Here you need to add link to reffer to pop up window--%>
                    <a href="#" class="create"><img src="resources/img/content/icons/create.svg" alt=""> Create Card</a>
                </div>

                <div class="filter"><span>Sort by: </span>
                    <c:choose>
                        <c:when test="${sortedByNumber == true}">
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=number&sortingOrder=DESC">Number</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=number&sortingOrder=ASC">Number</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sortedByName == true}">
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=name&sortingOrder=DESC">Name</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=name&sortingOrder=ASC">Name</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sortedByAmount == true}">
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=money_on_card&sortingOrder=DESC">Amount</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=money_on_card&sortingOrder=ASC">Amount</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:forEach items="${userCreditCardsWithPagination}" var="item">
                <div class="container-card <c:out value="${(item.isBlocked())? \"blocked\":\"\"}"/>" data-tilt=""
                     data-tilt-max="5" data-tilt-speed="2000" data-tilt-scale="1.05">
                    <div class="card">
                        <div class="options"><img src="resources/img/content/icons/options.svg" alt=""></div>
                            <%--If card is not blocked then options are available--%>
                        <c:choose>
                            <c:when test="${!item.isBlocked()}">
                                <div class="popup-options">
                                    <a href="#" class="card-rename">Rename</a>
                                    <a href="${pageContext.request.contextPath}/blockCreditCard?cardId=${item.getId()}" class="card-block">Block</a>
                                </div>
                            </c:when>
                        </c:choose>

                        <div class="sum"><span><c:out value="${item.getMoneyOnCard()}"/></span>$</div>
                        <div class="num">****
                            <span>
                                <c:out value="${item.getNumber().toString().substring(item.getNumber().toString().length() - 4)}"/>
                            </span>
                        </div>
                    </div>

                    <div class="name"><c:out value="${item.getName()}"/></div>
                </div>
            </c:forEach>

            <util:cardNavigation path="${pageContext.request.contextPath}/cardPagination"
                                 maxPage="${maxPage}" page="${page}" pageSize="${pageSize}"/>

        </div>

        <div class="history" data-aos="fade-up" data-aos-delay='600'>
            <div class="head">
                <div class="left">
                    <div class="title">Transaction history</div>

                    <%--                    <select name="chosenCreditCard" required id="chosenCreditCard">--%>
                    <%--                    <c:forEach items="${userCreditCards}" var="item">--%>
                    <%--                        <option>--%>
                    <%--                            <a href="${pageContext.request.contextPath}/selectByCard?=${item.getNumber()}"><c:out value="${item.getNumber()}"/></a>--%>
                    <%--                        </option>--%>
                    <%--                    </c:forEach>--%>
                    <%--                    </select>--%>

                    <a href="#" download class="download"><img src="resources/img/content/icons/pdf.svg" alt="">Download
                        PDF</a>
                </div>

                <div class="filter"><span>Sort by: </span>
                    <c:choose>
                        <c:when test="${sorted == true}">
                            <a id="sortingPaymentsByIdDESC" href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=id&paymentSortingOrder=DESC">ID</a>
                        </c:when>
                        <c:otherwise>
                            <a id="sortingPaymentsByIdASC" href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=id&paymentSortingOrder=ASC">ID</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sorted == true}">
                            <a href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=date&paymentSortingOrder=DESC">From
                                new to old</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=date&paymentSortingOrder=ASC">From
                                old to new</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

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
                            <div class="img"><img src="<c:out value="${item.getCategoryImage()}"/>" alt=""></div>
                            <c:out value="${item.getDescription()}"/>
                        </div>
                        <div class="category"><c:out value="${item.getPaymentCategory()}"/></div>
                        <div class="card">**** <span><c:out value="${item.getCreditCardDestination()}"/></span></div>
                        <div class="date"><c:out value="${item.getDate()}"/></div>
                        <c:choose>
                            <c:when test="${item.getMoney() > 0}">
                                <div class="amount plus"><c:out value="${item.getMoney()}"/>$</div>
                            </c:when>
                            <c:otherwise>
                                <div class="amount minus"><c:out value="${item.getMoney()}"/>$</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>

            </div>

            <div class="view-more">
                <c:choose>
                    <c:when test="${maxPaymentPageSize != null && maxPaymentPageSize <= paymentPageSize}">
                        <a href="${pageContext.request.contextPath}/showMorePayments?limit=5">
                            View less
                            <img src="resources/img/content/icons/view-more.svg" alt="">
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/showMorePayments?limit=${paymentPageSize}">
                            View more
                            <img src="resources/img/content/icons/view-more.svg" alt="">
                        </a>
                    </c:otherwise>
                </c:choose>
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
                <option><c:out value="${item.getNumber()}"/></option>
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

        <select name="chosenCreditCard" required id="chosenCreditCard">
            <c:forEach items="${userCreditCards}" var="item">
                <option><c:out value="${item.getNumber()}"/></option>
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
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/mobile.svg" alt=""></div>
            Mobile
        </a>
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/utilities.svg" alt=""></div>
            Utilities
        </a>
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/requisite.svg" alt=""></div>
            Requisite
        </a>
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/charity.svg" alt=""></div>
            Charity
        </a>
    </div>
</div>

<!-- popup-payments or 02 -->
<div class="popup popup-payments-02">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title">Payments</div>

    <div class="descr">02 or 02</div>

    <form action="${pageContext.request.contextPath}/createPayment" method="post">

        <label for="chosenCategory">category</label>
        <select name="chosenCategory" id="chosenCategory">
            <c:forEach items="${categories}" var="item">
                <option>
                    <c:out value="${item}"/>
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
                    <c:out value="${item.getNumber()}"/>
                </option>
            </c:forEach>
        </select>

        <button type="submit">Pay</button>
    </form>
</div>

<%--<div class="popup popup-creating-card">--%>
<%--    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>--%>
<%--    <div class="title">Create card</div>--%>

<%--    <div class="descr">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Quae, fuga! Enim sunt suscipit velit--%>
<%--    </div>--%>

<%--    <form action="${pageContext.request.contextPath}/creatingNewCreditCard" method="post">--%>

<%--        <label for="cardNumber">destinationNumber</label>--%>
<%--        <input type="number" name="cardNumber" placeholder="card number" required id="cardNumber">--%>

<%--        <label for="cardName">moneyToPay</label>--%>
<%--        <input type="text" name="cardName" placeholder="money to pay" required id="cardName">--%>

<%--        <button type="submit">Create card</button>--%>

<%--    </form>--%>

<%--</div>--%>

<div class="bg-menu"></div>
<script src="resources/js/app.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
</script>

</body>

</html>