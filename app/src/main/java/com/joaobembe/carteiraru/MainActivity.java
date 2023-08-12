package com.joaobembe.carteiraru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.joaobembe.carteiraru.controller.ControladorUsuario;
import com.joaobembe.carteiraru.model.Carteira;
import com.joaobembe.carteiraru.model.Credenciais;
import com.joaobembe.carteiraru.model.Perfil;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        String sharedPreferencesFileName = "file";

        File sharedPreferencesFile = new File(context.getFilesDir().getParent() + "/shared_prefs/" + sharedPreferencesFileName + ".xml");
        if (sharedPreferencesFile.exists()) {
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
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }

            if (sharedPreferences.getString("nome", "") != null) {
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

                Intent intent = new Intent(MainActivity.this, PaginaInicialActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        } else {
            Intent intent = new Intent(MainActivity.this, BemVindoActivity.class);
            startActivity(intent);
            finish();
        }
    }
}