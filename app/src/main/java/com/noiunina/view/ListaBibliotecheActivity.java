package com.noiunina.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.noiunina.R;
import com.noiunina.presenter.ListaBibliotechePresenter;
import com.noiunina.presenter.SubscriptionPresenter;

import java.util.ArrayList;

public class ListaBibliotecheActivity extends AppCompatActivity implements IListaBibliotecheView{

    ListView listView;
    ArrayList<String> listaBiblitoeche;
    ListaBibliotechePresenter presenter;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_biblioteche);

        listView = findViewById(R.id.lista_biblioteche);
        presenter = new ListaBibliotechePresenter(this);
        listaBiblitoeche = presenter.getListaBibliotecheDisponibili();

        arrayAdapter = new ArrayAdapter(this, R.layout.lista_layout, listaBiblitoeche);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.setCurrentBiblioteca(listaBiblitoeche.get(position));
            }
        });

    }

    @Override
    public void goToPrenotazioneActivity() {
        startActivity(new Intent(getApplicationContext(), PrenotazioneActivity.class));
    }
}