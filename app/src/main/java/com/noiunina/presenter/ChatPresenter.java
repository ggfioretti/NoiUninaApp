package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IChatView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatPresenter implements IChatPresenter{

    public static IChatView iChatView;

    public ChatPresenter(IChatView view) {

        iChatView = view;

    }

    public ChatPresenter() {

    }

    public void getCurrentChatTitle(){

        String currentChatTitle;

        GestoreRichieste sys = GestoreRichieste.getInstance();
        currentChatTitle = sys.getCurrentChatTitle();

        iChatView.getCurrentChatTitle(currentChatTitle);

    }

    public void invioMessaggio(String testoMessaggio){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.inviaMessaggio(testoMessaggio);

    }

    public ArrayList<String> getListaMittenti(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getListaMittenti();
    }


    public ArrayList<String> getListaMessaggi(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getListaTestoMessaggi();
    }

    public ArrayList<String> getListaUid(){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        return sys.getListaUid();
    }

    public void aggiornaListaMessaggi(){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.aggiornaListaMessaggi();

    }

    @Override
    public void getListaMessaggiAggiornati(String conversazione) {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        String messaggio;
        String mittente;
        String uid;

        sys.inizializzaConversazione();

        if (conversazione != null) {

            try {
                JSONObject jsonObject = new JSONObject(conversazione.trim());
                Iterator<String> keys = jsonObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();

                    JSONObject jsonMessaggio = new JSONObject(jsonObject.get(key).toString());
                    mittente = jsonMessaggio.get("mittente").toString();
                    messaggio = jsonMessaggio.get("messaggio").toString();
                    uid = jsonMessaggio.get("uid").toString();

                    sys.addMessaggioSuLista(mittente, messaggio, uid);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> listaTestoMessaggi;
        ArrayList<String> listaMittenti;
        ArrayList<String> listaUid;

        listaTestoMessaggi = sys.getListaTestoMessaggi();
        listaMittenti = sys.getListaMittenti();
        listaUid = sys.getListaUid();

        iChatView.refreshRecyclerView(listaTestoMessaggi, listaMittenti, listaUid);
    }

    @Override
    public void messageSent() {
        iChatView.clearEditText();
    }

    @Override
    public void erroreInvioMessaggio() {

        iChatView.errorInvioMessaggio();
    }

    @Override
    public void erroreAggiornamentoMessaggio() {

        iChatView.erroreAggiornamentoMessaggio();
    }
}
