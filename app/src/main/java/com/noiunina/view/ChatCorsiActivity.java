package com.noiunina.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.noiunina.R;
import com.noiunina.presenter.ChatCorsiPresenter;

import java.util.ArrayList;

public class ChatCorsiActivity extends AppCompatActivity implements IChatCorsiView {

    ListView listView;
    ArrayList<String> listaChatSottoscritte;
    ChatCorsiPresenter presenter;
    ArrayAdapter arrayAdapter;
    TextView disclaimerText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_corsi);

        listView = findViewById(R.id.lista_chat_sottoscritte);
        disclaimerText = findViewById(R.id.disclaimer_lista_chat);

        presenter = new ChatCorsiPresenter(this);
        listaChatSottoscritte = presenter.getChatSottoscritte();

        arrayAdapter = new ArrayAdapter(this, R.layout.lista_layout, listaChatSottoscritte);

        presenter.checkSottoscrizioniEffettuate(listaChatSottoscritte);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.setCurrentChat(listaChatSottoscritte.get(position));
                presenter.getMessageList(listaChatSottoscritte.get(position));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void mostraDisclaimer() {
        disclaimerText.setText("Non hai ancora effettuato sottoscrizzioni a chat. Vai alla sezione 'Sottoscriviti a Gruppi Chat' ed iscriviti ad una chat!");
    }

    @Override
    public void mostraChat() {
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void getChatActivity() {
        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
    }

    @Override
    public void ErrorGetMessages() {
        ChatCorsiActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(),"Non è stato possibile aprire la chat. Riprovare.",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}