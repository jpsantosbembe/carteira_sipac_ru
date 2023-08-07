package com.joaobembe.carteiraru;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (items.get(position).getOperacao().equals("Utilização do Cartão")) {
            holder.tvOperacao.setTextColor(Color.parseColor("#F44336"));
            holder.tvQtdeOp.setTextColor(Color.parseColor("#F44336"));
        }
        if (items.get(position).getOperacao().equals("Compra de Créditos Presencial")) {
            holder.tvOperacao.setTextColor(Color.parseColor("#4CAF50"));
            holder.tvQtdeOp.setTextColor(Color.parseColor("#4CAF50"));
        }

        holder.tvData.setText(items.get(position).getData());
        holder.tvHora.setText(items.get(position).getHora());
        holder.tvOperacao.setText(items.get(position).getOperacao());
        holder.tvQtdeOp.setText(items.get(position).getCreditoGerados());

        if (position == items.size() -1 ) {
            holder.linearLayout.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
