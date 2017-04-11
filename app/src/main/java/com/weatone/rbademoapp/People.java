package com.weatone.rbademoapp;

import java.io.Serializable;

/**
 * @author Celine Nicolas
 * @version 1.0.0, 2017-04-07
 * @since 1.0.0
 */

public class People implements Serializable {

    private String mFirstname;
    private String mLastname;
    private String mAge;
    private String mAddressUnit;
    private String mAddressStreet;
    private String mAddressCity;
    private String mAddressProvince;
    private String mAddressCountry;
    private String mAddressPostalCode;

    public People () {

    }

    public void setName( String firstName, String lastName ) {
        mFirstname = firstName;
        mLastname = lastName;
    }

    public void setAge( String age ) {
        mAge = age;
    }

    public void setAddress( String unit, String streetName, String city, String postalCode, String country, String province ) {
        mAddressUnit = unit;
        mAddressStreet = streetName;
        mAddressCity = city;
        mAddressProvince = province;
        mAddressCountry = country;
        mAddressPostalCode = postalCode;
    }

    public String getDisplayName() {
        return mFirstname + " " + mLastname;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public String getLastname() {
        return mLastname;
    }

    public String getAge() {
        return mAge;
    }

    public String getAddressUnit() {
        return mAddressUnit;
    }

    public String getAddressStreet() {
        return mAddressStreet;
    }

    public String getAddressCity() {
        return mAddressCity;
    }

    public String getAddressProvince() {
        return mAddressProvince;
    }

    public String getAddressCountry() {
        return mAddressCountry;
    }

    public String getAddressPostalCode() {
        return mAddressPostalCode;
    }
}
