package com.joaobembe.carteiraru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.joaobembe.carteiraru.model.Credenciais;
import com.joaobembe.carteiraru.util.Login;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
    TextView tvUsuarioSenhaInvalido;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.btEntrar_PaginaInicial);
        progressBar = findViewById(R.id.progressBar);
        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        checkBox = findViewById(R.id.cbSalvarSenha);
        tvUsuarioSenhaInvalido = findViewById(R.id.tvUsuarioSenhaInvalido);

        Context context = getApplicationContext();

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

        etUsuario.setText(sharedPreferences.getString("usuario", ""));
        etSenha.setText(sharedPreferences.getString("senha", ""));
        button.setOnClickListener(view -> {

            if (etUsuario.getText().toString().equals("") && etSenha.getText().toString().equals("")) {
                etUsuario.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                etSenha.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                tvUsuarioSenhaInvalido.setText("Informe o usuário e senha!");
                tvUsuarioSenhaInvalido.setVisibility(View.VISIBLE);
            } else if (etSenha.getText().toString().equals("")){
                etUsuario.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                etSenha.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                tvUsuarioSenhaInvalido.setText("Informe a senha!");
                tvUsuarioSenhaInvalido.setVisibility(View.VISIBLE);
            } else if (etUsuario.getText().toString().equals("")) {
                etUsuario.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                etSenha.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                tvUsuarioSenhaInvalido.setText("Informe o usuário!");
                tvUsuarioSenhaInvalido.setVisibility(View.VISIBLE);
            } else {
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(() -> {
                    try {
                        runOnUiThread(() -> {
                            etSenha.setEnabled(false);
                            etUsuario.setEnabled(false);
                            button.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            tvUsuarioSenhaInvalido.setVisibility(View.INVISIBLE);
                            etSenha.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                            etUsuario.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        });

                        new Login().fazerLogin(new Credenciais(etUsuario.getText().toString(), etSenha.getText().toString()));

                        if (checkBox.isChecked()){
                            String masterKeyAlias1;
                            masterKeyAlias1 = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                            SharedPreferences sharedPreferences1;
                            sharedPreferences1 = EncryptedSharedPreferences.create(
                                    "file",
                                    masterKeyAlias1,
                                    context,
                                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                            );
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putString("usuario", etUsuario.getText().toString());
                            editor.putString("senha", etSenha.getText().toString());
                            editor.apply();
                        }
                        Intent intent = new Intent(LoginActivity.this, PaginaInicialActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (ExcecaoUsuarioSenhaInvalido e) {
                        runOnUiThread(() -> {
                            etSenha.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                            etUsuario.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(LoginActivity.this, R.drawable.baseline_error_24), null);
                            etSenha.setEnabled(true);
                            etUsuario.setEnabled(true);
                            button.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            tvUsuarioSenhaInvalido.setText("Usuário e/ou senha invalidos!");
                            tvUsuarioSenhaInvalido.setVisibility(View.VISIBLE);
                        });
                    } catch (ExcecaoErroDeConectividade e) {
                        runOnUiThread(() -> {
                            tvUsuarioSenhaInvalido.setText("Verifique sua conexão com a internet!");
                            tvUsuarioSenhaInvalido.setVisibility(View.VISIBLE);
                            etSenha.setEnabled(true);
                            etUsuario.setEnabled(true);
                            button.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        });
                    } catch (GeneralSecurityException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}