package com.bridgelabz.addressBook;

public class Contact {

    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    String zip;
    String phoneNumber;
    String email;

    Contact(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
               "Address: " + address + "\n" +
               "City: " + city + "\n" +
               "State: " + state + "\n" +
               "ZIP: " + zip + "\n" +
               "Phone Number: " + phoneNumber + "\n" +
               "Email: " + email + "\n";
    }
}


