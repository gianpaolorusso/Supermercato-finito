package com.example.utente5academy.supermercato;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.utente5academy.supermercato.adapter.MyAdapter;
import com.example.utente5academy.supermercato.classi.Prodotti;
import com.example.utente5academy.supermercato.classi.SaveFileObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class ListProducts extends AppCompatActivity {
    private SaveFileObject saveFileObject;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter adapter;
    private ArrayList<Prodotti> listaPrdotti;
    private com.loopj.android.http.AsyncHttpClient asyncHttpClient;
    public ArrayList<Prodotti> carne;
    public ArrayList<Prodotti> Prodotti;
    private  JSONObject obj;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        saveFileObject=new SaveFileObject(getApplicationContext());
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        asyncHttpClient = new com.loopj.android.http.AsyncHttpClient();
        if(network(getApplicationContext()))
        {
            getDati();}
            else{
            Toast.makeText(getApplicationContext(),"Rete non disponibile",Toast.LENGTH_LONG).show();
        try {
            obj=saveFileObject.readObject();
                Iterator chiavi = obj.keys();
                while (chiavi.hasNext()) {
                    JSONObject jsonObjectTemp = null;
                    String og = (String) chiavi.next();
                    jsonObjectTemp = (JSONObject) obj.get(og);
                   Iterator secondChiavi=jsonObjectTemp.keys();
                    while(secondChiavi.hasNext()){

                        String ogIn=(String)secondChiavi.next();
                        JSONObject jsonObjectTemp2= (JSONObject) jsonObjectTemp.get(ogIn);
                        Prodotti prodTemp = new Prodotti();
                        prodTemp.setPrezzo(jsonObjectTemp2.getString("Prezzo"));
                        prodTemp.setMarca(jsonObjectTemp2.getString("Marca"));
                        prodTemp.setTipoProdotto(og);
                        listaPrdotti.add(prodTemp);
                        adapter.notifyDataSetChanged();}
                }

            } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

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
        });}

public boolean network(Context cx)
{
    ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(cx.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
    return networkInfo !=null;
}

    public void getDati() {

        asyncHttpClient.get("https://supermercato-77c93.firebaseio.com/Prodotti/.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONObject jsonObject = null;

                String tree = new String(responseBody);
                try {
                    jsonObject = new JSONObject(tree);
                    saveFileObject.writeFile(tree);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator secondChiavi=null;
                Iterator chiavi = jsonObject.keys();
                try {
                    while (chiavi.hasNext()) {
                        JSONObject jsonObjectTemp = null;
                        String og = (String) chiavi.next();
                        jsonObjectTemp = (JSONObject) jsonObject.get(og);
                        secondChiavi=jsonObjectTemp.keys();
                        while(secondChiavi.hasNext()){
                        String ogIn=(String)secondChiavi.next();
                        JSONObject jsonObjectTemp2= (JSONObject) jsonObjectTemp.get(ogIn);
                        Prodotti prodTemp = new Prodotti();
                        prodTemp.setPrezzo(jsonObjectTemp2.getString("Prezzo"));
                        prodTemp.setMarca(jsonObjectTemp2.getString("Marca"));
                        prodTemp.setTipoProdotto(og);
                        listaPrdotti.add(prodTemp);
                        adapter.notifyDataSetChanged();
                    }
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

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ListProducts.this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,i,PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}


