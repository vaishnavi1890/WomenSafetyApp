package com.example.womensafety;

public class cnt {
    private String Name;
    private String Contact;


    public cnt() {

    }

    public cnt(String Name,String Contact) {
        this.Name=Name;
        this.Contact = Contact;

    }
    public String getName(){
        return Name;
    }
    public String setName(String Name){
        this.Name=Name;
        return Name;
    }

    public String getContact() {
        return Contact;
    }
    public String setContact(String contact){
        this.Contact=Contact;
        return Contact;
    }




}

