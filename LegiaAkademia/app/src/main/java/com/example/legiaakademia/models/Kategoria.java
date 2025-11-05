package com.example.legiaakademia.models;

public class Kategoria {
    private Long id;
    private String nazwa;
    private int rokOd;
    private int rokDo;
    private String opis;

    public Kategoria() {
    }

    public Kategoria(Long id, String nazwa, int rokOd, int rokDo, String opis) {
        this.id = id;
        this.nazwa = nazwa;
        this.rokOd = rokOd;
        this.rokDo = rokDo;
        this.opis = opis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getRokOd() {
        return rokOd;
    }

    public void setRokOd(int rokOd) {
        this.rokOd = rokOd;
    }

    public int getRokDo() {
        return rokDo;
    }

    public void setRokDo(int rokDo) {
        this.rokDo = rokDo;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}