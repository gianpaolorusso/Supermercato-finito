package com.example.utente5academy.supermercato.classi;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by utente on 02/12/17.
 */

public abstract class SaveFileObject {
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private FileOutputStream file;
    private ObjectOutputStream object;
    private FileInputStream inputFile;
    private ObjectInputStream inputObject;

    public void writeFile(JSONObject obj) throws IOException {
        file = new FileOutputStream("oggetto");
        object = new ObjectOutputStream(file);
        object.writeObject(obj);
        object.close();
        file.close();
    }

    public Object readObject() throws IOException {
        inputObject = new ObjectInputStream(new FileInputStream("oggetto"));
        if (inputObject != null) {
            return (Object) inputObject;
        } else
            return null;
    }


    public int numElement() throws IOException {
int num=0;
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
                        Log.i("d",""+jsonObjectTemp.length());
                        Iterator in = (Iterator) jsonObjectTemp.keys();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
        return num;
    }
}