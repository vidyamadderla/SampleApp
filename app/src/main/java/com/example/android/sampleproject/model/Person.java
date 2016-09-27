package com.example.android.sampleproject.model;

import org.json.JSONObject;

/**
 * Created by vidya.madderla on 9/20/2016.
 */
public class Person {

    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_DOB = "dateOfBirth";
    public static final String FIELD_ZIPCODE = "zipCode";

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String zipCode;

    public Person(){
    }
    public Person(String firstName, String lastName, String dob, String zipCode){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dob;
        this.zipCode = zipCode;
    }

    public void parseJson(JSONObject jsonObject){
        this.firstName = jsonObject.optString(FIELD_FIRST_NAME);
        this.lastName = jsonObject.optString(FIELD_LAST_NAME);
        this.dateOfBirth = jsonObject.optString(FIELD_DOB);
        this.zipCode = jsonObject.optString(FIELD_ZIPCODE);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getZipCode() {
        return zipCode;
    }

}
