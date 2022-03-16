package com.noiunina.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.noiunina.R;

import com.noiunina.presenter.ListaPrenotazioniPresenter;

import java.util.ArrayList;

public class ListaPrenotazioniActivity extends AppCompatActivity implements IListaPrenotazioniView{

    ArrayList<String> listaNomiBiblioteca;
    ArrayList<String> listaId;

    ListView listView;
    ArrayAdapter arrayAdapter;

    TextView textViewDisclaimer;

    ListaPrenotazioniPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prenotazioni);

        presenter = new ListaPrenotazioniPresenter(this);

        listView = findViewById(R.id.lista_prenotazioniEffettuate);
        textViewDisclaimer = findViewById(R.id.disclaimer_lista_prenotazioni);

        listaNomiBiblioteca = presenter.getListaNomiBiblioteca();
        listaId = presenter.getListaId();

        arrayAdapter = new ArrayAdapter(this, R.layout.lista_layout, listaNomiBiblioteca);

        presenter.checkPrenotazioni(listaId);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.setPrenotazioneDaVisualizzare(listaId.get(position));
            }
        });


    }

    @Override
    public void goToDatiPrenotazioneActivity() {
        startActivity(new Intent(getApplicationContext(), DatiPrenotazioneActivity.class));
    }

    @Override
    public void mostraDisclaimer() {
        textViewDisclaimer.setText("Non ci sono prenotazioni effettuate. Accedi alla lista biblioteca o scansiona un QRCode per iniziare una prenotazione.");
    }

    @Override
    public void mostraPrenotazioni() {
        listView.setAdapter(arrayAdapter);
    }


}