package com.rentacar.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrei.Plesca
 */
@Embeddable
public class DrivingLicense {

    @Column(name = "licenseNumber", unique = true, nullable = false)
    private String licenseNumber;
    @Column(name = "licenseObtainingDate")
    private Date obtainingDate;
    @Column(name = "licenseExpiringDate")
    private Date expiringDate;
    @Transient
    private SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Date getObtainingDate() {
        return obtainingDate;
    }

    public void setObtainingDate(Date obtainingDate) {
        this.obtainingDate = obtainingDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

//    public Date getObtainingDate() {
//        return obtainingDate;
//    }
//
//    public void setObtainingDate(String obtainingDate) {
//        try {
//            this.obtainingDate = fmt.parse(obtainingDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Date getExpiringDate() {
//        return expiringDate;
//    }
//
//    public void setExpiringDate(String expiringDate) {
//        try {
//            this.expiringDate = fmt.parse(expiringDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
