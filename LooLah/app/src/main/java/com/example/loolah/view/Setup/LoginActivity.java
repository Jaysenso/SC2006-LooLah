package com.example.loolah.view.Setup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.loolah.R;
import com.example.loolah.databinding.ActivityLoginBinding;
import com.example.loolah.databinding.ActivityRegisterBinding;
import com.example.loolah.viewmodel.LoginViewModel;
import com.example.loolah.viewmodel.RegisterViewModel;

public class LoginActivity extends AppCompatActivity {
    private boolean password_visible = false;

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(viewModel);
        binding.btnLoginTogglePassword.setOnClickListener(v -> password_visible = togglePasswordVisibility(binding.etLoginPassword, binding.btnLoginTogglePassword, password_visible));

        viewModel.getUser().observe(this, user -> {
            if (TextUtils.isEmpty(user.getEmail())) {
                Log.d("TEST", "Empty email address");
                binding.etLoginEmail.setError("Error");
            } else if (!user.isEmailValid()) {
                Log.d("TEST", "Invalid email address");
                binding.etLoginEmail.setError("Error");
            } else if (TextUtils.isEmpty(user.getPassword())) {
                Log.d("TEST", "Empty password");
                binding.etLoginPassword.setError("Error");
            } else {
                viewModel.login();
            }
        });

        viewModel.getAuthenticatedUser().observe(this, user -> {
            switch (user.getStatus()) {
                case SUCCESS:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
                case ERROR:
                    Log.d("TEST", "Login failed");
                    Log.d("TEST", user.getMessage());
                    binding.btnLoginLogin.setEnabled(true);
                    break;
                case LOADING:
                    Log.d("TEST", "Loading");
                    binding.btnLoginLogin.setEnabled(false);
                    break;
            }
        });

        Button btn_sign_up = findViewById(R.id.btn_login_sign_up);
        btn_sign_up.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    public boolean togglePasswordVisibility(EditText et, ImageButton ib, boolean visible) {
        if (!visible) {
            ib.setImageResource(R.drawable.ic_password_show);
            et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ib.setImageResource(R.drawable.ic_password_hide);
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        et.setSelection(et.getText().length());
        return !visible;
    }
}
