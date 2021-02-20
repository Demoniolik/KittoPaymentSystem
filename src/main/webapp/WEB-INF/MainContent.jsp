<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

<%--Localization--%>
<c:if test="${sessionScope.locale == null}">
    <fmt:setLocale value="en"/>
</c:if>
<c:if test="${sessionScope.locale != null}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="localization" var="bundle"/>
<%----%>


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
                <div class="title"><img src="resources/img/content/menu/exchange-rates.svg" alt="">
                    <fmt:message key="exchange_rates" bundle="${bundle}"/>
                </div>

                <a href='#' class="link">
                    <div class="icon"><img src="resources/img/content/menu/dollar.svg" alt=""></div>
                    <div class="title"><fmt:message key="dollar" bundle="${bundle}"/></div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/euro.svg" alt=""></div>
                    <div class="title"><fmt:message key="euro" bundle="${bundle}"/></div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/yen.svg" alt=""></div>
                    <div class="title"><fmt:message key="yen" bundle="${bundle}"/></div>
                </a>

                <div class="title"><img src="resources/img/content/menu/settings.svg" alt="">
                    <fmt:message key="settings" bundle="${bundle}"/>
                </div>

                <c:choose>
                    <c:when test="${locale != null && locale.equals(\"ru\")}">
                        <a href="${pageContext.request.contextPath}/changeLocale?locale=en" class="link">
                            <div class="icon"><img src="resources/img/content/menu/languages.svg" alt=""></div>
                            <div class="title"><fmt:message key="language" bundle="${bundle}"/></div>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/changeLocale?locale=ru" class="link">
                            <div class="icon"><img src="resources/img/content/menu/languages.svg" alt=""></div>
                            <div class="title"><fmt:message key="language" bundle="${bundle}"/></div>
                        </a>
                    </c:otherwise>
                </c:choose>

                <a href="${pageContext.request.contextPath}/logout" class="link logout">
                    <div class="icon"><img src="resources/img/content/menu/logout.svg" alt=""></div>
                    <div class="title"><fmt:message key="logout" bundle="${bundle}"/></div>
                </a>

            </div>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='100'>
            <img src="resources/img/content/header/card-managment.svg" alt="" class="img">
            <a href="#" class="open-card-management">
                <fmt:message key="card_management" bundle="${bundle}"/>
                <img src="resources/img/content/header/show.svg" alt=""></a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='200'>
            <img src="resources/img/content/header/replenishment.svg" alt="" class="img">
            <a href="#" class="open-replenishment">
                <fmt:message key="replenishment" bundle="${bundle}"/>
            </a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='300'>
            <img src="resources/img/content/header/translations.svg" alt="" class="img">
            <a href="#" class="open-transaction">
                <fmt:message key="transactions" bundle="${bundle}"/>
            </a>
        </div>

        <div class="block" data-aos="fade-up" data-aos-delay='400'>
            <img src="resources/img/content/header/payments.svg" alt="" class="img">
            <a href="#" class="open-payments">
                <fmt:message key="payments_section" bundle="${bundle}"/>
            </a>
        </div>

        <div class="card-management" data-aos="fade-up" data-aos-delay='500'>
            <div class="head">
                <div class="left">
                    <div class="title">
                        <fmt:message key="card_management" bundle="${bundle}"/>
                    </div>

                    <%--Here you need to add link to reffer to pop up window--%>
                    <a href="" class="create open-create">
                        <img src="resources/img/content/icons/create.svg" alt="">
                        <fmt:message key="create_card" bundle="${bundle}"/>
                    </a>
                </div>

                <div class="filter">
                    <span><fmt:message key="sort_by" bundle="${bundle}"/> </span>
                    <c:choose>
                        <c:when test="${sortedByNumber == true}">
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=number&sortingOrder=DESC">
                                <fmt:message key="sorting_criteria_by_number" bundle="${bundle}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=number&sortingOrder=ASC">
                                <fmt:message key="sorting_criteria_by_number" bundle="${bundle}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sortedByName == true}">
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=name&sortingOrder=DESC">
                                <fmt:message key="sorting_criteria_by_name" bundle="${bundle}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=name&sortingOrder=ASC">
                                <fmt:message key="sorting_criteria_by_name" bundle="${bundle}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sortedByAmount == true}">
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=money_on_card&sortingOrder=DESC">
                                <fmt:message key="sorting_criteria_by_amount" bundle="${bundle}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortCards?sortingCriteria=money_on_card&sortingOrder=ASC">
                                <fmt:message key="sorting_criteria_by_amount" bundle="${bundle}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="slider">
                <div class="cards">
                    <c:forEach items="${userCreditCardsWithPagination}" var="item">
                        <div class="container-card <c:out value="${(item.isBlocked())? \"blocked\":\"\"}"/>"
                             data-tilt=""
                             data-tilt-max="5" data-tilt-speed="2000" data-tilt-scale="1.05">
                            <div class="card">
                                <div class="options"><img src="resources/img/content/icons/options.svg" alt=""></div>
                                    <%--If card is not blocked then options are available--%>
                                <c:choose>
                                    <c:when test="${!item.isBlocked()}">
                                        <div class="popup-options">
                                            <a href="#" class="card-rename">Rename</a>
                                            <a href="${pageContext.request.contextPath}/blockCreditCard?cardId=${item.getId()}"
                                               class="card-block">Block</a>
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
                </div>

                <util:cardNavigation path="${pageContext.request.contextPath}/cardPagination"
                                     maxPage="${maxPage}" page="${page}" pageSize="${pageSize}"/>
            </div>

        </div>

        <div class="history" data-aos="fade-up" data-aos-delay='600'>
            <div class="head">
                <div class="left">
                    <div class="title">
                        <fmt:message key="transaction_history" bundle="${bundle}"/>
                    </div>

                    <%--                    <select name="chosenCreditCard" required id="chosenCreditCard">--%>
                    <%--                    <c:forEach items="${userCreditCards}" var="item">--%>
                    <%--                        <option>--%>
                    <%--                            <a href="${pageContext.request.contextPath}/selectByCard?=${item.getNumber()}"><c:out value="${item.getNumber()}"/></a>--%>
                    <%--                        </option>--%>
                    <%--                    </c:forEach>--%>
                    <%--                    </select>--%>


                    <c:choose>
                        <c:when test="${prepared != null && prepared}">
                            <a href="${fileName}" download class="download"><img
                                    src="resources/img/content/icons/pdf.svg" alt="">
                                <fmt:message key="download_pdf" bundle="${bundle}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/preparePdfFile" class="download"><img
                                    src="resources/img/content/icons/pdf.svg">
                                <fmt:message key="prepare_pdf" bundle="${bundle}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>


                </div>

                <div class="filter"><span><fmt:message key="sort_by" bundle="${bundle}"/></span>
                    <c:choose>
                        <c:when test="${sorted == true}">
                            <a id="sortingPaymentsByIdDESC"
                               href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=id&paymentSortingOrder=DESC">
                                <fmt:message key="sorting_criteria_by_id" bundle="${bundle}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a id="sortingPaymentsByIdASC"
                               href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=id&paymentSortingOrder=ASC">
                                <fmt:message key="sorting_criteria_by_id" bundle="${bundle}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sorted == true}">
                            <a href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=date&paymentSortingOrder=DESC">
                                <fmt:message key="sorting_criteria_by_date_from_new_to_old" bundle="${bundle}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/sortPayments?paymentSortingCriteria=date&paymentSortingOrder=ASC">
                                <fmt:message key="sorting_criteria_by_date_from_old_to_new" bundle="${bundle}"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="table overflow-table">
                <div class="item item-head">
                    <div class="id"><fmt:message key="id_column" bundle="${bundle}"/></div>
                    <div class="descr"><fmt:message key="description_column" bundle="${bundle}"/></div>
                    <div class="category"><fmt:message key="type_column" bundle="${bundle}"/></div>
                    <div class="card"><fmt:message key="card_column" bundle="${bundle}"/></div>
                    <div class="date"><fmt:message key="date_column" bundle="${bundle}"/></div>
                    <div class="amount"><fmt:message key="amount_column" bundle="${bundle}"/></div>
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
                        <a href="${pageContext.request.contextPath}/showMorePayments?limit=5" class="view-less">
                            <fmt:message key="view_less" bundle="${bundle}"/>
                            <img src="resources/img/content/icons/view-more.svg" alt="">
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/showMorePayments?limit=${paymentPageSize}">
                            <fmt:message key="view_more" bundle="${bundle}"/>
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
    <div class="title"><fmt:message key="replenishment" bundle="${bundle}"/></div>

    <div class="descr">
        <fmt:message key="replenishment_description" bundle="${bundle}"/>
    </div>

    <form action="${pageContext.request.contextPath}/replenishCreditCard" method="post">

        <label for="chosenCreditCard"><fmt:message key="select_card" bundle="${bundle}"/></label>

        <select name="chosenCreditCard" required id="chosenCreditCard">
            <c:forEach items="${userCreditCards}" var="item">
                <option><c:out value="${item.getNumber()}"/></option>
            </c:forEach>
        </select>

        <label for="replenishMoney"><fmt:message key="replenish_money" bundle="${bundle}"/></label>

        <input type="number" min="0" name="<fmt:message key="replenish_money" bundle="${bundle}"/>" placeholder="0" required id="replenishMoney">

        <button type="submit"><fmt:message key="replenish" bundle="${bundle}"/></button>
    </form>

</div>

<!-- popup-transaction -->
<div class="popup popup-transaction">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title"><fmt:message key="create_transaction" bundle="${bundle}"/></div>

    <div class="descr">
        <fmt:message key="transaction_description" bundle="${bundle}"/>
    </div>

    <form action="${pageContext.request.contextPath}/createTransfer" method="post">

        <label for="destinationNumber"><fmt:message key="destination_number" bundle="${bundle}"/></label>
        <input type="number" name="destinationNumber" placeholder="<fmt:message key="destination_placeholder" bundle="${bundle}"/>" required id="destinationNumber">

        <label for="moneyToPay"><fmt:message key="money_to_pay" bundle="${bundle}"/></label>
        <input type="number" name="moneyToPay" placeholder="<fmt:message key="money_to_pay_placeholder" bundle="${bundle}"/>" required id="moneyToPay">

        <label for="sourceNumber"><fmt:message key="source_number" bundle="${bundle}"/></label>

        <select name="chosenCreditCard" required id="chosenCreditCard">
            <c:forEach items="${userCreditCards}" var="item">
                <option><c:out value="${item.getNumber()}"/></option>
            </c:forEach>
        </select>

        <button type="submit"><fmt:message key="create_transaction" bundle="${bundle}"/></button>

    </form>

</div>

<!-- popup-payments 01 -->
<div class="popup popup-payments">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title"><fmt:message key="payments_section" bundle="${bundle}"/></div>

    <div class="descr"><fmt:message key="step_header_01" bundle="${bundle}"/></div>

    <div class="select-category">
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/mobile.svg" alt=""></div>
            <fmt:message key="mobile_category" bundle="${bundle}"/>
        </a>
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/utilities.svg" alt=""></div>
            <fmt:message key="utilities_category" bundle="${bundle}"/>
        </a>
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/requisite.svg" alt=""></div>
            <fmt:message key="requisite_category" bundle="${bundle}"/>
        </a>
        <a href="#" class="payments-01">
            <div class="img"><img src="resources/img/content/category/charity.svg" alt=""></div>
            <fmt:message key="charity_category" bundle="${bundle}"/>
        </a>
    </div>
</div>

<!-- popup-payments or 02 -->
<div class="popup popup-payments-02">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title"><fmt:message key="payments_section" bundle="${bundle}"/></div>

    <div class="descr"><fmt:message key="step_header_02" bundle="${bundle}"/></div>

    <form action="${pageContext.request.contextPath}/createPayment" method="post">

        <label for="chosenCategory"><fmt:message key="payment_category" bundle="${bundle}"/></label>
        <select name="chosenCategory" id="chosenCategory">
            <c:forEach items="${categories}" var="item">
                <option>
                    <c:out value="${item}"/>
                </option>
            </c:forEach>
        </select>

        <label for="destination"><fmt:message key="destination_number" bundle="${bundle}"/></label>
        <input type="number" min="0" name="destination" placeholder="<fmt:message key="destination_placeholder" bundle="${bundle}"/>" id="destination">

        <label for="moneyToPay"><fmt:message key="money_to_pay" bundle="${bundle}"/></label>
        <input type="number" min="0" name="moneyToPay" placeholder="<fmt:message key="money_to_pay_placeholder" bundle="${bundle}"/>" id="moneyToPay">

        <label for="sourceNumber"><fmt:message key="source_number" bundle="${bundle}"/></label>
        <select name="sourceNumber" id="sourceNumber">
            <c:forEach items="${userCreditCards}" var="item">
                <option>
                    <c:out value="${item.getNumber()}"/>
                </option>
            </c:forEach>
        </select>

        <button type="submit"><fmt:message key="pay" bundle="${bundle}"/></button>
    </form>
</div>

<div class="popup popup-creating-card">
    <div class="close"><img src="resources/img/content/icons/close.svg" alt=""></div>
    <div class="title"><fmt:message key="create_card" bundle="${bundle}"/></div>

    <div class="descr">
        <fmt:message key="create_card_description" bundle="${bundle}"/>
    </div>

    <form action="${pageContext.request.contextPath}/creatingNewCreditCard" method="post">

        <label for="cardNumber"><fmt:message key="card_number" bundle="${bundle}"/></label>
        <input type="number" name="cardNumber" placeholder="<fmt:message key="card_name_placeholder" bundle="${bundle}"/>" required id="cardNumber">

        <label for="cardName"><fmt:message key="card_name" bundle="${bundle}"/></label>
        <input type="text" name="cardName" placeholder="<fmt:message key="card_number_placeholder" bundle="${bundle}"/>" required id="cardName">

        <button type="submit"><fmt:message key="create_card_button" bundle="${bundle}"/></button>

    </form>

</div>

<div class="bg-menu"></div>
<script src="resources/js/app.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
</script>

<script>
    $('a.open-create').click(function (e) {
        e.preventDefault();
        $('.popup.popup-creating-card').toggleClass('show');
        $('.bg-menu').toggleClass('show');
    });

</script>

</body>

</html>