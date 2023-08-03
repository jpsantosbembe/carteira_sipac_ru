package com.joaobembe.carteiraru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("usuario.getPerfil().getNomeCompleto()");


        Intent intent = new Intent(this, BemVindoActivity.class);
        startActivity(intent);
    }
}