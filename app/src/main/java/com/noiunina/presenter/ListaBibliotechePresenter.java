package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IListaBibliotecheView;

import java.util.ArrayList;

public class ListaBibliotechePresenter {

    public static IListaBibliotecheView iListaBibliotecheView;

    public ListaBibliotechePresenter(IListaBibliotecheView view){

        iListaBibliotecheView = view;

    }



    public ArrayList<String> getListaBibliotecheDisponibili(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getListaBibliotecheDisponibili();
    }

    public void setCurrentBiblioteca(String nomeBiblioteca){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.setNomeBiblioteca(nomeBiblioteca);

        iListaBibliotecheView.goToPrenotazioneActivity();

    }


}
