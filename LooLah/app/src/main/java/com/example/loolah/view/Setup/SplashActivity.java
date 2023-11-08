package com.example.loolah.view.Setup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.example.loolah.database.DatabaseSetup;
import com.example.loolah.viewmodel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        viewModel.getFirebaseUser().observe(this, currentUser -> {
            Intent intent;

            if (currentUser != null) intent = new Intent(SplashActivity.this, MainActivity.class);
            else intent = new Intent(SplashActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        });

        viewModel.getCurrentUser();
    }

    private void setupDatabase() {
        DatabaseSetup db = new DatabaseSetup();
        db.setup(getApplicationContext());
    }
}
