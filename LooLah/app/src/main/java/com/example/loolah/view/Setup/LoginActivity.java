package com.example.loolah.view.Setup;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.loolah.R;
import com.example.loolah.databinding.ActivityLoginBinding;
import com.example.loolah.util.InputValidator;
import com.example.loolah.viewmodel.LoginViewModel;

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

        viewModel.getUser().observe(this, user -> {
            boolean error = false;

            if (InputValidator.isEmptyInput(user.getEmail())) {
                binding.etLoginEmail.setError("Enter your email");
                error = true;
            } else if (!InputValidator.isValidEmail(user.getEmail())) {
                binding.etLoginEmail.setError("Invalid Email");
                error = true;
            }

            if (InputValidator.isEmptyInput(user.getPassword())) {
                binding.etLoginPassword.setError("Enter your password");
                error = true;
            }

            if (!error) viewModel.login();
        });

        viewModel.getAuthenticatedUser().observe(this, user -> {
            switch (user.getStatus()) {
                case SUCCESS:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
                case ERROR:
                    Toast.makeText(this, "Login failed, please login again.", Toast.LENGTH_SHORT).show();
                    binding.btnLoginLogin.setEnabled(true);
                    break;
                case LOADING:
                    binding.btnLoginLogin.setEnabled(false);
                    break;
            }
        });

        binding.btnLoginTogglePassword.setOnClickListener(v -> password_visible = togglePasswordVisibility(binding.etLoginPassword, binding.btnLoginTogglePassword, password_visible));
        binding.btnLoginSignUp.setOnClickListener(v -> {
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
