package com.noiunina.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IPrenotazioneView;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrenotazionePresenter implements IPrenotazionePresenter{


    public static IPrenotazioneView iPrenotazioneView;

    public PrenotazionePresenter(IPrenotazioneView view){

        iPrenotazioneView = view;

    }

    public PrenotazionePresenter(){

    }

    public String getNomeBiblioteca(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getNomeBiblioteca();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void effettuaPrenotazione(String nomeBiblioteca, String dataPren, String oraInizio, String oraFine){

        LocalDate data = LocalDate.parse(dataPren);
        LocalTime startTime = LocalTime.parse(oraInizio);
        LocalTime endTime = LocalTime.parse(oraFine);

        if(data.isBefore(LocalDate.now())){
            iPrenotazioneView.showDataError();
        }
        else if(endTime.isBefore(startTime)){
            iPrenotazioneView.showTimeError();
        }
        else if(startTime.isBefore(LocalTime.now()) && data.isEqual(LocalDate.now())){
            iPrenotazioneView.showTimeErrorWithCurrentTime();
        }
        else if(oraInizio.equals(oraFine)){
            iPrenotazioneView.showTimeErrorEquals();
        }
        else{
            GestoreRichieste sys = GestoreRichieste.getInstance();
            sys.effettuaPrenotazione(nomeBiblioteca, dataPren, oraInizio, oraFine);

        }

    }


    @Override
    public void showReservationError() {

        iPrenotazioneView.showReservationError();

    }

    @Override
    public void aggiungiPrenotazione(String id, String nomeBiblioteca, String oraInizio, String oraFine, String dataPren) {

        GestoreRichieste sys = GestoreRichieste.getInstance();
        oraInizio = oraInizio+":00";
        oraFine = oraFine+":00";
        sys.addPrenotazione(id, nomeBiblioteca, oraInizio, oraFine, dataPren);

        iPrenotazioneView.showReservationSuccessful();
    }

    @Override
    public void showUserAlreadyReservedError() {
        iPrenotazioneView.showUserAlreadyReservedError();
    }

    @Override
    public void showMaximumOccupancyReachedError() {
        iPrenotazioneView.showMaximumOccupancyReachedError();
    }
}
