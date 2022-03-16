package com.noiunina.model;

public class Prenotazione {

    public String id, nomeBiblioteca, oraInizio, oraFine, dataPrenotazione;

    public Prenotazione(String id, String nomeBiblioteca, String oraInizio, String oraFine, String dataPrenotazione) {
        this.id = id;
        this.nomeBiblioteca = nomeBiblioteca;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.dataPrenotazione = dataPrenotazione;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeBiblioteca() {
        return nomeBiblioteca;
    }

    public void setNomeBiblioteca(String nomeBiblioteca) {
        this.nomeBiblioteca = nomeBiblioteca;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public String getOraFine() {
        return oraFine;
    }

    public void setOraFine(String oraFine) {
        this.oraFine = oraFine;
    }

    public String getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(String dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }
}
