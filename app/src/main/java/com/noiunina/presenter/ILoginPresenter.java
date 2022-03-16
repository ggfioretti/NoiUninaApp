package com.noiunina.presenter;

public interface ILoginPresenter {

    void memorizzaDatiStudente(String uuid, String risposta);
    void checkSottoscrizioni(String risposta);
    void prenotazioniTrovate(String risposta);
    void prenotazioniNonTrovate();
    void loginFallito();

}
