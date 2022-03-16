package com.noiunina.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IChatView;
import com.noiunina.view.IListaPrenotazioniView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ListaPrenotazioniPresenter {

    public static IListaPrenotazioniView iListaPrenotazioniView;

    public ListaPrenotazioniPresenter(IListaPrenotazioniView view) {

        iListaPrenotazioniView = view;

    }

    public ArrayList<String> getListaNomiBiblioteca(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getListaNomiBiblioteca();
    }

    public ArrayList<String> getListaId(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getListaId();

    }

    public void setPrenotazioneDaVisualizzare(String id){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.setPrenotazioneDaVisualizzare(id);

        iListaPrenotazioniView.goToDatiPrenotazioneActivity();


    }

    public void checkPrenotazioni(ArrayList<String> listaId){

        if(listaId.isEmpty()){
            iListaPrenotazioniView.mostraDisclaimer();
        }
        else{
            iListaPrenotazioniView.mostraPrenotazioni();
        }


    }



}
