package com.noiunina.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.noiunina.R;
import com.noiunina.presenter.SubscriptionPresenter;

import java.util.ArrayList;

public class SubscriptionActivity extends AppCompatActivity implements ISubscriptionView{

    ListView listView;
    ArrayList<String> listaEsami;
    SubscriptionPresenter presenter;
    ArrayAdapter arrayAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.lista_sottoscrizioni);
        presenter = new SubscriptionPresenter(this);
        listaEsami = presenter.getListaEsami();


        arrayAdapter = new ArrayAdapter(this, R.layout.lista_layout, listaEsami);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.checkSottoscrizione(listaEsami.get(position));
            }
        });

    }

    @Override
    public void sottoscrizioneFallita() {
        SubscriptionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Non è stato possibile effetuare la sottoscrizione", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    );
    }

    @Override
    public void sottoscrizioneEffettuata() {
        SubscriptionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Sottoscrizione effettuata con successo", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
    }

    @Override
    public void eliminazioneSottoscrizioneFallita() {
        SubscriptionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Non è stato possibile eliminare la sottoscrizione", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
    }

    @Override
    public void eliminazioneSottoscrizioneEffettuata() {
        SubscriptionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Sottoscrizione eliminata con successo", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
    }

    @Override
    public void checkSottoscrizioneTrue(String esame) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                        presenter.eliminaSottoscrizione(esame);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sei già iscritto alla chat: '"+esame+"'. Vuoi annullare l'iscrizione?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public void checkSottoscrizioneFalse(String esame) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                        presenter.effettuaSottoscrizione(esame);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vuoi iscriverti alla chat: '"+esame+"'?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}