package com.joaobembe.carteiraru;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joaobembe.carteiraru.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvData;
    TextView tvHora;
    TextView tvOperacao;
    TextView tvQtdeOp;
    LinearLayout linearLayout;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvData = itemView.findViewById(R.id.tvData);
        tvHora = itemView.findViewById(R.id.tvHora);
        tvOperacao = itemView.findViewById(R.id.tvOperacao);
        tvQtdeOp = itemView.findViewById(R.id.tvQtdeOp);
        linearLayout = itemView.findViewById(R.id.linearLayout);
    }
}
