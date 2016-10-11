package com.sap.cloud.sample.persistence;

/**
 * Class holding information on a person.
 */
public class Person {
    private String password = "onePersonId";
    private String firstName = "onePersonFirstName";
    private String lastName = "onePersonLastName";
    private String userName = "oneUserName@hexagonconsulting.net";
    private int age = 0;
    private int earnings = 100;
    
    public int getEarnings() {
    	return earnings;
    }
    public void setEarnings(int earnings) {
    	this.earnings = earnings;
    }
    
    public String getUserName() {
    	return userName;
    }
    public void setUserName(String userName) {
    	this.userName = userName;
    }
    
    public int getAge() {
    	return age;
    }
    public void setAge(int age) {
    	this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }
}
