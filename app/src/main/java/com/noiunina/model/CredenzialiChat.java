package com.noiunina.model;

public class CredenzialiChat {

    private String esame;
    private String codice;

    public CredenzialiChat(String esame, String codice) {
        this.esame = esame;
        this.codice = codice;
    }

    public String getEsame() {
        return esame;
    }

    public void setEsame(String esame) {
        this.esame = esame;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }
}
