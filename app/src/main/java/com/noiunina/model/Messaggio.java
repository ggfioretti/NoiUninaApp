package com.noiunina.model;

public class Messaggio {

    public String mittente;
    public String testo;
    public String uid;

    Messaggio(String mittente, String testo, String uid){

        this.mittente = mittente;
        this.testo = testo;
        this.uid = uid;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
