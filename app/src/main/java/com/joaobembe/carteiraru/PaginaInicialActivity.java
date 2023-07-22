package com.joaobembe.carteiraru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;


public class PaginaInicialActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    QrCodeFragment qrCodeFragment = new QrCodeFragment();
    CardapioFragment cardapioFragment = new CardapioFragment();
    PerfilFragment perfilFragment = new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(this::onNavigationItemSelected);
        navigationBarView.setSelectedItemId(R.id.qrcode);

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
}