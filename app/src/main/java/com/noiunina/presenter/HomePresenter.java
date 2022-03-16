package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IHomeView;

public class HomePresenter {

    public static IHomeView iHomeView;

    public HomePresenter(IHomeView view){

        iHomeView = view;

    }

    public HomePresenter(){

    }

    public void getCredenziali(){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        String nomeStudente = sys.getNomeStudente();
        String cognomeStudente = sys.getCognomeStudente();

        iHomeView.mostraCredenziali(nomeStudente, cognomeStudente);

    }

}
