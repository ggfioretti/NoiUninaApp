package com.noiunina.presenter;

import android.util.Log;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IHomePrenotazioneView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePrenotazionePresenter implements IHomePrenotazionePresenter{

    public static IHomePrenotazioneView iHomePrenotazioneView;

    public HomePrenotazionePresenter(IHomePrenotazioneView view) {

        iHomePrenotazioneView = view;

    }

    public HomePrenotazionePresenter() {

    }

    public void getListaBiblioteche(){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.prendiListaBibliotecheDisponibili();

    }


    @Override
    public void getListaBibliotecheError() {
        iHomePrenotazioneView.showGetListaBibliotecheError();
    }

    @Override
    public void setListaBibliotecheDisponibili(String listaBibliotecheDisponibili) {

        ArrayList<String> listaBilbioteche = new ArrayList<>();

        JSONArray jsonarray;
        try {
            jsonarray = new JSONArray(listaBibliotecheDisponibili);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                listaBilbioteche.add(jsonobject.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.setListaBibliotecheDisponibili(listaBilbioteche);

        iHomePrenotazioneView.goToListaBibliotecheActivity();

    }
}
