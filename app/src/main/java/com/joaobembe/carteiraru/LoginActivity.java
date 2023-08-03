package com.joaobembe.carteiraru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.joaobembe.carteiraru.controller.ControladorUsuario;
import com.joaobembe.carteiraru.model.Credenciais;
import com.joaobembe.carteiraru.model.Usuario;
import com.joaobembe.carteiraru.util.Login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exception.ExcecaoErroDeConectividade;
import exception.ExcecaoUsuarioSenhaInvalido;

public class LoginActivity extends AppCompatActivity {

    EditText etUsuario;
    EditText etSenha;
    CheckBox checkBox;
    Button button;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.btEntrar_PaginaInicial);
        progressBar = findViewById(R.id.progressBar);
        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        checkBox = findViewById(R.id.cbSalvarSenha);

        Context context = getApplicationContext(); // ou getContext() em um Fragment

        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        SharedPreferences sharedPreferences = null;
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

        etUsuario.setText(sharedPreferences.getString("usuario", ""));
        etSenha.setText(sharedPreferences.getString("senha", ""));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new Login().fazerLogin(new Credenciais(etUsuario.getText().toString(), etSenha.getText().toString()));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            });

                            if (checkBox.isChecked()){
                                String masterKeyAlias = null;
                                masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                                SharedPreferences sharedPreferences = null;
                                sharedPreferences = EncryptedSharedPreferences.create(
                                        "file",
                                        masterKeyAlias,
                                        context,
                                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                                );
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("usuario", etUsuario.getText().toString());
                                editor.putString("senha", etSenha.getText().toString());
                                editor.apply();
                            }
                            Intent intent = new Intent(LoginActivity.this, PaginaInicialActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (ExcecaoUsuarioSenhaInvalido e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etSenha.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                                    button.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } catch (ExcecaoErroDeConectividade e) {
                            throw new RuntimeException(e);
                        } catch (GeneralSecurityException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}