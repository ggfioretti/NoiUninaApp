package com.noiunina.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.noiunina.R;
import com.noiunina.presenter.HomeChatPresenter;

public class HomeChatActivity extends AppCompatActivity implements IHomeChatView{

    Button buttonSottoscrivi;
    Button buttonListaChat;
    ProgressBar progressBar;
    HomeChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chat);

        buttonSottoscrivi = findViewById(R.id.buttonSottoscritiviChat);
        buttonListaChat = findViewById(R.id.buttonIniziaChat);
        presenter = new HomeChatPresenter(this);
        progressBar = findViewById(R.id.progressBar);

        buttonSottoscrivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                presenter.getListaEsami();
            }
        });

        buttonListaChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatCorsiActivity.class));
            }
        });
    }

    @Override
    public void getSubscriptionActivityFailed() {
        HomeChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Non è stato possibile ottenere la lista chat da sottoscrivere", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
    }

    @Override
    public void getSubscriptionActivitySuccess() {
        HomeChatActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
            }
        }
        );

    }

}