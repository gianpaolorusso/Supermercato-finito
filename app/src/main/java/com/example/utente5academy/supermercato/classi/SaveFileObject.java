package com.example.utente5academy.supermercato.classi;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by utente on 02/12/17.
 */

public class SaveFileObject {
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private OutputStreamWriter file;
    private Context cx;

    public SaveFileObject(Context c) {
        this.cx = c;
    }

    public void writeFile(String obj) throws IOException {
        file = new OutputStreamWriter(cx.openFileOutput("json.txt", Context.MODE_PRIVATE));
        file.write(obj);
        file.close();
    }

    public JSONObject readObject() throws IOException, JSONException {
        InputStream inputStream = cx.openFileInput("json.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(cx.openFileInput("json.txt"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((receiveString = bufferedReader.readLine()) != null) {
            stringBuilder.append(receiveString);
        }
        JSONObject jsonObject = new JSONObject(String.valueOf(stringBuilder));
        return jsonObject;
    }
}