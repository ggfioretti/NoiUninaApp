package com.noiunina.presenter;

public interface ISubscriptionPresenter {

    void sottoscrizioneFallita();
    void setSottoscrizione(String esame, String codiceEsame);
    void eliminazioneSottoscrizioneFallita();
    void deleteSottoscrizione(String esame);
}
