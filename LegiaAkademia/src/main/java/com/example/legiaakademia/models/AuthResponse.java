package com.example.legiaakademia.models;

public class AuthResponse {
    private String token;
    private Long rodzicId;
    private String email;
    private String imie;
    private String nazwisko;

    public AuthResponse() {
    }

    public AuthResponse(String token, Long rodzicId, String email, String imie, String nazwisko) {
        this.token = token;
        this.rodzicId = rodzicId;
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getRodzicId() {
        return rodzicId;
    }

    public void setRodzicId(Long rodzicId) {
        this.rodzicId = rodzicId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}