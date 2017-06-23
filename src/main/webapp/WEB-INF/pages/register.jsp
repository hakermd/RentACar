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

            <div id="registerPageCentral">
                <h1>Register New User Account</h1>
                <form:form name="registrationForm" method="POST" commandName="person" action="register.do">
                    <div class="carDetailsViewDescription"><label for="firstNameRegister">First Name :</label>
                        <form:input type="text" id="firstNameRegister" path="firstName"/>
                        <form:errors type="text" path="firstName" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="lastNameRegister">LastName :</label>
                        <form:input type="text" id="lastNameRegister" path="lastName"/>
                        <form:errors type="text" path="lastName" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="emailRegister">Email :</label>
                        <form:input type="text" id="emailRegister" path="email"/>
                        <form:errors type="text" path="email" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="addressRegister">Address :</label>
                        <form:input type="text" id="addressRegister" path="address"/>
                        <form:errors type="text" path="address" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="userPasswordRegister">Password :</label>
                        <form:input type="password" id="userPasswordRegister" path="password"/>
                        <form:errors type="text" path="password" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="userPasswordCheckRegister">Confirm Password
                        :</label>
                        <form:input type="password" id="userPasswordCheckRegister"
                                    path="checkPassword"/>
                        <form:errors type="text" path="checkPassword" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="userBirthDayRegister">Birth Day :</label>
                        <form:input type="date" id="userBirthDayRegister" path="birthDate"
                                    placeholder="dd-mm-yyyy"/>
                        <form:errors type="text" path="birthDate" cssClass="error"/>
                    </div>
                    <hr>
                    <div class="carDetailsViewDescription"><label>Gender :</label>
                        <form:radiobutton name="gender" checked="true" path="gender" value="M"/> Male
                        <form:radiobutton name="gender" path="gender" value="W"/> Female
                    </div>
                    <hr>
                    <h2>Driving License</h2>
                    <div class="carDetailsViewDescription"><label for="licenseNumberRegister">License Number :</label>
                        <form:input type="text" id="licenseNumberRegister"
                                    path="drivingLicense.licenseNumber"/>
                        <form:errors type="text" path="drivingLicense.licenseNumber" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="obtainingDateRegister">Obtaining Date :</label>
                        <form:input type="date" id="obtainingDateRegister" placeholder="dd-mm-yyyy"
                                    path="drivingLicense.obtainingDate"/>
                        <form:errors type="text" path="drivingLicense.obtainingDate" cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="expiringDateRegister">Expiring Date :</label>
                        <form:input type="date" id="expiringDateRegister" placeholder="dd-mm-yyyy"
                                    path="drivingLicense.expiringDate"/>
                        <form:errors type="text" path="drivingLicense.expiringDate" cssClass="error"/>
                    </div>


                    <div><input id="registerButton" class="button button1" type="submit" name="action"
                                value="REGISTER"/></div>
                </form:form>

            </div>

        </div>
    </div>

</div>
</body>
</html>
