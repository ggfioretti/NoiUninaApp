package com.noiunina.model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.noiunina.presenter.ITraduzionePresenter;
import com.noiunina.presenter.TraduzionePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServizioTraduzioneAPI {

    private static ServizioTraduzioneAPI instance = null;

    public static ServizioTraduzioneAPI getInstance() {

        if (instance == null) {
            instance = new ServizioTraduzioneAPI();
        }
        return instance;
    }

    ITraduzionePresenter iTraduzionePresenter = new TraduzionePresenter();

    public void traduzioneTesto(String URL_BROKER, String testo, String traduzioneTesto){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+traduzioneTesto)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iTraduzionePresenter.setErrorTraduzione();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    String url_servizio_traduzione = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1, url_servizio_traduzione);

                    JSONObject traduzioneTestoJson = new JSONObject();
                    try{
                        traduzioneTestoJson.put("q", testo);
                        traduzioneTestoJson.put("target", "it");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, traduzioneTestoJson.toString());

                    Request request = new Request.Builder()
                            .url(url_servizio_traduzione)
                            .addHeader("content-type","application/json")
                            .addHeader("x-rapidapi-host","deep-translate1.p.rapidapi.com")
                            .addHeader("x-rapidapi-key","3fa42a1cebmsh192c14434bddec9p12b3a1jsn5c39ff1c5df6")
                            .post(body)
                            .build();

                    call = client.newCall(request);

                    call.enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                    iTraduzionePresenter.setErrorTraduzione();
                                }
                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {

                                        String risposta = response.body().string();

                                        String TAG1 = "SERVIZIO TRADUZIONE";
                                        Log.i(TAG1,risposta);

                                        iTraduzionePresenter.setTraduzione(risposta);


                                    }
                                    else{
                                        iTraduzionePresenter.setErrorTraduzione();
                                        String TAG1 = "SERVIZIO TRADUZIONE";
                                        Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                                        Log.i(TAG1, response.body().string());
                                    }
                                }
                            }
                    );
                }
                else{
                    iTraduzionePresenter.setErrorTraduzione();
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                }
            }
        });



    }

}
