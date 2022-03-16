package com.noiunina.model;

import android.util.Log;
import androidx.annotation.NonNull;

import com.noiunina.presenter.ChatCorsiPresenter;
import com.noiunina.presenter.ChatPresenter;
import com.noiunina.presenter.HomeChatPresenter;
import com.noiunina.presenter.IChatPresenter;
import com.noiunina.presenter.IHomeChatPresenter;
import com.noiunina.presenter.ISubscriptionPresenter;
import com.noiunina.presenter.SubscriptionPresenter;
import com.noiunina.presenter.IChatCorsiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServizioMessaggisticaAPI {

    private static ServizioMessaggisticaAPI instance = null;

    IHomeChatPresenter iHomeChatPresenter = new HomeChatPresenter();
    ISubscriptionPresenter iSubscriptionPresenter = new SubscriptionPresenter();
    IChatCorsiPresenter iChatCorsiPresenter = new ChatCorsiPresenter();
    IChatPresenter iChatPresenter = new ChatPresenter();

    public static ServizioMessaggisticaAPI getInstance() {

        if (instance == null) {
            instance = new ServizioMessaggisticaAPI();
        }
        return instance;

    }

    public String URL_CHAT_CORRENTE;

    public void recuperaListaCorsi(String URL_BROKER, String corso, String LISTACORSI){

        OkHttpClient client = new OkHttpClient();

        RequestBody corsorequest = new FormBody.Builder()
                .add("corso", corso)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+LISTACORSI)
                .post(corsorequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iHomeChatPresenter.erroreRestituzioneListaCorsi();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    String url_servizio_recuperaListaCorsi = response.body().string();

                    url_servizio_recuperaListaCorsi = url_servizio_recuperaListaCorsi.replace("+","%20");

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1, url_servizio_recuperaListaCorsi);

                    Request request = new Request.Builder()
                            .url(url_servizio_recuperaListaCorsi)
                            .get()
                            .build();

                    call = client.newCall(request);

                    call.enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                    iHomeChatPresenter.erroreRestituzioneListaCorsi();
                                }
                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {

                                        String listaCorsi = response.body().string();

                                        String TAG = "Servizio Richiesta Lista Corsi";
                                        Log.i(TAG, listaCorsi);

                                        iHomeChatPresenter.getSubscriptionActivity(listaCorsi);

                                    }
                                    else{
                                        iHomeChatPresenter.erroreRestituzioneListaCorsi();
                                        String TAG1 = "SERVIZIO RECUPERO CORSI DISPONIBILI";
                                        Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                                    }
                                }
                            }
                    );
                }
                else{
                    iHomeChatPresenter.erroreRestituzioneListaCorsi();
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                }
            }
        });
    }

    public void prendiCredenziali(String URL_BROKER, String uuid, String esame, String corsoDiStudio, String getCredenziali){

        OkHttpClient client = new OkHttpClient();

        RequestBody corsorequest = new FormBody.Builder()
                .add("esame", esame)
                .add("corsoDiStudio", corsoDiStudio)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+getCredenziali)
                .post(corsorequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    iSubscriptionPresenter.sottoscrizioneFallita();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String url_getCodice = response.body().string();

                        String TAG1 = "RISPOSTA BROKER";
                        Log.i(TAG1, url_getCodice);

                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url(url_getCodice)
                                .get()
                                .build();

                        call = client.newCall(request);

                        call.enqueue(
                                new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        if (response.isSuccessful()) {
                                            String codice = response.body().string();

                                            String TAG1 = "RISPOSTA SERVIZIO SOTTOSCRIZIONE";
                                            Log.i(TAG1, "E' stato prelevato il seguente codice: "+codice);

                                            String sottoscriviStudente = "sottoscriviStudente";
                                            sottoscriviStudenteAChat(URL_BROKER, uuid, sottoscriviStudente, codice, esame);

                                        }
                                        else{
                                            String TAG1 = "RISPOSTA SERVIZIO SOTTOSCRIZIONE";
                                            Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                                        }
                                    }
                                }
                        );
                    }
                    else{
                        iSubscriptionPresenter.sottoscrizioneFallita();
                        String TAG1 = "RISPOSTA BROKER";
                        Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                    }
                    }
                }
            );
    }

    public void sottoscriviStudenteAChat(String URL_BROKER, String uuid, String sottoscriviStudente, String codice, String esame){

        OkHttpClient client = new OkHttpClient();

        RequestBody corsorequest = new FormBody.Builder()
                .add("uuid", uuid)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+sottoscriviStudente)
                .post(corsorequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String url_sottoscrizioneStudenteAChat = response.body().string();

                            String TAG1 = "RISPOSTA SERVIZIO SOTTOSCRIZIONE";
                            Log.i(TAG1, url_sottoscrizioneStudenteAChat);

                            String codiceEsame = codice.replace("\"", "");

                            JSONObject userSub = new JSONObject();
                            try{
                                userSub.put(esame, codiceEsame);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(JSON, userSub.toString());

                            Request request = new Request.Builder()
                                    .url(url_sottoscrizioneStudenteAChat)
                                    .patch(body)
                                    .build();

                            call = client.newCall(request);

                            call.enqueue(
                                    new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                            iSubscriptionPresenter.sottoscrizioneFallita();
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                            if (response.isSuccessful()) {
                                                String TAG1 = "RISPOSTA SERVIZIO SOTTOSCRIZIONE";
                                                Log.i(TAG1,"Sottoscrizione esame: "+esame+" - codice: "+codice+" effettuata");

                                                iSubscriptionPresenter.setSottoscrizione(esame, codiceEsame);


                                            }
                                            else{
                                                String TAG1 = "RISPOSTA SERVIZIO SOTTOSCRIZIONE";
                                                Log.i(TAG1,"Non e stato possibile effetuare la sottoscrizione");
                                            }
                                        }
                                    }
                                );
                        }
                        else{
                            iSubscriptionPresenter.sottoscrizioneFallita();
                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                        }
                    }
                }
            );
    }

    public void cancellaSottoscrizione(String URL_BROKER, String uuid, String esame, String deleteSottoscrizione) {

        OkHttpClient client = new OkHttpClient();

        RequestBody corsorequest = new FormBody.Builder()
                .add("uuid", uuid)
                .add("esame", esame)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+deleteSottoscrizione)
                .post(corsorequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        iSubscriptionPresenter.eliminazioneSottoscrizioneFallita();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            String url_cancellazioneSottoscrizione = response.body().string();

                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,url_cancellazioneSottoscrizione);

                            Request request = new Request.Builder()
                                    .url(url_cancellazioneSottoscrizione)
                                    .delete()
                                    .build();

                            call = client.newCall(request);

                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    iSubscriptionPresenter.eliminazioneSottoscrizioneFallita();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if(response.isSuccessful()){

                                        String TAG1 = "RISPOSTA SERVIZIO ELIMINAZIONE SOTTOSCRIZIONE";
                                        Log.i(TAG1,"La chat e' stata eliminata correttamente");

                                        iSubscriptionPresenter.deleteSottoscrizione(esame);

                                    }
                                    else{
                                        iSubscriptionPresenter.eliminazioneSottoscrizioneFallita();
                                        String TAG1 = "RISPOSTA SERVIZIO ELIMINAZIONE SOTTOSCRIZIONE";
                                        Log.i(TAG1,"Non e' stato possibile eliminare la chat");
                                    }
                                }
                            });
                        }
                        else{
                            iSubscriptionPresenter.eliminazioneSottoscrizioneFallita();
                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                        }
                    }
             });
    }

    public void getMessageList(String URL_BROKER, String codice, String URL_CHAT){

        OkHttpClient client = new OkHttpClient();

        RequestBody chatrequest = new FormBody.Builder()
                .add("codice", codice)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+URL_CHAT)
                .post(chatrequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        iChatCorsiPresenter.ErrorGetMessages();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            URL_CHAT_CORRENTE = response.body().string();

                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,URL_CHAT_CORRENTE);

                            Request request = new Request.Builder()
                                    .url(URL_CHAT_CORRENTE)
                                    .get()
                                    .build();

                            call = client.newCall(request);

                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    iChatCorsiPresenter.ErrorGetMessages();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if(response.isSuccessful()){

                                        String conversazioneChat = response.body().string();
                                        iChatCorsiPresenter.setConversazione(conversazioneChat);

                                    }
                                    else{
                                        iChatCorsiPresenter.ErrorGetMessages();
                                        String TAG1 = "RISPOSTA SERVIZIO GET LISTA CHAT";
                                        Log.i(TAG1,"Non e' stato possibile ottenere la lista chat");
                                    }
                                }
                            });
                        }
                        else{
                            iChatCorsiPresenter.ErrorGetMessages();
                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                        }
                    }
                });
    }

    public void invioMessaggio(String testo, String mittente, String uid){

        OkHttpClient client = new OkHttpClient();

        JSONObject messaggioJson = new JSONObject();
        try{
            messaggioJson.put("messaggio", testo);
            messaggioJson.put("mittente", mittente);
            messaggioJson.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, messaggioJson.toString());

        Request request = new Request.Builder()
                .url(URL_CHAT_CORRENTE)
                .post(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iChatPresenter.erroreInvioMessaggio();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String risposta = response.body().string();
                    String TAG1 = "RISPOSTA SERVIZIO INVIO LISTA CHAT";
                    Log.i(TAG1,risposta);

                    iChatPresenter.messageSent();

                    refreshMessageList();

                }
                else{
                    String TAG1 = "RISPOSTA SERVIZIO INVIO LISTA CHAT";
                    Log.i(TAG1,"Non è stato possibile aggiornare la chat");
                    iChatPresenter.erroreInvioMessaggio();
                }

            }
        });


    }

    public void refreshMessageList(){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_CHAT_CORRENTE)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iChatPresenter.erroreAggiornamentoMessaggio();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String conversazioneChat = response.body().string();
                    String TAG1 = "RISPOSTA SERVIZIO AGGIORNA LISTA CHAT";
                    Log.i(TAG1,conversazioneChat);

                    iChatPresenter.getListaMessaggiAggiornati(conversazioneChat);

                }
                else{
                    String TAG1 = "RISPOSTA SERVIZIO AGGIORNA LISTA CHAT";
                    Log.i(TAG1,"Non è stato possibile aggiornare la chat");
                    iChatPresenter.erroreAggiornamentoMessaggio();
                }

            }
        });


    }

}
