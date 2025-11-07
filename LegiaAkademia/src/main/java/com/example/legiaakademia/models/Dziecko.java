package com.example.legiaakademia.models;

public class Dziecko {
    private Long id;
    private String imie;
    private String nazwisko;
    private String dataUrodzenia;
    private String pesel;
    private Long kategoriaId;
    private String kategoriaNazwa;

    public Dziecko() {
    }

    public Dziecko(Long id, String imie, String nazwisko, String dataUrodzenia,
                   String pesel, Long kategoriaId, String kategoriaNazwa) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzenia = dataUrodzenia;
        this.pesel = pesel;
        this.kategoriaId = kategoriaId;
        this.kategoriaNazwa = kategoriaNazwa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Long getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(Long kategoriaId) {
        this.kategoriaId = kategoriaId;
    }

    public String getKategoriaNazwa() {
        return kategoriaNazwa;
    }

    public void setKategoriaNazwa(String kategoriaNazwa) {
        this.kategoriaNazwa = kategoriaNazwa;
    }
}