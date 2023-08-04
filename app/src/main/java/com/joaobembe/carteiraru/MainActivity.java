package com.joaobembe.carteiraru;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        String sharedPreferencesFileName = "file";

        File sharedPreferencesFile = new File(context.getFilesDir().getParent() + "/shared_prefs/" + sharedPreferencesFileName + ".xml");
        if (sharedPreferencesFile.exists()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, BemVindoActivity.class);
            startActivity(intent);
            finish();
        }
    }
}