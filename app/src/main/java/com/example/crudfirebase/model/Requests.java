package com.example.crudfirebase.model;

public class Requests {
    private String nama;
    private String email;
    private String deks;

    private String key;

    public Requests() {

    }

    public Requests(String nama, String email, String deks) {
        this.nama = nama;
        this.email = email;
        this.deks = deks;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeks() {
        return deks;
    }

    public void setDeks(String deks) {
        this.deks = deks;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " " + nama + "\n" +
                " " + email + "\n" +
                " " + deks;
    }
}
