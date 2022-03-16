package com.noiunina.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.noiunina.presenter.DatiPrenotazionePresenter;
import com.noiunina.presenter.HomePrenotazionePresenter;
import com.noiunina.presenter.IDatiPrenotazionePresenter;
import com.noiunina.presenter.IHomePrenotazionePresenter;
import com.noiunina.presenter.IPrenotazionePresenter;
import com.noiunina.presenter.IQRCodePresenter;
import com.noiunina.presenter.PrenotazionePresenter;
import com.noiunina.presenter.QRCodePresenter;

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

public class ServizioPrenotazioneAPI {

    private static ServizioPrenotazioneAPI instance = null;

    IHomePrenotazionePresenter iHomePrenotazionePresenter = new HomePrenotazionePresenter();
    IPrenotazionePresenter iPrenotazionePresenter = new PrenotazionePresenter();
    IQRCodePresenter iqrCodePresenter = new QRCodePresenter();
    IDatiPrenotazionePresenter iDatiPrenotazionePresenter = new DatiPrenotazionePresenter();


    public static ServizioPrenotazioneAPI getInstance() {

        if (instance == null) {
            instance = new ServizioPrenotazioneAPI();
        }
        return instance;
    }


    public void recuperaListaBibliotecheDisponibili(String URL_BROKER, String LISTA_BIBLIOTECHE_DISPONIBILI){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+LISTA_BIBLIOTECHE_DISPONIBILI)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iHomePrenotazionePresenter.getListaBibliotecheError();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String url_recupero_lista_biblioteche = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,url_recupero_lista_biblioteche);

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url_recupero_lista_biblioteche)
                            .get()
                            .build();

                    call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iHomePrenotazionePresenter.getListaBibliotecheError();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){

                                String risposta = response.body().string();

                                String TAG1 = "RISPOSTA SERVIZIO GET LISTA BIBLIOTECHE";
                                Log.i(TAG1,risposta);

                                iHomePrenotazionePresenter.setListaBibliotecheDisponibili(risposta);

                            }
                            else{
                                iHomePrenotazionePresenter.getListaBibliotecheError();
                                String TAG1 = "RISPOSTA SERVIZIO GET LISTA BIBLIOTECHE";
                                Log.i(TAG1,"Non e stato possibile ottenere la lista");
                            }
                        }
                    });

                }
                else{
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                    iHomePrenotazionePresenter.getListaBibliotecheError();
                }
            }
        });

    }

    public void checkLibrary(String URL_BROKER, String nomeBiblioteca, String CHECK_NOME_BIBLIOTECA){

        OkHttpClient client = new OkHttpClient();

        RequestBody bibliotecaRequest = new FormBody.Builder()
                .add("nomeBiblioteca", nomeBiblioteca)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+CHECK_NOME_BIBLIOTECA)
                .post(bibliotecaRequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iqrCodePresenter.connectionError();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String url_check_nomeBiblioteca = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,url_check_nomeBiblioteca);

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url_check_nomeBiblioteca)
                            .get()
                            .build();

                    call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iqrCodePresenter.connectionError();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){

                                String risposta = response.body().string();

                                String TAG1 = "RISPOSTA SERVIZIO CHECK NOME BIBLIOTECA";
                                Log.i(TAG1,risposta);

                                iqrCodePresenter.setNomeBiblioteca(risposta);

                            }
                            else{

                                String risposta = response.body().string();

                                if(risposta.equals("Library not found")){
                                    iqrCodePresenter.checkNomeBibliotecaError();
                                }
                                else {
                                    String TAG1 = "RISPOSTA SERVIZIO CHECK NOME BIBLIOTECA";
                                    Log.i(TAG1, "Errore nel check della biblioteca");
                                    iqrCodePresenter.connectionError();
                                }
                            }
                        }
                    });
                }
                else{
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                    iqrCodePresenter.connectionError();
                }
            }
        });

    }

    void prenotazione(String URL_BROKER, String nomeStudente, String cognomeStudente, String emailStudente, String nomeBiblioteca,
                      String oraInizio, String oraFine, String dataPren, String PRENOTAZIONE){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+PRENOTAZIONE)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iPrenotazionePresenter.showReservationError();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String url_prenotazione = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,url_prenotazione);

                    OkHttpClient client = new OkHttpClient();

                    JSONObject prenotazioneDati = new JSONObject();
                    try{

                        prenotazioneDati.put("firstName", nomeStudente);
                        prenotazioneDati.put("lastName", cognomeStudente);
                        prenotazioneDati.put("reserverEmail", emailStudente);
                        prenotazioneDati.put("libraryName", nomeBiblioteca);
                        prenotazioneDati.put("oraInizio", oraInizio);
                        prenotazioneDati.put("oraFine", oraFine);
                        prenotazioneDati.put("dataPren", dataPren);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, prenotazioneDati.toString());

                    Request request = new Request.Builder()
                            .url(url_prenotazione)
                            .post(body)
                            .build();

                    call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iPrenotazionePresenter.showReservationError();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){

                                String id = response.body().string();

                                String TAG1 = "RISPOSTA SERVIZIO PRENOTAZIONE";
                                Log.i(TAG1,id);

                                iPrenotazionePresenter.aggiungiPrenotazione(id, nomeBiblioteca, oraInizio, oraFine, dataPren);


                            }
                            else{

                                String risposta = response.body().string();

                                if(risposta.equals("Utente già prenotato.")){
                                    iPrenotazionePresenter.showUserAlreadyReservedError();
                                }
                                else if(risposta.equals("Raggiunta la capienza massima per la fascia oraria selezionata.")){
                                    iPrenotazionePresenter.showMaximumOccupancyReachedError();
                                }
                                else {
                                    String TAG1 = "RISPOSTA SERVIZIO PRENOTAZIONE";
                                    Log.i(TAG1, "Non e stato possibile effetuare la prenotazione");
                                    iPrenotazionePresenter.showReservationError();
                                }
                            }

                        }
                    });


                }
                else{
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                    iPrenotazionePresenter.showReservationError();
                }
            }
        });

    }

    public void eliminaPrenotazione(String URL_BROKER, String id, String ELIMINA_PRENOTAZIONE){

        OkHttpClient client = new OkHttpClient();

        RequestBody eliminazioneRequest = new FormBody.Builder()
                .add("uuid", id)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+ELIMINA_PRENOTAZIONE)
                .post(eliminazioneRequest)
                .build();

        String TAG1 = "ID";
        Log.i(TAG1,id);

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iDatiPrenotazionePresenter.elimanzionePrenotazioneFallita();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String url_servizio_eliminazione_prenotazione = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,url_servizio_eliminazione_prenotazione);

                    Request request = new Request.Builder()
                            .url(url_servizio_eliminazione_prenotazione)
                            .delete()
                            .build();

                    call = client.newCall(request);


                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iDatiPrenotazionePresenter.elimanzionePrenotazioneFallita();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){

                                String TAG1 = "RISPOSTA SERVIZIO ELIMINAZIONE PRENOTAZIONE";
                                Log.i(TAG1,"Eliminazione prenotazione effettuata");

                                iDatiPrenotazionePresenter.eliminazionePrenotazioneAvvenutaConSuccesso(id);

                            }
                            else{
                                String TAG1 = "RISPOSTA SERVIZIO ELIMINAZIONE PRENOTAZIONE";
                                Log.i(TAG1,"Non e stato possibile eliminare la prenotazione");
                                iDatiPrenotazionePresenter.elimanzionePrenotazioneFallita();
                            }
                        }
                    });

                }
                else{
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                    iDatiPrenotazionePresenter.elimanzionePrenotazioneFallita();
                }
            }
        });

    }

    }

