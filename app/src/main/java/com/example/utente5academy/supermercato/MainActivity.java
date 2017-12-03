package com.example.utente5academy.supermercato;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.utente5academy.supermercato.classi.SaveFileObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String utente;
    private SharedPreferences preferences;
    private TextView messaggio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messaggio = (TextView) findViewById(R.id.messaggio);
        Button reigistrati = (Button) findViewById(R.id.registrati);
        Button mostra = (Button) findViewById(R.id.mostra);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        utente = preferences.getString("UtenteLoggato", "");

        mostra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListProducts.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),1,i,PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

            }
        });
        reigistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void utentelog() {
        utente = preferences.getString("UtenteLoggato", "");
        if (utente != "") {
            messaggio.setText("Benvenuto " + utente);

        } else
            messaggio.setText("Benvenuto OSPITE ");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        utentelog();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
