package com.noiunina.view;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noiunina.R;
import com.noiunina.presenter.ChatPresenter;
import com.noiunina.presenter.CustomAdapter;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements IChatView{

    ChatPresenter presenter;
    TextView titoloChat;
    Button btnInvia;
    EditText editText;
    RecyclerView recyclerView;
    Button btnAggiorna;

    ArrayList<String> listaMessaggi;
    ArrayList<String> listaMittenti;
    ArrayList<String> listaUid;

    public static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        titoloChat = findViewById(R.id.titolo_chat);
        btnInvia = findViewById(R.id.btnSend);
        btnAggiorna = findViewById(R.id.buttonAggiorna);
        editText = findViewById(R.id.textInput);

        presenter = new ChatPresenter(this);
        presenter.getCurrentChatTitle();

        listaMittenti = presenter.getListaMittenti();
        listaMessaggi = presenter.getListaMessaggi();
        listaUid = presenter.getListaUid();

        adapter = new CustomAdapter(this, listaMessaggi, listaMittenti, listaUid);
        recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.aggiornaListaMessaggi();
            }
        });

        btnInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.invioMessaggio(editText.getText().toString());
            }
        });

    }

    @Override
    public void getCurrentChatTitle(String chatTitle) {
        titoloChat.setText(chatTitle);
    }

    @UiThread
    @Override
    public void refreshRecyclerView(ArrayList<String> listaTestoMessaggi, ArrayList<String> listaMittenti, ArrayList<String> listaUid) {

        this.listaMessaggi = listaTestoMessaggi;
        this.listaMittenti = listaMittenti;
        this.listaUid = listaUid;

        ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new CustomAdapter(getApplicationContext(), listaMessaggi, listaMittenti, listaUid);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void clearEditText() {
        ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.getText().clear();
            }
        }
        );
    }

    @Override
    public void errorInvioMessaggio() {
        ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(),"Non è stato possibile inviare il messaggio.",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void erroreAggiornamentoMessaggio() {
        ChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(),"Non è stato possibile aggiornare la conversazione.",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}