package com.noiunina.view;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noiunina.R;
import com.noiunina.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    EditText eEmail;
    EditText ePassword;
    TextView eRegistrati;
    Button eLogin;
    ProgressBar progressBar;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        eEmail = findViewById(R.id.etemail);
        ePassword = findViewById(R.id.etPassword);
        eRegistrati = findViewById(R.id.tvRegistrati);
        eLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);

        presenter = new LoginPresenter(this);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                presenter.effettuaLogin(eEmail.getText().toString(), ePassword.getText().toString());
            }
        });

        eRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }

    @Override
    public void showError() {
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Non è stato possibile effettuare il login",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void getHomeActivity() {
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(),"Login effettuato con successo",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    public void showValidationEmailError() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        eEmail.setError("Inserire una email valida");
    }

    @Override
    public void showValidationPwdError() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        ePassword.setError("Inserire una password valida");
    }

}