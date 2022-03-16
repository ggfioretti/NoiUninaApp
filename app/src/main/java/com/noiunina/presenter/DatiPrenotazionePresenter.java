package com.noiunina.presenter;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IDatiPrenotazioneView;
import com.noiunina.view.IQRCodeView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DatiPrenotazionePresenter implements IDatiPrenotazionePresenter{

    public static IDatiPrenotazioneView iDatiPrenotazioneView;

    public DatiPrenotazionePresenter(IDatiPrenotazioneView view){

        iDatiPrenotazioneView = view;

    }

    public DatiPrenotazionePresenter(){

    }


    public String getNomeStudente(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getNomeStudente();
    }


    public String getCognomeStudente(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getCognomeStudente();
    }


    public String getEmailStudente(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getEmailStudente();
    }


    public String getIdPrenotazione(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getIdPrenotazione();

    }


    public String getNomeBiblioteca(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getNomeBibliotecaPrenotata();
    }


    public String getOraInizio(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getOraInizio();

    }


    public String getOraFine(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getOraFine();

    }


    public String getDataPren(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getDataPren();
    }

    public void eliminaPrenotazione(String id){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.eliminaPrenotazione(id);

    }

    @Override
    public void elimanzionePrenotazioneFallita() {

        iDatiPrenotazioneView.eliminazioneFattila();


    }

    @Override
    public void eliminazionePrenotazioneAvvenutaConSuccesso(String id) {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.rimuoviPrenotazioneDaLista(id);

        iDatiPrenotazioneView.eliminazioneAvvenutaConSuccesso();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkScadenza(String oraFine, String dataPren){

        LocalTime endTime = LocalTime.parse(oraFine);
        LocalDate data = LocalDate.parse(dataPren);

        if((endTime.isBefore(LocalTime.now()) && data.isEqual(LocalDate.now())) || data.isAfter(LocalDate.now())){
            iDatiPrenotazioneView.mostraDisclaimer();
        }

    }
}