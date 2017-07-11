package com.rentacar.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrei.Plesca
 */
@Embeddable
public class DrivingLicense implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "licenseNumber", unique = true, nullable = false)
    private String licenseNumber;
    @Column(name = "licenseObtainingDate")
    private Date obtainingDate;
    @Column(name = "licenseExpiringDate")
    private Date expiringDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrivingLicense that = (DrivingLicense) o;

        return (licenseNumber != null ? licenseNumber.equals(that.licenseNumber) : that.licenseNumber == null)
                && (obtainingDate != null ? obtainingDate.equals(that.obtainingDate) : that.obtainingDate == null)
                && (expiringDate != null ? expiringDate.equals(that.expiringDate) : that.expiringDate == null);
    }

    @Override
    public int hashCode() {
        int result = licenseNumber != null ? licenseNumber.hashCode() : 0;
        result = 31 * result + (obtainingDate != null ? obtainingDate.hashCode() : 0);
        result = 31 * result + (expiringDate != null ? expiringDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DrivingLicense{" +
                "\n licenseNumber='" + licenseNumber + '\'' +
                ",\n obtainingDate=" + obtainingDate +
                ",\n expiringDate=" + expiringDate +
                "\n}";
    }
}
