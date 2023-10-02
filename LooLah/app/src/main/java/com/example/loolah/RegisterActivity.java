package com.example.loolah;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    public boolean password_visible = false;
    public boolean confirm_password_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton btn_toggle_password = findViewById(R.id.btn_register_toggle_password);
        EditText et_password = findViewById(R.id.et_register_password);
        ImageButton btn_toggle_confirm_password = findViewById(R.id.btn_register_toggle_confirm_password);
        EditText et_confirm_password = findViewById(R.id.et_register_confirm_password);

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

        btn_toggle_confirm_password.setOnClickListener(v -> {
            if (!confirm_password_visible) {
                btn_toggle_confirm_password.setImageResource(R.drawable.ic_password_show);
                et_confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                btn_toggle_confirm_password.setImageResource(R.drawable.ic_password_hide);
                et_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            confirm_password_visible = !confirm_password_visible;
            et_confirm_password.setSelection(et_confirm_password.getText().length());
        });
    }
}
