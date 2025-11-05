package com.example.legiaakademia.models;

public class RequestModels {

    public static class LogowanieRequest {
        private String email;
        private String haslo;

        public LogowanieRequest(String email, String haslo) {
            this.email = email;
            this.haslo = haslo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHaslo() {
            return haslo;
        }

        public void setHaslo(String haslo) {
            this.haslo = haslo;
        }
    }

    public static class RejestracjaRequest {
        private String email;
        private String haslo;
        private String imie;
        private String nazwisko;
        private String telefon;

        public RejestracjaRequest(String email, String haslo, String imie,
                                  String nazwisko, String telefon) {
            this.email = email;
            this.haslo = haslo;
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.telefon = telefon;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHaslo() {
            return haslo;
        }

        public void setHaslo(String haslo) {
            this.haslo = haslo;
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

        public String getTelefon() {
            return telefon;
        }

        public void setTelefon(String telefon) {
            this.telefon = telefon;
        }
    }

    public static class DzieckoRequest {
        private String imie;
        private String nazwisko;
        private String dataUrodzenia;
        private String pesel;
        private Long kategoriaId;

        public DzieckoRequest(String imie, String nazwisko, String dataUrodzenia,
                              String pesel, Long kategoriaId) {
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.dataUrodzenia = dataUrodzenia;
            this.pesel = pesel;
            this.kategoriaId = kategoriaId;
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
    }
}