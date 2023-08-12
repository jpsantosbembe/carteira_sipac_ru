package com.joaobembe.carteiraru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.navigation.NavigationBarView;
import com.joaobembe.carteiraru.controller.ControladorUsuario;
import com.joaobembe.carteiraru.model.Carteira;
import com.joaobembe.carteiraru.model.Credenciais;
import com.joaobembe.carteiraru.model.Perfil;
import com.joaobembe.carteiraru.model.Usuario;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class PaginaInicialActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    QrCodeFragment qrCodeFragment = new QrCodeFragment();
    CardapioFragment cardapioFragment = new CardapioFragment();
    PerfilFragment perfilFragment = new PerfilFragment();
    TextView tvNomeUsuario;
    TextView tvTipoDeVinculo;
    TextView tvMatricula;
    TextView tvCodRu;
    TextView tvSituacao;
    TextView tvRefeicoes;
    ImageView ivFotoPerfil;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.qrcode);

        tvTipoDeVinculo = findViewById(R.id.tvTipoDeVinculo);
        tvNomeUsuario = findViewById(R.id.tvNomeUsuario);
        tvMatricula = findViewById(R.id.tvMatricula);
        tvCodRu = findViewById(R.id.tvCodRu);
        tvSituacao = findViewById(R.id.tvSituacao);
        tvRefeicoes = findViewById(R.id.tvRefeicoes);
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil);
        progressBar = findViewById(R.id.progressBar2);
        swipeRefreshLayout = findViewById(R.id.srvPaginaInicial);

        String masterKeyAlias;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        SharedPreferences sharedPreferences;
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "file",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }


        Perfil perfil = new Perfil(
                sharedPreferences.getString("nome", ""),
                sharedPreferences.getString("tipoDeVinculo", ""),
                sharedPreferences.getString("matricula", ""),
                sharedPreferences.getString("situacao", ""),
                sharedPreferences.getString("urlFotoPerfil", "")
        );
        Carteira carteira = new Carteira(
                sharedPreferences.getString("codigoRu", ""),
                Integer.parseInt(sharedPreferences.getString("saldo", "")),
                sharedPreferences.getString("strQRCode", "")
        );
        Credenciais credenciais = new Credenciais(
                sharedPreferences.getString("usuario", ""),
                sharedPreferences.getString("senha", "")
        );

        new ControladorUsuario().criarNovoUsuario(perfil, carteira, credenciais, null);


        Usuario usuario = new ControladorUsuario().obterUsuario();
        tvNomeUsuario.setText(StringEscapeUtils.unescapeHtml4(formatarNome(usuario.getPerfil().getNomeCompleto())));
        tvMatricula.setText(usuario.getPerfil().getMatricula());
        tvTipoDeVinculo.setText(" (" + usuario.getPerfil().getTipoDeVinculo() + ")");
        tvCodRu.setText(usuario.getCarteira().getCodigo());
        tvSituacao.setText(usuario.getPerfil().getSituacaoDoVinculo());

        Glide.with(this)
                .load(usuario.getPerfil().getURLFoto())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        ivFotoPerfil.setImageResource(R.drawable.baseline_account_circle_24);
                        ivFotoPerfil.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        ivFotoPerfil.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(ivFotoPerfil);

        if (usuario.getCarteira().getSaldo() > 1 || usuario.getCarteira().getSaldo() == 0) {
            tvRefeicoes.setText(
                    getResources().getString(R.string.tv_refeicoes_restantes_1) + usuario.getCarteira().getSaldo() + getResources().getString(R.string.tv_refeicoes_restantes_2)
            );
        } else {
            tvRefeicoes.setText(
                    getResources().getString(R.string.tv_refeicoes_restante_1) + usuario.getCarteira().getSaldo() + getResources().getString(R.string.tv_refeicoes_restante_2)
            );
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.qrcode) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, qrCodeFragment)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.cardapio) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, cardapioFragment)
                    .commit();
            return true;
        }
        if (item.getItemId() == R.id.perfil) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, perfilFragment)
                    .commit();
            return true;
        }
        return false;
    }

    public static String formatarNome(String nomeCompleto) {
        String[] palavras = nomeCompleto.split(" ");
        StringBuilder resultado = new StringBuilder();
        if (palavras.length > 0) {
            String primeiroNome = palavras[0].substring(0, 1).toUpperCase() + palavras[0].substring(1).toLowerCase();
            resultado.append(primeiroNome).append(" ");
        }
        if (palavras.length > 1) {
            String ultimoNome = palavras[palavras.length - 1].substring(0, 1).toUpperCase() + palavras[palavras.length - 1].substring(1).toLowerCase();
            resultado.append(ultimoNome);
        }

        return resultado.toString().trim();
    }

}