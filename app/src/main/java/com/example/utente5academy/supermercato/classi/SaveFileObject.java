package com.example.utente5academy.supermercato.classi;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by utente on 02/12/17.
 */

public  class SaveFileObject {
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private OutputStreamWriter file;
    private FileInputStream inputFile;
    private ObjectInputStream inputObject;
    private   int num=0;
    Context cx;

    public SaveFileObject(Context c)
    {
        this.cx=c;
    }
   public  void writeFile(String obj) throws IOException {
        file = new OutputStreamWriter(cx.openFileOutput("json.txt", Context.MODE_PRIVATE));
        file.write(obj);
        file.close();
    }

 public  JSONObject readObject() throws IOException, JSONException {
     InputStream inputStream = cx.openFileInput("json.txt");
     InputStreamReader inputStreamReader=new InputStreamReader(cx.openFileInput("json.txt"));
     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
     String receiveString = "";
     StringBuilder stringBuilder = new StringBuilder();

     while ( (receiveString = bufferedReader.readLine()) != null ) {
         stringBuilder.append(receiveString);}
         JSONObject jsonObject=new JSONObject(String.valueOf(stringBuilder));
     return jsonObject;
    }


    public void numElement() throws IOException {

        asyncHttpClient.get("https://supermercato-77c93.firebaseio.com/Prodotti.json", new AsyncHttpResponseHandler() {
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
                        jsonObjectTemp = new JSONObject(og);
                       ret(jsonObjectTemp.length());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
    }
    private  void ret(int n)
    {
        num=n;
    }
    public int getNum()
    {
        return num;
    }

}