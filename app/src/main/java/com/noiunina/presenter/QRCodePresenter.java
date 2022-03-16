package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IQRCodeView;

import org.json.JSONException;
import org.json.JSONObject;

public class QRCodePresenter implements IQRCodePresenter{

    public static IQRCodeView iqrCodeView;

    public QRCodePresenter(IQRCodeView view){

        iqrCodeView = view;

    }

    public QRCodePresenter(){

    }

    public void checkNomeBiblioteca(String nomeBiblioteca){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.checkNomeBiblioteca(nomeBiblioteca);

    }

    @Override
    public void checkNomeBibliotecaError() {

        iqrCodeView.showErrorQRCode();

    }

    @Override
    public void setNomeBiblioteca(String risposta) {

        GestoreRichieste sys = GestoreRichieste.getInstance();
        String nomeBiblioteca;

        try {
            JSONObject jsonobject = new JSONObject(risposta);
            nomeBiblioteca = jsonobject.get("name").toString();
            sys.setNomeBiblioteca(nomeBiblioteca);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iqrCodeView.getPrenotazioneActivity();

    }

    @Override
    public void connectionError() {
        iqrCodeView.connectionError();
    }
}
