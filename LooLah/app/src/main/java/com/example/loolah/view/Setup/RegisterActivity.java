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
import com.example.loolah.databinding.ActivityRegisterBinding;
import com.example.loolah.util.InputValidator;
import com.example.loolah.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    public boolean password_visible = false;
    public boolean confirm_password_visible = false;

    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        binding.setRegisterViewModel(viewModel);

        viewModel.getUser().observe(this, user -> {
            boolean error = false;

            if (InputValidator.isEmptyInput(user.getEmail())) {
                binding.etRegisterEmail.setError("Email required");
                error = true;
            } else if (!InputValidator.isValidEmail(user.getEmail())) {
                binding.etRegisterEmail.setError("Invalid email");
                error = true;
            }

            if (InputValidator.isEmptyInput(user.getUsername())) {
                binding.etRegisterUsername.setError("Username required");
                error = true;
            } else if (!InputValidator.isValidUsername(user.getUsername())) {
                binding.etRegisterUsername.setError("Invalid username");
                error = true;
            }

            if (InputValidator.isEmptyInput(user.getPassword())) {
                binding.etRegisterPassword.setError("Password required");
                error = true;
            } else if (!InputValidator.isValidPassword(user.getPassword())) {
                binding.etRegisterPassword.setError("Invalid password");
                error = true;
            } else if (InputValidator.isEmptyInput(user.getConfirmPassword())) {
                binding.etRegisterConfirmPassword.setError("Confirm Password required");
                error = true;
            } else if (!user.isPasswordMatching()) {
                binding.etRegisterConfirmPassword.setError("Password does not match");
                error = true;
            }

            if (!error) viewModel.register();
        });

        viewModel.getAuthenticatedUser().observe(this, user -> {
            switch (user.getStatus()) {
                case SUCCESS:
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                    break;
                case ERROR:
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                    binding.btnRegisterRegister.setEnabled(true);
                    break;
                case LOADING:
                    binding.btnRegisterRegister.setEnabled(false);
                    break;
            }
        });

        binding.btnRegisterTogglePassword.setOnClickListener(v -> password_visible = togglePasswordVisibility(binding.etRegisterPassword, binding.btnRegisterTogglePassword, password_visible));
        binding.btnRegisterToggleConfirmPassword.setOnClickListener(v -> confirm_password_visible = togglePasswordVisibility(binding.etRegisterConfirmPassword, binding.btnRegisterToggleConfirmPassword, confirm_password_visible));
        binding.tvRegisterAccountExistLink.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
