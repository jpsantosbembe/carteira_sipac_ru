package com.joaobembe.carteiraru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationBarView;
import com.joaobembe.carteiraru.controller.ControladorUsuario;
import com.joaobembe.carteiraru.model.Usuario;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import org.apache.commons.text.StringEscapeUtils;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

public class PaginaInicialActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    QrCodeFragment qrCodeFragment = new QrCodeFragment();
    CardapioFragment cardapioFragment = new CardapioFragment();
    PerfilFragment perfilFragment = new PerfilFragment();
    TextView tvNomeUsuario;
    TextView tvTipoDeVinculo;
    TextView tvCodRu;
    TextView tvSituacao;
    TextView tvRefeicoes;
    ImageView ivFotoPerfil;
    ProgressBar progressBar;

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
        tvCodRu = findViewById(R.id.tvCodRu);
        tvSituacao = findViewById(R.id.tvSituacao);
        tvRefeicoes = findViewById(R.id.tvRefeicoes);
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil);
        progressBar = findViewById(R.id.progressBar2);

        Usuario usuario = new ControladorUsuario().obterUsuario();
        tvNomeUsuario.setText(StringEscapeUtils.unescapeHtml4(formatarNome(usuario.getPerfil().getNomeCompleto())));
        tvTipoDeVinculo.setText(" (" + usuario.getPerfil().getTipoDeVinculo() + ")");
        tvCodRu.setText(usuario.getCarteira().getCodigo());
        tvSituacao.setText(usuario.getPerfil().getSituacaoDoVinculo());

        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(getUnsafeSSLSocketFactory(trustAllCertificates), (X509TrustManager) trustAllCertificates[0])
                .hostnameVerifier((hostname, session) -> true)
                .build();
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();

        if (usuario.getPerfil().getURLFoto() != null) {
            picasso
                    .load(usuario.getPerfil().getURLFoto())
                    .into(ivFotoPerfil, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.INVISIBLE);
                            ivFotoPerfil.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onError(Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            ivFotoPerfil.setImageResource(R.drawable.baseline_account_circle_24);
                            ivFotoPerfil.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    });
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            ivFotoPerfil.setImageResource(R.drawable.baseline_account_circle_24);
            ivFotoPerfil.setVisibility(View.VISIBLE);
        }
        if (usuario.getCarteira().getSaldo() > 1 || usuario.getCarteira().getSaldo() == 0) {
            tvRefeicoes.setText(
                    getResources().getString(R.string.tv_refeicoes_restantes_1) + usuario.getCarteira().getSaldo() + getResources().getString(R.string.tv_refeicoes_restantes_2)
            );
        } else {
            tvRefeicoes.setText(
                    getResources().getString(R.string.tv_refeicoes_restante_1) + usuario.getCarteira().getSaldo() + getResources().getString(R.string.tv_refeicoes_restante_2)
            );
        }
    }
    private static SSLSocketFactory getUnsafeSSLSocketFactory(TrustManager[] trustAllCertificates) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create unsafe SSL socket factory", e);
        }
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
        for (String palavra : palavras) {
            String palavraFormatada = palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase();
            resultado.append(palavraFormatada).append(" ");
        }
        return resultado.toString().trim();
    }

}