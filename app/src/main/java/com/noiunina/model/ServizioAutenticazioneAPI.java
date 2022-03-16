package com.noiunina.model;
import android.util.Log;

import androidx.annotation.NonNull;

import com.noiunina.presenter.ILoginPresenter;
import com.noiunina.presenter.IRegisterPresenter;
import com.noiunina.presenter.LoginPresenter;
import com.noiunina.presenter.RegisterPresenter;

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

public class ServizioAutenticazioneAPI {

    private static ServizioAutenticazioneAPI instance = null;

    IRegisterPresenter iRegisterPresenter = new RegisterPresenter();
    ILoginPresenter iLoginPresenter = new LoginPresenter();

    public static ServizioAutenticazioneAPI getInstance() {

        if (instance == null) {
            instance = new ServizioAutenticazioneAPI();
        }
        return instance;

    }

    public void login(String email, String pwd, String URL_Broker, String SIGNIN){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_Broker+"/"+SIGNIN)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iLoginPresenter.loginFallito();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    String url_servizio_login = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1, url_servizio_login);

                    RequestBody formBody = new FormBody.Builder()
                            .add("email", email)
                            .add("password", pwd)
                            .build();

                    Request request = new Request.Builder()
                            .url(url_servizio_login)
                            .post(formBody)
                            .build();

                    call = client.newCall(request);

                    call.enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                    iLoginPresenter.loginFallito();
                                }
                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {

                                        String getDataUser = "UserData";

                                        try {
                                            String uuid = new JSONObject(response.body().string()).get("localId").toString();
                                            String TAG1 = "SERVIZIO LOGIN";
                                            Log.i(TAG1,"UIID utente - "+ uuid);
                                            getDatiUtente(URL_Broker, getDataUser, uuid);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        iLoginPresenter.loginFallito();
                                        String TAG1 = "SERVIZIO LOGIN";
                                        Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                                    }
                                }
                            }
                    );
                }
                else{
                    iLoginPresenter.loginFallito();
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                }
            }
        });
    }

    public void getDatiUtente(String URL_BROKER, String getDataUser, String uuid){

        OkHttpClient client = new OkHttpClient();

        RequestBody uuidrequest = new FormBody.Builder()
                .add("uuid", uuid)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+getDataUser)
                .post(uuidrequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iLoginPresenter.loginFallito();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String url_servizio_userData = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1, url_servizio_userData);

                    Request request = new Request.Builder()
                            .url(url_servizio_userData)
                            .get()
                            .build();

                    call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iLoginPresenter.loginFallito();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {

                                iLoginPresenter.memorizzaDatiStudente(uuid, response.body().string());

                            }
                            else{
                                iLoginPresenter.loginFallito();
                                String TAG1 = "RISPOSTA GETDATA";
                                Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                            }
                        }
                    });
                }
                else {
                    iLoginPresenter.loginFallito();
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                }
            }
        });
    }

    public void checkSottoscrizioni(String URL_BROKER, String uuid, String checkSottoscrizioni){

        OkHttpClient client = new OkHttpClient();

        RequestBody uuidrequest = new FormBody.Builder()
                .add("uuid", uuid)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+checkSottoscrizioni)
                .post(uuidrequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iLoginPresenter.loginFallito();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String url_servizio_checkSottoscrizioni = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1, url_servizio_checkSottoscrizioni);

                    Request request = new Request.Builder()
                            .url(url_servizio_checkSottoscrizioni)
                            .get()
                            .build();

                    call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iLoginPresenter.loginFallito();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {

                                String risposta = response.body().string();

                                String TAG1 = "RISPOSTA CHECK";
                                Log.i(TAG1,risposta);

                                iLoginPresenter.checkSottoscrizioni(risposta);

                            }
                            else{
                                iLoginPresenter.loginFallito();
                                String TAG1 = "RISPOSTA GETDATA";
                                Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                            }
                        }
                    });
                }
                else {
                    iLoginPresenter.loginFallito();
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                }
            }
        });
    }

    public void checkPrenotazioni(String URL_BROKER, String email, String CHECK_PRENOTAZIONI){

        OkHttpClient client = new OkHttpClient();

        RequestBody checkPrenotazioniRequest = new FormBody.Builder()
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+CHECK_PRENOTAZIONI)
                .post(checkPrenotazioniRequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                iLoginPresenter.loginFallito();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){

                    String url_check_prenotazioni = response.body().string();

                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,url_check_prenotazioni);

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url_check_prenotazioni)
                            .get()
                            .build();

                    call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            iLoginPresenter.loginFallito();

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){

                                String risposta = response.body().string();

                                String TAG1 = "RISPOSTA SERVIZIO CHECK PRENOTAZIONI";
                                Log.i(TAG1,risposta);

                                iLoginPresenter.prenotazioniTrovate(risposta);

                            }
                            else{
                                String risposta = response.body().string();

                                if(risposta.equals("No reservation exists for this email")){

                                    String TAG1 = "RISPOSTA SERVIZIO CHECK PRENOTAZIONI";
                                    Log.i(TAG1, risposta);

                                    iLoginPresenter.prenotazioniNonTrovate();

                                }
                                else {
                                    String TAG1 = "RISPOSTA SERVIZIO CHECK PRENOTAZIONI";
                                    Log.i(TAG1, "Non e stato possibile effetuare il check delle prenotazioni");
                                    iLoginPresenter.loginFallito();
                                }
                            }

                        }
                    });

                }
                else{
                    String TAG1 = "RISPOSTA BROKER";
                    Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                    iLoginPresenter.loginFallito();
                }

            }
        });

    }

    public void registrazione(String nome, String cognome, String corso, String email, String pwd, String URL_BROKER, String SIGNUP){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+SIGNUP)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        iRegisterPresenter.registrazioneFallita();
                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String url_servizio_reg = response.body().string();

                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1, url_servizio_reg);

                            RequestBody formBody = new FormBody.Builder()
                                    .add("email", email)
                                    .add("password", pwd)
                                    .build();

                            Request request = new Request.Builder()
                                    .url(url_servizio_reg)
                                    .post(formBody)
                                    .build();

                            call = client.newCall(request);

                            call.enqueue(
                                    new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                            e.printStackTrace();
                                            iRegisterPresenter.registrazioneFallita();
                                        }
                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                            if (response.isSuccessful()) {

                                                String risposta = response.body().string();

                                                String TAG1 = "SERVIZIO REGISTRAZIONE";
                                                Log.i(TAG1,"Registrazione effettuata!");

                                                String setDataUser = "UserData";

                                                JSONObject jsonDataUser;
                                                try {
                                                    jsonDataUser = new JSONObject(risposta);
                                                    Object jsonuuid = jsonDataUser.get("localId");
                                                    String uuid = jsonuuid.toString();
                                                    Log.i(TAG1,"UUID ottenuto: "+ uuid);
                                                    setDatiUtente(nome, cognome, corso, email, URL_BROKER, setDataUser, uuid);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else{
                                                iRegisterPresenter.registrazioneFallita();
                                                String TAG1 = "SERVIZIO REGISTRAZIONE";
                                                Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                                            }
                                        }
                                    }
                            );
                        }
                        else{
                            iRegisterPresenter.registrazioneFallita();
                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                        }
                    }
                }
        );
    }

    public void setDatiUtente(String nome, String cognome, String corso, String email, String URL_BROKER, String setDataUser, String uuid){

        OkHttpClient client = new OkHttpClient();

        RequestBody uuidrequest = new FormBody.Builder()
                .add("uuid", uuid)
                .build();

        Request request = new Request.Builder()
                .url(URL_BROKER+"/"+setDataUser)
                .post(uuidrequest)
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        iRegisterPresenter.registrazioneFallita();
                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String url_servizio_setData = response.body().string();

                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1, url_servizio_setData);

                            JSONObject userField = new JSONObject();
                            try{
                                userField.put("nome", nome);
                                userField.put("cognome", cognome);
                                userField.put("corso", corso);
                                userField.put("email", email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(JSON, userField.toString());

                            Request request = new Request.Builder()
                                    .url(url_servizio_setData)
                                    .put(body)
                                    .build();

                            call = client.newCall(request);

                            call.enqueue(
                                    new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                            e.printStackTrace();
                                            iRegisterPresenter.registrazioneFallita();
                                        }
                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response){
                                            if (response.isSuccessful()) {

                                                String TAG1 = "SERVIZIO SET DATI UTENTE";
                                                Log.i(TAG1,"Dati utente settati con successo!");

                                                iRegisterPresenter.registrazioneEseguitaConSuccesso();
                                            }
                                            else{
                                                iRegisterPresenter.registrazioneFallita();
                                                String TAG1 = "SERVIZIO SET DATI UTENTEE";
                                                Log.i(TAG1,"Non e stato possibile effetuare il settaggio");
                                            }
                                        }
                                    }
                            );
                        }
                        else{
                            iRegisterPresenter.registrazioneFallita();
                            String TAG1 = "RISPOSTA BROKER";
                            Log.i(TAG1,"Non e stato possibile effetuare la richiesta");
                        }
                    }
                }
        );
    }
}
