package com.example.utente5academy.supermercato;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        Button salva = (Button) findViewById(R.id.salva);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        edit = (EditText) findViewById(R.id.username);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString() != "") {
                    editor.putString("UtenteLoggato", edit.getText().toString());
                    editor.commit();
                    registrationUser(edit.getText().toString(), lastUser());
                    Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }

                } else
                    Toast.makeText(getApplicationContext(), "Inserire un username", Toast.LENGTH_SHORT).show();
            }

        });

    }

    int n;

    public int lastUser() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://supermercato-77c93.firebaseio.com/Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                n = (int) dataSnapshot.getChildrenCount() + 1;
                editor.putInt("last", n);
                editor.commit();
                ret(n);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return 0;
    }

    public int ret(int r) {
        return n = r;
    }

    //  public void lastInsert

    public void registrationUser(String username, int nu) {
        n++;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://supermercato-77c93.firebaseio.com/");
        String last;
        if (preferences.getInt("last", 0) <= 9) {
            last = "0" + String.valueOf(preferences.getInt("last", 0));
        }
        else{
            last =String.valueOf(preferences.getInt("last", 0));
        }
        myRef.child("Users").child(last).setValue(username);
        Toast.makeText(getApplicationContext(), "Username aggiunto al database", Toast.LENGTH_SHORT).show();

    }
}
