package com.joaobembe.carteiraru;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joaobembe.carteiraru.controller.ControladorUsuario;
import com.joaobembe.carteiraru.model.Transacao;
import com.joaobembe.carteiraru.model.Usuario;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_perfil, container, false);
        RecyclerView recyclerView = rootview.findViewById(R.id.recyclerview);

        Usuario usuario = new ControladorUsuario().obterUsuario();
        List<Transacao> todasTransacoes = usuario.getHistoricoTransacoes().getTransacoes();
        List<Item> items = new ArrayList<Item>();

        for (Transacao transacao: todasTransacoes) {
            if (StringEscapeUtils.unescapeHtml4(transacao.getNomeOperacao()).equals("Utilização do Cartão")) {
                items.add(new Item(
                        transacao.getData(),
                        transacao.getHora(),
                        StringEscapeUtils.unescapeHtml4(transacao.getNomeOperacao()),
                        "-1",
                        transacao.getSaldoAnterior(),
                        transacao.getSaldoAtual()));
            }
            if (StringEscapeUtils.unescapeHtml4(transacao.getNomeOperacao()).equals("Compra de Créditos Presencial")) {
                items.add(new Item(
                        transacao.getData(),
                        transacao.getHora(),
                        StringEscapeUtils.unescapeHtml4(transacao.getNomeOperacao()),
                        "+" + transacao.getCreditosGerados(),
                        transacao.getSaldoAnterior(),
                        transacao.getSaldoAtual()));
            }

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        recyclerView.setAdapter(new MyAdapter(getContext(), items));


        return rootview;
    }
}