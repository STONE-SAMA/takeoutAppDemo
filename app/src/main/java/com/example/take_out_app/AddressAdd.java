package com.example.take_out_app;

public class AddressAdd {
    String Phone;
    String newAddress;

    public AddressAdd(String phone, String newAddress) {
        this.Phone = phone;
        this.newAddress = newAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
}
