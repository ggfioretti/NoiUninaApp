package com.noiunina.view;

public interface IPrenotazioneView {

    void showDataError();
    void showTimeError();
    void showTimeErrorEquals();
    void showTimeErrorWithCurrentTime();
    void showReservationError();
    void showReservationSuccessful();
    void showUserAlreadyReservedError();
    void showMaximumOccupancyReachedError();
}
