package com.bridgelabz.addressBook;

import java.util.ArrayList;


class AddressBook {
    String name;
    ArrayList<Contact> contacts;

    AddressBook(String name) {
        this.name = name;
        this.contacts = new ArrayList<>();
    }
}