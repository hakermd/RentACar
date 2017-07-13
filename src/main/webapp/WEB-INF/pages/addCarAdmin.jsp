<%@ page import="com.rentacar.model.enums.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
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
            <form:form name="addCarForm" method="POST" commandName="car" action="addCar.do">
                <div id="viewCarDetailsModify">
                    <div class="carDetailsViewDescription"><label for="carWinCode">Win Code :</label>
                        <form:input type="text" id="carWinCode" path="winCode"/>
                        <form:errors type="text" id="carWinCode" path="winCode"
                                     cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carManufacturer">Manufacturer
                        :</label>
                        <form:input type="text" id="carManufacturer" path="manufacturer"/>
                        <form:errors type="text" id="carManufacturer" path="manufacturer"
                                     cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carModel">Model
                        :</label>
                        <form:input type="text" id="carModel" path="model"/>
                        <form:errors type="text" id="carModel" path="model"
                                     cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription">
                        <label for="carType">Car Type: </label>
                        <form:select id="carType"
                                     class="form-control styled-select blue semi-square"
                                     path="type">
                            <c:forEach items="<%=CarType.values()%>" var="carType">

                                <option value="${carType.value}">${carType.value}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carYear">Year Of Production
                        :</label>
                        <form:select id="carYear"
                                     class="form-control styled-select blue semi-square"
                                     path="yearOfProduction">
                            <c:forEach begin="1999" end="2017" var="year">
                                <option value="${year}">${year}
                                </option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="carDetailsViewDescription"><label for="registrationNumber">Registration
                        Number
                        :</label>
                        <form:input type="text" id="registrationNumber" path="registrationNumber"/>
                        <form:errors type="text" id="registrationNumber" path="registrationNumber"
                                     cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription"><label for="engineVolume">Engine Volume
                        :</label>
                        <form:input type="text" id="engineVolume" path="engineVolume"/>
                        <form:errors type="text" id="engineVolume" path="engineVolume"
                                     cssClass="error"/>
                    </div>
                    <div class="carDetailsViewDescription">
                        <label for="fuelType">Fuel Type: </label>
                        <form:select id="fuelType"
                                     class="form-control styled-select blue semi-square"
                                     path="fuelType">
                            <c:forEach items="<%=FuelType.values()%>" var="fuel">

                                <option value="${fuel.value}">${fuel.value}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="carDetailsViewDescription">
                        <label for="carTransmission">Car Transmission : </label>
                        <form:select id="carTransmission"
                                     class="form-control styled-select blue semi-square"
                                     path="transmission">
                            <c:forEach items="<%=CarTransmission.values()%>" var="trans">

                                <option value="${trans.value}">${trans.value}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="carDetailsViewDescription">
                        <label for="carEconomyClass">Car Economy Class : </label>
                        <form:select id="carEconomyClass"
                                     class="form-control styled-select blue semi-square"
                                     path="economyClass">
                            <c:forEach items="<%=EconomyClass.values()%>" var="economy">

                                <option value="${economy.value}">${economy.value}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="carDetailsViewDescription">
                        <label for="carOptions">Options : </label>
                        <form:select id="carOptions"
                                     class="form-control styled-select blue semi-square"
                                     path="options">
                            <c:forEach items="<%=Options.values()%>" var="options">

                                <option value="${options.value}">${options.value}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <hr>
                    <div class="carDetailsViewDescription"><label for="carPrice">Price :</label>
                        <form:input type="text" id="carPrice" path="carPrice"/>
                        <form:errors type="text" id="carPrice" path="carPrice"
                                     cssClass="error"/>
                    </div>
                    <hr>
                    <div class="carDetailsViewDescription">
                        <input class="button button1" type="submit"
                               name="addCar"
                               value="ADD CAR"/>
                        <input class=" admbtn button button3" type="submit" name="cancel"
                               value="CANCEL"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

</div>
</body>
</html>
