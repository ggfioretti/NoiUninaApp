package com.noiunina.presenter;

import com.noiunina.model.CredenzialiChat;
import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.ILoginView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LoginPresenter implements ILoginPresenter{

    public static ILoginView ILoginView;

    public LoginPresenter(ILoginView view){

        ILoginView = view;

    }

    public LoginPresenter(){

    }

    public void effettuaLogin(String email, String pwd){

        if(email.isEmpty() | !isEmailValid(email)){
            ILoginView.showValidationEmailError();
        }
        else if(pwd.isEmpty()){
            ILoginView.showValidationPwdError();
        }
        else{
            GestoreRichieste sys = GestoreRichieste.getInstance();
            sys.richiestaLogin(email, pwd);
        }

    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void memorizzaDatiStudente(String uuid, String risposta) {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        try {
            JSONObject jsonDatiUtente = new JSONObject(risposta);
            sys.setStudente(uuid, jsonDatiUtente.get("nome").toString(), jsonDatiUtente.get("cognome").toString(),
                    jsonDatiUtente.get("corso").toString(), jsonDatiUtente.get("email").toString());
            sys.checkSottoscrizioni(uuid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void checkSottoscrizioni(String risposta) {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.inizializzazioneArrayListSottoscrizioniStudenteVuota();

        if(!risposta.equals("null")){

            try {
                JSONObject jsonObject = new JSONObject(risposta.trim());
                Iterator<String> keys = jsonObject.keys();

                while(keys.hasNext()) {
                    String key = keys.next();

                    sys.addCredenzialiChatStudente(key,jsonObject.get(key).toString());

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        sys.checkPrenotazioni();

    }

    @Override
    public void prenotazioniTrovate(String risposta) {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.inizializzazioneArrayListPrenotazioniStudenteVuota();

        JSONArray jsonarray;
        try {
            jsonarray = new JSONArray(risposta);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String id = jsonobject.getString("id");
                String nomeBiblioteca = jsonobject.getString("libraryName");
                String oraInizio = jsonobject.getString("oraInizio");
                String oraFine = jsonobject.getString("oraFine");
                String dataPren = jsonobject.getString("dataPren");
                sys.addPrenotazione(id,nomeBiblioteca,oraInizio,oraFine,dataPren);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ILoginView.getHomeActivity();

    }

    @Override
    public void prenotazioniNonTrovate() {

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.inizializzazioneArrayListPrenotazioniStudenteVuota();

        ILoginView.getHomeActivity();

    }

    @Override
    public void loginFallito() {
        ILoginView.showError();
    }
}

