package com.joaobembe.carteiraru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardapioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardapioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardapioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardapioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardapioFragment newInstance(String param1, String param2) {
        CardapioFragment fragment = new CardapioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String json = "{\n" +
                "    \"data_inicial\": \"2023-08-01\",\n" +
                "    \"data_final\": \"2023-08-31\",\n" +
                "    \"cardapio_semanal\": {\n" +
                "        \"segunda-feira\": {\n" +
                "            \"prato_principal\": \"Frango assado\",\n" +
                "            \"ovolactovegetariana\": \"Lasanha de berinjela\",\n" +
                "            \"acompanhamentos\": [\"Arroz branco\", \"Feijão preto\", \"Batata assada\"],\n" +
                "            \"guarnicao\": [\"Salada verde\", \"Legumes refogados\"],\n" +
                "            \"sobremesa\": [\"Pudim\", \"Torta de limão\"],\n" +
                "            \"suco\": [\"Laranja\", \"Limão\"]\n" +
                "        },\n" +
                "        \"terça-feira\": {\n" +
                "            \"prato_principal\": \"Peixe grelhado\",\n" +
                "            \"ovolactovegetariana\": \"Quiche de espinafre\",\n" +
                "            \"acompanhamentos\": [\"Arroz integral\", \"Feijão carioca\", \"Abóbora cozida\"],\n" +
                "            \"guarnicao\": [\"Salada de alface\", \"Couve refogada\"],\n" +
                "            \"sobremesa\": [\"Mousse de maracujá\", \"Sorvete de morango\"],\n" +
                "            \"suco\": [\"Abacaxi\", \"Maçã\"]\n" +
                "        },\n" +
                "        \"quarta-feira\": {\n" +
                "            \"prato_principal\": \"Bife acebolado\",\n" +
                "            \"ovolactovegetariana\": \"Escondidinho de cogumelos\",\n" +
                "            \"acompanhamentos\": [\"Arroz com brócolis\", \"Feijão mulatinho\", \"Mandioca frita\"],\n" +
                "            \"guarnicao\": [\"Salada de rúcula\", \"Tomate e cebola\"],\n" +
                "            \"sobremesa\": [\"Gelatina\", \"Brownie de chocolate\"],\n" +
                "            \"suco\": [\"Uva\", \"Melancia\"]\n" +
                "        },\n" +
                "        \"quinta-feira\": {\n" +
                "            \"prato_principal\": \"Feijoada\",\n" +
                "            \"ovolactovegetariana\": \"Feijoada de legumes\",\n" +
                "            \"acompanhamentos\": [\"Arroz branco\", \"Couve à mineira\", \"Farofa\"],\n" +
                "            \"guarnicao\": [\"Torresmo\", \"Laranja fatiada\"],\n" +
                "            \"sobremesa\": [\"Arroz doce\", \"Salada de frutas\"],\n" +
                "            \"suco\": [\"Morango\", \"Caju\"]\n" +
                "        },\n" +
                "        \"sexta-feira\": {\n" +
                "            \"prato_principal\": \"Frango xadrez\",\n" +
                "            \"ovolactovegetariana\": \"Tofu ao molho de amendoim\",\n" +
                "            \"acompanhamentos\": [\"Arroz com cenoura\", \"Feijão fradinho\", \"Brócolis cozido\"],\n" +
                "            \"guarnicao\": [\"Salada de escarola\", \"Pimentão refogado\"],\n" +
                "            \"sobremesa\": [\"Canjica\", \"Pavê de chocolate\"],\n" +
                "            \"suco\": [\"Goiaba\", \"Pêssego\"]\n" +
                "        }\n" +
                "    }\n" +
                "}";

        View rootView = inflater.inflate(R.layout.fragment_cardapio, container, false);

        LinearLayout.LayoutParams params8 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params8.setMargins(0, 8, 0, 0);
        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params5.setMargins(0, 5, 0, 0);
        LinearLayout.LayoutParams params16 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params16.setMargins(0, 16, 0, 0);

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject cardapioSemanal = jsonObject.getJSONObject("cardapio_semanal");
            JSONObject cardapioDia = cardapioSemanal.getJSONObject("segunda-feira");

            LinearLayout linearLayout = rootView.findViewById(R.id.llCardapio);

            TextView tvPratoPrincipal = new TextView(getContext());
            String pratoPrincipal = cardapioDia.getString("prato_principal");
            tvPratoPrincipal.setText(pratoPrincipal);
            tvPratoPrincipal.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleMedium);
            linearLayout.addView(tvPratoPrincipal);

            TextView tvSegundaOpcaoLb = new TextView(getContext());
            tvSegundaOpcaoLb.setText("2ª Opção:");
            tvSegundaOpcaoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
            tvSegundaOpcaoLb.setLayoutParams(params8);
            linearLayout.addView(tvSegundaOpcaoLb);

            TextView tvSegundaOpcao = new TextView(getContext());
            String ovolactovegetariana = cardapioDia.getString("ovolactovegetariana");
            tvSegundaOpcao.setText(ovolactovegetariana);
            linearLayout.addView(tvSegundaOpcao);

            TextView tvAcompanhamentoLb = new TextView(getContext());
            tvAcompanhamentoLb.setText("Acompanhamento:");
            tvAcompanhamentoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
            tvAcompanhamentoLb.setLayoutParams(params16);
            linearLayout.addView(tvAcompanhamentoLb);

            JSONArray acompanhamentos = cardapioDia.getJSONArray("acompanhamentos");
            for (int i = 0; i < acompanhamentos.length(); i++) {
                TextView tvAcompanhamentos = new TextView(getContext());
                tvAcompanhamentos.setText(acompanhamentos.getString(i));
                linearLayout.addView(tvAcompanhamentos);
            }

            TextView tvGuarnicaoLb = new TextView(getContext());
            tvGuarnicaoLb.setText("Guarnicao:");
            tvGuarnicaoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
            tvGuarnicaoLb.setLayoutParams(params16);
            linearLayout.addView(tvGuarnicaoLb);

            JSONArray guarnicao = cardapioDia.getJSONArray("guarnicao");
            for (int i = 0; i < guarnicao.length(); i++) {
                TextView tvGuarnicao = new TextView(getContext());
                tvGuarnicao.setText(guarnicao.getString(i));
                linearLayout.addView(tvGuarnicao);
            }

            TextView tvSobremesaLb = new TextView(getContext());
            tvSobremesaLb.setText("Sobremesa:");
            tvSobremesaLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
            tvSobremesaLb.setLayoutParams(params16);
            linearLayout.addView(tvSobremesaLb);

            JSONArray sobremesa = cardapioDia.getJSONArray("sobremesa");
            for (int i = 0; i < sobremesa.length(); i++) {
                TextView tvSobremesa = new TextView(getContext());
                tvSobremesa.setText(sobremesa.getString(i));
                linearLayout.addView(tvSobremesa);
            }

            TextView tvSucoLb = new TextView(getContext());
            tvSucoLb.setText("Suco:");
            tvSucoLb.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleSmall);
            tvSucoLb.setLayoutParams(params16);
            linearLayout.addView(tvSucoLb);

            JSONArray suco = cardapioDia.getJSONArray("suco");
            for (int i = 0; i < suco.length(); i++) {
                TextView tvSuco = new TextView(getContext());
                tvSuco.setText(suco.getString(i));
                linearLayout.addView(tvSuco);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}