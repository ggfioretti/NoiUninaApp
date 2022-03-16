package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IHomeChatView;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeChatPresenter implements IHomeChatPresenter{

    public static IHomeChatView iHomeChatView;

    public HomeChatPresenter(IHomeChatView view){

        iHomeChatView = view;

    }

    public HomeChatPresenter(){

    }

    public void getListaEsami(){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.getSottoscrizioniChat();

    }

    @Override
    public void erroreRestituzioneListaCorsi() {

        iHomeChatView.getSubscriptionActivityFailed();
    }

    @Override
    public void getSubscriptionActivity(String listaCorsi) {

        ArrayList<String> listaEsami;

        GestoreRichieste sys = GestoreRichieste.getInstance();

        listaCorsi = listaCorsi.replace("\"", "");
        listaCorsi = listaCorsi.replace("[","");
        listaCorsi = listaCorsi.replace("]","");

        listaEsami = new ArrayList<>(Arrays.asList(listaCorsi.split(",")));

        sys.setListaSottoscrizioniDisponibili(listaEsami);

        iHomeChatView.getSubscriptionActivitySuccess();
    }

}
