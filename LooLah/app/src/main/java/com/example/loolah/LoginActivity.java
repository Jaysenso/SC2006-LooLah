package com.example.loolah;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private boolean password_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_sign_up = findViewById(R.id.btn_login_sign_up);
        ImageButton btn_toggle_password = findViewById(R.id.btn_login_toggle_password);
        EditText et_password = findViewById(R.id.et_login_password);

        btn_sign_up.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        btn_toggle_password.setOnClickListener(v -> {
            if (!password_visible) {
                btn_toggle_password.setImageResource(R.drawable.ic_password_show);
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                btn_toggle_password.setImageResource(R.drawable.ic_password_hide);
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            password_visible = !password_visible;
            et_password.setSelection(et_password.getText().length());
        });
    }
}
