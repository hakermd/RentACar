<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head><TITLE>Login</TITLE>
    <link href="/resources/css/pageView.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery.js" type="text/javascript"></script>
    <script src="/resources/js/rentCar.js" type="text/javascript"></script>
</head>
<body>
<div>
    <div id="centralContent">
        <div id="centralContentHeader">
            <h1>RENT A CAR</h1>
        </div>
        <div>

            <div id="loginPageCentral">
                <h1>Login Into Rent A Car Service</h1>
                <form:form name="loginForm" method="POST" modelAttribute="login" action="login.do">
                    <div class="carDetailsViewDescription"><label for="userLogin">Login :</label>
                        <form:input type="text" id="userLogin" path="email"/>
                        <form:errors type="text" id="userLogin" path="email" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="userPassword">Password :</label>
                        <form:input type="password" id="userPassword" path="userPassword"/>
                        <form:errors type="password" id="userPassword" path="userPassword" cssClass="error"/>
                    </div>
                    <div><input class="button button1" type="submit" name="action"
                                value="LOGIN"/></div>
                </form:form>
                <form name="createNewAccount" method="GET" action="/register.html">
                    <div>
                        <output id="createNewAccount">Create New Account</output>
                    </div>
                </form>

            </div>

        </div>
    </div>

</div>
</body>
</html>
