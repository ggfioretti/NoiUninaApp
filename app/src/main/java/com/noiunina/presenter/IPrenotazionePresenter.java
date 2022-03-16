package com.noiunina.presenter;

import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface IPrenotazionePresenter {

    void showReservationError();
    void aggiungiPrenotazione(String id, String nomeBiblioteca, String oraInizio, String oraFine, String dataPren);
    void showUserAlreadyReservedError();
    void showMaximumOccupancyReachedError();
}
