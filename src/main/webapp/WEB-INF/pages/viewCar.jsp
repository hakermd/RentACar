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
            <form:form name="${car.winCode}" method="POST" action="/viewCar.html">
                <div id="viewCarDetails">
                    <div class="carDetailsViewDescription"><h2>${car.manufacturer} ${car.model} ${car.type}</h2></div>
                    <hr>
                    <div class="carDetailsViewDescription"><label for="carYearOfProduction">Year Of Production :</label>
                        <output id="carYearOfProduction">${car.yearOfProduction}</output>
                        <input id="carViewDetailedId" type="hidden" name="carWinCode" value="${car.winCode}"/></td>
                    </div>
                    <div class="carDetailsViewDescription"><img class="carFullPhoto"
                                                                src="resources/img/${car.carPhoto}"
                                                                alt="Car Photo"></div>
                    <div class="carDetailsViewDescription"><label for="carEconomyClass">Economy Class :</label>
                        <output id="carEconomyClass">${car.economyClass}</output>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carEconomyOptions">Options :</label>
                        <output id="carEconomyOptions">${car.options}</output>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carEngineVolume">Engine Volume :</label>
                        <output id="carEngineVolume">${car.engineVolume} cm3</output>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carFuelType">Fuel Type :</label>
                        <output id="carFuelType">${car.fuelType}</output>
                    </div>
                    <div class="carDetailsViewDescription"><label for="carTransmission">Transmission :</label>
                        <output id="carTransmission">${car.transmission}</output>
                    </div>
                    <hr>
                </div>

                <div id="detailedLeftPanel">
                    <div id="leftContentViewCarDetails">
                        <label for="carRentPrice">Rent Price :</label>
                        <output id="carRentPrice">${car.carPrice} $</output>
                        <br>
                        <label for="carInsurancePrice">Insurance :</label>
                        <output id="carInsurancePrice">${insurance.insuranceCostCalculate(car.economyClass)} $
                        </output>
                        <h2>
                            <label for="carTotalPrice">Total Price :</label>
                            <output id="carTotalPrice">
                                    ${car.carPrice + insurance.insuranceCostCalculate(car.economyClass)} $
                            </output>
                        </h2>
                        <hr>
                        <div><input class="button button1" type="submit" name="action"
                                    value="RENT"/>
                            <input class="button button2" type="submit" name="action"
                                   value="BOOK"/>
                        </div>

                    </div>
                </div>
            </form:form>
        </div>
    </div>

</div>
</body>
</html>
