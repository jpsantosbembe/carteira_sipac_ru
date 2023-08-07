package com.joaobembe.carteiraru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItems;

    public SliderAdapter(List<SliderItem> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        LinearLayout.LayoutParams params8 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params8.setMargins(0, 8, 0, 0);
        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params5.setMargins(0, 5, 0, 0);
        LinearLayout.LayoutParams params16 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params16.setMargins(0, 16, 0, 0);

        LinearLayout linearLayout = holder.llCardapio.findViewById(R.id.llCardapio);

        TextView tvPratoPrincipal = new TextView(holder.itemView.getContext());
        String pratoPrincipal = sliderItems.get(position).getPratoPrincipal().toString();
        tvPratoPrincipal.setText(pratoPrincipal);
        tvPratoPrincipal.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleMedium);
        linearLayout.addView(tvPratoPrincipal);

        TextView tvSegundaOpcaoLb = new TextView(holder.itemView.getContext());
        tvSegundaOpcaoLb.setText("2ª Opção:");
        tvSegundaOpcaoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
        tvSegundaOpcaoLb.setLayoutParams(params8);
        linearLayout.addView(tvSegundaOpcaoLb);

        TextView tvSegundaOpcao = new TextView(holder.itemView.getContext());
        String ovolactovegetariana = sliderItems.get(position).getSegundaOpcao().toString();
        tvSegundaOpcao.setText(ovolactovegetariana);
        linearLayout.addView(tvSegundaOpcao);

        TextView tvAcompanhamentoLb = new TextView(holder.itemView.getContext());
        tvAcompanhamentoLb.setText("Acompanhamento:");
        tvAcompanhamentoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
        tvAcompanhamentoLb.setLayoutParams(params16);
        linearLayout.addView(tvAcompanhamentoLb);

        for (int i = 0; i < sliderItems.get(position).getAcompanhamentos().size(); i++) {
            TextView tvAcompanhamentos = new TextView(holder.itemView.getContext());
            tvAcompanhamentos.setText(sliderItems.get(position).getAcompanhamentos().get(i));
            linearLayout.addView(tvAcompanhamentos);
        }

        TextView tvGuarnicaoLb = new TextView(holder.itemView.getContext());
        tvGuarnicaoLb.setText("Guarnicao:");
        tvGuarnicaoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
        tvGuarnicaoLb.setLayoutParams(params16);
        linearLayout.addView(tvGuarnicaoLb);

        for (int i = 0; i < sliderItems.size(); i++) {
            TextView tvGuarnicao = new TextView(holder.itemView.getContext());
            tvGuarnicao.setText(sliderItems.get(position).getGuarnicao().get(i));
            linearLayout.addView(tvGuarnicao);
        }

        TextView tvSobremesaLb = new TextView(holder.itemView.getContext());
        tvSobremesaLb.setText("Sobremesa:");
        tvSobremesaLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
        tvSobremesaLb.setLayoutParams(params16);
        linearLayout.addView(tvSobremesaLb);

        for (int i = 0; i < sliderItems.size(); i++) {
            TextView tvSobremesa = new TextView(holder.itemView.getContext());
            tvSobremesa.setText(sliderItems.get(position).getSobremesa().get(i));
            linearLayout.addView(tvSobremesa);
        }

        TextView tvSucoLb = new TextView(holder.itemView.getContext());
        tvSucoLb.setText("Suco:");
        tvSucoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
        tvSucoLb.setLayoutParams(params16);
        linearLayout.addView(tvSucoLb);

        for (int i = 0; i < sliderItems.size(); i++) {
            TextView tvSuco = new TextView(holder.itemView.getContext());
            tvSuco.setText(sliderItems.get(position).getSuco().get(i));
            linearLayout.addView(tvSuco);
        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llCardapio;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            llCardapio = itemView.findViewById(R.id.llCardapio);
        }
    }
}
