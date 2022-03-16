package com.noiunina.presenter;

public interface IChatPresenter {

    void getListaMessaggiAggiornati(String conversazione);
    void messageSent();
    void erroreInvioMessaggio();
    void erroreAggiornamentoMessaggio();
}
