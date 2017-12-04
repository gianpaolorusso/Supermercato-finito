package com.example.utente5academy.supermercato.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utente5academy.supermercato.R;
import com.example.utente5academy.supermercato.classi.Prodotti;

import java.util.ArrayList;

/**
 * Created by Utente on 02/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Prodotti> lista;

    public MyAdapter(Context cx, ArrayList<Prodotti> list) {
        this.lista = list;
        this.context = cx;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Prodotti prod = lista.get(position);
        holder.prezzo.setText(prod.getPrezzo());
        holder.marca.setText(prod.getMarca());
        holder.tipo.setText(prod.getTipoProdotto());
        switch (prod.getTipoProdotto()) {
            case "Pesce":holder.img.setImageResource(R.drawable.pesce);break;
            case "Carne":holder.img.setImageResource(R.drawable.carne);break;
            case "Latte":holder.img.setImageResource(R.drawable.latte);break;
        }

    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView prezzo;
        public TextView marca;
        public TextView tipo;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            prezzo = (TextView) itemView.findViewById(R.id.prezzo);
            marca = (TextView) itemView.findViewById(R.id.marca);
            tipo = (TextView) itemView.findViewById(R.id.tipo);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }



}
