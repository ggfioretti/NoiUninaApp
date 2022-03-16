package com.noiunina.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.noiunina.R;
import com.noiunina.presenter.DatiPrenotazionePresenter;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DatiPrenotazioneActivity extends AppCompatActivity implements IDatiPrenotazioneView{

    TextView textViewId;
    TextView textViewStudente;
    TextView textViewEmail;
    TextView textViewNomeBiblioteca;
    TextView textViewOrario;
    TextView textViewData;
    TextView textViewDisclaimer;

    ImageView imageViewQRCode;
    ProgressBar progressBar;

    String nomeStudente;
    String cognomeStudente;
    String email;
    String id;
    String nomeBiblioteca;
    String oraInizio;
    String oraFine;
    String dataPren;

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Button btnElimina;

    DatiPrenotazionePresenter datiPrenotazionePresenter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dati_prenotazione);

        textViewId = findViewById(R.id.textView_uid);
        textViewStudente = findViewById(R.id.textView_studente);
        textViewEmail = findViewById(R.id.textView_email);
        textViewNomeBiblioteca = findViewById(R.id.textView_Biblioteca);
        textViewOrario = findViewById(R.id.textView_orario);
        textViewData = findViewById(R.id.textView_data);
        textViewDisclaimer = findViewById(R.id.textView_titlePRENOTAZIONESCADUTA);

        imageViewQRCode = findViewById(R.id.imageView_QRCode);
        progressBar = findViewById(R.id.progressBar);

        btnElimina = findViewById(R.id.buttonEliminaPrenotazione);

        datiPrenotazionePresenter = new DatiPrenotazionePresenter(this);

        nomeStudente = datiPrenotazionePresenter.getNomeStudente();
        cognomeStudente = datiPrenotazionePresenter.getCognomeStudente();
        email = datiPrenotazionePresenter.getEmailStudente();
        id = datiPrenotazionePresenter.getIdPrenotazione();
        nomeBiblioteca = datiPrenotazionePresenter.getNomeBiblioteca();
        oraInizio = datiPrenotazionePresenter.getOraInizio();
        oraFine = datiPrenotazionePresenter.getOraFine();
        dataPren = datiPrenotazionePresenter.getDataPren();

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = Math.min(width, height);
        smallerDimension = smallerDimension * 3;

        qrgEncoder = new QRGEncoder(
                id, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("GenerateQRCode", e.toString());
        }

        textViewId.setText("ID: "+id);
        textViewStudente.setText("Studente: "+nomeStudente+" "+cognomeStudente);
        textViewEmail.setText("Email: "+email);
        textViewNomeBiblioteca.setText("Locazione: "+nomeBiblioteca);
        textViewOrario.setText("Orario: "+oraInizio+"-"+oraFine);
        textViewData.setText("Data: "+dataPren);

        datiPrenotazionePresenter.checkScadenza(oraFine, dataPren);


        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                                datiPrenotazionePresenter.eliminaPrenotazione(id);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(DatiPrenotazioneActivity.this);
                builder.setMessage("Vuoi annullare la tua prenotazione?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });


    }

    @Override
    public void eliminazioneFattila() {
        DatiPrenotazioneActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Non è stato possibile annullare la prenotazione",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void eliminazioneAvvenutaConSuccesso() {
        DatiPrenotazioneActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"La tua prenotazione è stata annullata",Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(getApplicationContext(), HomePrenotazioneActivity.class));
                finish();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void mostraDisclaimer() {
        textViewDisclaimer.setText("PRENOTAZIONE SCADUTA");
    }
}