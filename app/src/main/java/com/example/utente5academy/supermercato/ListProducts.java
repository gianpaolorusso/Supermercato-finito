package com.example.utente5academy.supermercato;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.utente5academy.supermercato.adapter.MyAdapter;
import com.example.utente5academy.supermercato.classi.Prodotti;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class ListProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter adapter;
    private ArrayList<Prodotti> listaPrdotti;
    private com.loopj.android.http.AsyncHttpClient asyncHttpClient;
    public ArrayList<Prodotti> carne;
    public ArrayList<Prodotti> Prodotti;
    private RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        listaPrdotti = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(getApplicationContext(), listaPrdotti);
        recyclerView.setAdapter(adapter);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        asyncHttpClient = new com.loopj.android.http.AsyncHttpClient();
        params = new RequestParams();
        getDati("Carne");
        getDati("Pesce");
        getDati("Latte");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListProducts.this, AddProduct.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getDati(final String tipo) {
        asyncHttpClient.get("https://supermercato-77c93.firebaseio.com/Prodotti/" + tipo + ".json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONObject jsonObject = null;

                String tree = new String(responseBody);
                try {
                    jsonObject = new JSONObject(tree);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator chiavi = jsonObject.keys();
                try {
                    while (chiavi.hasNext()) {
                        JSONObject jsonObjectTemp = null;
                        String og = (String) chiavi.next();
                        jsonObjectTemp = (JSONObject) jsonObject.get(og);
                        Prodotti prodTemp = new Prodotti();
                        prodTemp.setPrezzo(jsonObjectTemp.getString("Prezzo"));
                        prodTemp.setMarca(jsonObjectTemp.getString("Marca"));
                        prodTemp.setTipoProdotto(tipo);
                        listaPrdotti.add(prodTemp);
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable
                                          error) {
                Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();
            }


        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter.notifyDataSetChanged();
    }
}


