package com.example.loolah.view.Setup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.loolah.R;
import com.example.loolah.databinding.ActivityRegisterBinding;
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
            if (TextUtils.isEmpty(user.getEmail())) {
                Log.d("TEST", "Empty email address");
                binding.etRegisterEmail.setError("Error");
            } else if (!user.isEmailValid()) {
                Log.d("TEST", "Invalid email address");
                binding.etRegisterEmail.setError("Error");
            } else if (TextUtils.isEmpty(user.getUsername())) {
                Log.d("TEST", "Empty username");
                binding.etRegisterUsername.setError("Error");
            } else if (TextUtils.isEmpty(user.getPassword())) {
                Log.d("TEST", "Empty password");
                binding.etRegisterPassword.setError("Error");
            } else if (TextUtils.isEmpty(user.getConfirmPassword())) {
                Log.d("TEST", "Empty confirm password");
                binding.etRegisterConfirmPassword.setError("Error");
            } else if (!user.isPasswordMatching()) {
                Log.d("TEST", "Passwords do not match");
                binding.etRegisterConfirmPassword.setError("Error");
            } else {
                viewModel.register();
            }
        });

        viewModel.getAuthenticatedUser().observe(this, user -> {
            switch (user.getStatus()) {
                case SUCCESS:
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                    break;
                case ERROR:
                    Log.d("TEST", "Register failed");
                    binding.etRegisterConfirmPassword.setError("Registration failed");
                    binding.btnRegisterRegister.setEnabled(true);
                    break;
                case LOADING:
                    Log.d("TEST", "Loading");
                    binding.btnRegisterRegister.setEnabled(false);
                    break;
            }
        });

        binding.btnRegisterTogglePassword.setOnClickListener(v -> password_visible = togglePasswordVisibility(binding.etRegisterPassword, binding.btnRegisterTogglePassword, password_visible));
        binding.btnRegisterToggleConfirmPassword.setOnClickListener(v -> confirm_password_visible = togglePasswordVisibility(binding.etRegisterConfirmPassword, binding.btnRegisterToggleConfirmPassword, confirm_password_visible));
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
