package com.example.loolah.view.Setup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.loolah.database.DatabaseSetup;

import java.util.ArrayList;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //DatabaseSetup db = new DatabaseSetup();
        //db.setup(getApplicationContext());

        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
