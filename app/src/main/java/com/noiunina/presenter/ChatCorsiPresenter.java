package com.noiunina.presenter;

import android.util.Log;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IChatCorsiView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatCorsiPresenter implements IChatCorsiPresenter {

    public static IChatCorsiView iChatCorsiView;

    public ChatCorsiPresenter(IChatCorsiView view) {

        iChatCorsiView = view;

    }

    public ChatCorsiPresenter() {

    }

    public ArrayList<String> getChatSottoscritte() {

       GestoreRichieste sys = GestoreRichieste.getInstance();
       ArrayList<String> listaChatSottoscritte;
       listaChatSottoscritte = sys.getListaChatSottoscritte();

        return listaChatSottoscritte;

    }

    public void setCurrentChat(String chat){

        GestoreRichieste sys = GestoreRichieste.getInstance();
        sys.setCurrentChat(chat);

    }

    public void getMessageList(String chat){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        ArrayList<String> listaChatSottoscritte = sys.getListaChatSottoscritte();
        ArrayList<String> listaCodiciChatSottoscritte = sys.getListaCodiciChatSottoscritte();

        String codice = null;

        int i = 0;
        boolean trovato = false;

        while(i<listaChatSottoscritte.size() && !trovato){
            if(chat.equals(listaChatSottoscritte.get(i))){
                codice = listaCodiciChatSottoscritte.get(i);
                trovato = true;
            }
            else{
                i++;
            }
        }

        sys.getMessageList(codice);

    }

    public void checkSottoscrizioniEffettuate(ArrayList<String> listaChatSottoscritte){

        if(listaChatSottoscritte.isEmpty()) {
            iChatCorsiView.mostraDisclaimer();
        }
        else{
            iChatCorsiView.mostraChat();
        }
    }

    @Override
    public void setConversazione(String conversazioneChat) {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        String messaggio;
        String mittente;
        String uid;

        sys.inizializzaConversazione();

        if (conversazioneChat != null) {

            try {
                JSONObject jsonObject = new JSONObject(conversazioneChat.trim());
                Iterator<String> keys = jsonObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();

                    JSONObject jsonMessaggio = new JSONObject(jsonObject.get(key).toString());
                    mittente = jsonMessaggio.get("mittente").toString();
                    messaggio = jsonMessaggio.get("messaggio").toString();
                    uid = jsonMessaggio.get("uid").toString();

                    Log.i("setConversazione", mittente);
                    Log.i("setConversazione", messaggio);

                    sys.addMessaggioSuLista(mittente, messaggio, uid);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        iChatCorsiView.getChatActivity();

    }

    @Override
    public void ErrorGetMessages() {
        iChatCorsiView.ErrorGetMessages();
    }
}

