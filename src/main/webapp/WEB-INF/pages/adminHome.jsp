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
            <h2>Admin Panel</h2>
        </div>
        <div>
            <form name="createNewCar" method="GET" action="/addCarAdmin.html">
                <div>
                    <output id="createNewCar">Create New Car</output>
                </div>
            </form>
            <div>
                <div class="centralContentCarList">

                    <c:choose>
                        <c:when test="${empty  cars}">
                            <div class="centralContentTableNoResults">
                                <h1>No results ...</h1>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="centralContentTable">
                                <c:forEach items="${cars}" var="car">
                                    <form:form name="${car.winCode}" method="GET" action="viewCar">
                                        <tr class="carRow" id="${car.winCode}">
                                            <td class="carItemSpecial"><img class="carIcon"
                                                                            src="resources/img/${car.carPhoto}"
                                                                            alt="Car Photo"></td>
                                            <td class="carItem">
                                                    ${car.manufacturer}
                                                <br>
                                                    ${car.model}
                                                <br>
                                                    ${car.yearOfProduction}
                                            </td>
                                            <input type="hidden" name="carWinCode"
                                                   value="${car.winCode}"/></td>
                                            <td class="carItem">
                                                    ${car.type}
                                                <br>
                                                    ${car.fuelType}
                                            </td>
                                            <td class="carItem">${car.engineVolume} cm3</td>
                                            <td class="carItem">${car.availability} </td>

                                        </tr>
                                    </form:form>
                                </c:forEach>

                            </table>
                        </c:otherwise>
                    </c:choose>

                </div>
                <div id="centralContentFilter">
                    <div id="centralContentFilterTitle">
                        <h2>FILTER</h2>
                    </div>
                    <form:form method="POST" action="filterAdminCars" commandName="filter">
                        <table id="centralContentFilterTable">
                            <tr>
                                <td><label for="carStatusFilter">Car Status: </label>
                                    <form:select path="carAvailability" id="carStatusFilter"
                                                 class="form-control styled-select blue semi-square">
                                        <option value="">Select Value</option>
                                        <c:forEach items="<%=CarAvailability.values()%>" var="available">
                                            <c:choose>
                                                <c:when test="${filter.carAvailability==available.value}">
                                                    <option value="${available.value}"
                                                            selected>${available.value}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${available.value}">${available.value}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="economyClassFilter">Economy Class: </label>
                                    <form:select path="economyClass" id="economyClassFilter"
                                                 class="form-control styled-select blue semi-square">
                                        <option value="">Select Value</option>
                                        <c:forEach items="<%=EconomyClass.values()%>" var="ecoClass">
                                            <c:choose>
                                                <c:when test="${filter.economyClass==ecoClass.value}">
                                                    <option value="${ecoClass.value}"
                                                            selected>${ecoClass.value}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${ecoClass.value}">${ecoClass.value}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="carTypeFilter">Car Type: </label>
                                    <form:select id="carTypeFilter"
                                                 class="form-control styled-select blue semi-square"
                                                 path="carType">
                                        <option value="">Select Value</option>
                                        <c:forEach items="<%=CarType.values()%>" var="carType">
                                            <c:choose>
                                                <c:when test="${filter.carType==carType.value}">
                                                    <option value="${carType.value}"
                                                            selected>${carType.value}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${carType.value}">${carType.value}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="optionsFilter">Options: </label>
                                    <form:select id="optionsFilter"
                                                 class="form-control styled-select blue semi-square"
                                                 path="options">
                                        <option value="">Select Value</option>
                                        <c:forEach items="<%=Options.values()%>" var="compl">
                                            <c:choose>
                                                <c:when test="${filter.options==compl.value}">

                                                    <option value="${compl.value}"
                                                            selected>${compl.value}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${compl.value}">${compl.value}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="transmissionFilter">Transmission: </label>
                                    <form:select id="transmissionFilter"
                                                 class="form-control styled-select blue semi-square"
                                                 path="transmission">
                                        <option value="">ANY</option>
                                        <c:forEach items="<%=CarTransmission.values()%>" var="transm">
                                            <c:choose>
                                                <c:when test="${filter.transmission==transm.value}">

                                                    <option value="${transm.value}"
                                                            selected>${transm.value}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${transm.value}">${transm.value}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="yearFilter">Year Of Production: </label>
                                    <form:select id="yearFilter"
                                                 class="form-control styled-select blue semi-square"
                                                 path="yearOfProduction">
                                        <option value="0">Select Value</option>
                                        <c:forEach begin="1999" end="2017" var="year">
                                            <c:choose>
                                                <c:when test="${filter.yearOfProduction==year}">

                                                    <option value="${year}" selected>${year}
                                                    </option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${year}">${year}
                                                    </option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td class="carItemSpecial"><input class="button button1" type="submit"
                                                                  action="filterAdminCars"
                                                                  value="FILTER"/>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
