package com.noiunina.view;

public interface ISubscriptionView {

    void sottoscrizioneFallita();
    void sottoscrizioneEffettuata();
    void eliminazioneSottoscrizioneFallita();
    void eliminazioneSottoscrizioneEffettuata();
    void checkSottoscrizioneTrue(String esame);
    void checkSottoscrizioneFalse(String esame);
}
