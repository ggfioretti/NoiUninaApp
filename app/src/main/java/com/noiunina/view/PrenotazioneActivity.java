package com.noiunina.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noiunina.R;
import com.noiunina.presenter.PrenotazionePresenter;

import java.util.Calendar;

public class PrenotazioneActivity extends AppCompatActivity implements IPrenotazioneView{

    TextView textViewTitoloNomeBiblioteca;
    EditText editTextData;
    DatePickerDialog datePickerDialog;
    Button btnPrenotazione;

    ProgressBar progressBar;
    Spinner spinnerOraInizio;
    Spinner spinnerOraFIne;
    PrenotazionePresenter presenter;
    String nomeBiblioteca;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenotazione);

        textViewTitoloNomeBiblioteca = findViewById(R.id.titoloNomeBiblioteca);
        editTextData = findViewById(R.id.etData);
        spinnerOraInizio = findViewById(R.id.spinnerOraInizio);
        spinnerOraFIne = findViewById(R.id.spinnerOraFine);
        btnPrenotazione = findViewById(R.id.btnPrenotazione);
        progressBar = findViewById(R.id.progressBar);

        presenter = new PrenotazionePresenter(this);
        nomeBiblioteca = presenter.getNomeBiblioteca();

        textViewTitoloNomeBiblioteca.setText("Prenotazione per: "+ nomeBiblioteca);

        editTextData.setInputType(InputType.TYPE_NULL);
        editTextData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int year = cldr.get(Calendar.YEAR);
                int month = cldr.get(Calendar.MONTH);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PrenotazioneActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(month<10 && dayOfMonth<10){
                            editTextData.setText(year + "-" + "0"+(month+1) + "-" + "0"+dayOfMonth);
                        }
                        else if(month<10) {
                            editTextData.setText(year + "-" + "0"+(month+1) + "-" + dayOfMonth);
                        }
                        else if(dayOfMonth<10){
                            editTextData.setText(year + "-" +(month+1) + "-" + "0"+dayOfMonth);
                        }
                        else{
                            editTextData.setText(year + "-" +(month+1) + "-" + dayOfMonth);
                        }
                        }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnPrenotazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    presenter.effettuaPrenotazione(nomeBiblioteca, editTextData.getText().toString(),
                            spinnerOraInizio.getSelectedItem().toString(), spinnerOraFIne.getSelectedItem().toString());
                }
            }
        });

    }

    @Override
    public void showDataError() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        Toast toast = Toast.makeText(getApplicationContext(),"Inserire una data valida a partire da quella corrente",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showTimeError() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        Toast toast = Toast.makeText(getApplicationContext(),"L'ora di fine prenotazione non può essere antecedente all'ora di inizio prenotazione",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showTimeErrorEquals() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        Toast toast = Toast.makeText(getApplicationContext(),"L'ora di fine prenotazione non può essere la stessa dell'ora di inizio prenotazione",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showTimeErrorWithCurrentTime() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        Toast toast = Toast.makeText(getApplicationContext(),"Non puoi effettuare una prenotazione antecedente all'orario attuale",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showReservationError() {
        PrenotazioneActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Impossibile connettersi al servizio.",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void showReservationSuccessful() {
        PrenotazioneActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Prenotazione effettuata con successo",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void showUserAlreadyReservedError() {
        PrenotazioneActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Utente già prenotato per questa fascia oraria",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void showMaximumOccupancyReachedError() {
        PrenotazioneActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Non ci sono posti disponibili per questa fascia oraria",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}