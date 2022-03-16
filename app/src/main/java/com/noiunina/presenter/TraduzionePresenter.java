package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IChatView;
import com.noiunina.view.ITraduzioneView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class TraduzionePresenter implements ITraduzionePresenter{

    public static ITraduzioneView iTraduzioneView;

    public TraduzionePresenter(ITraduzioneView view) {

        iTraduzioneView = view;

    }

    public TraduzionePresenter() {

    }

    public void traduciTesto(String testo){

        GestoreRichieste sys = GestoreRichieste.getInstance();

        sys.effettuaTraduzione(testo);

    }


    @Override
    public void setTraduzione(String traduzione) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(traduzione.trim());
            Iterator<String> keys = jsonObject.keys();

            String key = keys.next();
            JSONObject jsonobject1 = new JSONObject(jsonObject.get(key).toString());
            keys = jsonobject1.keys();

            key = keys.next();
            JSONObject jsonTraduzione = new JSONObject(jsonobject1.get(key).toString());

            String traduzioneTesto = jsonTraduzione.get("translatedText").toString();

            iTraduzioneView.setTraduzione(traduzioneTesto);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setErrorTraduzione() {
        iTraduzioneView.setErrorTraduzione();
    }
}
