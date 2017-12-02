package com.example.utente5academy.supermercato;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProduct extends AppCompatActivity {
    private EditText edmarca;
    private EditText edprezzo;
    private Spinner spinner;
    private Button button;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        edmarca = (EditText) findViewById(R.id.edmarca);
        edprezzo = (EditText) findViewById(R.id.edprezzo);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.inserisci);
        editor = preferences.edit();
        String array[] = {"Carne", "Pesce", "Latte"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array);
        spinner.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edmarca.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Inserire la marca", Toast.LENGTH_SHORT).show();
                }
                if (edprezzo.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Inserire il prezzo", Toast.LENGTH_SHORT).show();
                } else {
                    String marca = edmarca.getText().toString();
                    String tipo = spinner.getSelectedItem().toString();
                    String prezzo = edprezzo.getText().toString();
                    insertEl(tipo, marca, prezzo);
                    try {
                        startActivity();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void startActivity() throws PendingIntent.CanceledException {
        Intent i = new Intent(AddProduct.this, ListProducts.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.send();
    }

    public void insertEl(String tipo, String marca, String prezzo) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = null;
        switch (tipo) {
            case "Carne":
                ref = database.getReferenceFromUrl("https://supermercato-77c93.firebaseio.com/Prodotti/Carne");
                break;
            case "Pesce":
                ref = database.getReferenceFromUrl("https://supermercato-77c93.firebaseio.com/Prodotti/Pesce");
                break;
            case "Latte":
                ref = database.getReferenceFromUrl("https://supermercato-77c93.firebaseio.com/Prodotti/Latte");
                break;
        }
        getLast(tipo, ref);
        int last = 0;
        switch (tipo) {
            case "Pesce":
                last = preferences.getInt("lastPesce", 0);
                break;
            case "Latte":
                last = preferences.getInt("lastLatte", 0);
                break;
            case "Carne":
                last = preferences.getInt("lastCarne", 0);
                break;

        }
        String lastInsert;
        if (last > 9)
            lastInsert = String.valueOf(last);
        else {
            lastInsert = "0" + String.valueOf(last);
        }
        ref.child(lastInsert).child("Marca").setValue(marca);
        ref.child(lastInsert).child("Prezzo").setValue(prezzo);

    }

    int n;

    public void getLast(final String tipo, DatabaseReference myRef) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                n = (int) dataSnapshot.getChildrenCount();
                switch (tipo) {
                    case "Carne":
                        editor.putInt("lastCarne", n);
                        break;
                    case "Pesce":
                        editor.putInt("lastPesce", n);
                        break;
                    case "Latte":
                        editor.putInt("lastLatte", n);
                        break;
                }
                editor.commit();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

}

