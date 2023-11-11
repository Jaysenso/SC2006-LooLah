package com.example.loolah.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;
import com.example.loolah.databinding.FragmentChangePasswordBinding;
import com.example.loolah.model.LoginUser;
import com.example.loolah.util.InputValidator;
import com.example.loolah.viewmodel.ChangePasswordViewModel;

public class ChangePasswordFragment extends Fragment {
    private ChangePasswordViewModel viewModel;
    private FragmentChangePasswordBinding binding;
    private final LoginUser user = new LoginUser();
    private String profilePicUrl;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ChangePasswordViewModel.class);

        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setChangePasswordView(this);

        viewModel.getChangePasswordStatus().observe(getViewLifecycleOwner(), booleanLiveDataWrapper -> {
            switch (booleanLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    viewModel.resetChangePasswordStatus();
                    Toast.makeText(getContext(), "Password successfully updated.", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_changePasswordFragment_to_profileFragment);
                    break;
                case ERROR:
                    binding.btnChangePasswordSave.setEnabled(true);
                    Toast.makeText(getContext(), booleanLiveDataWrapper.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    binding.btnChangePasswordSave.setEnabled(false);
                    break;
            }
        });

        if (getArguments() != null) profilePicUrl = getArguments().getString("profilePicUrl");
        binding.setUser(user);

        return binding.getRoot();
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void onClickBack() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) navHostFragment.getNavController().navigateUp();
    }

    public void onClickSave() {
        boolean error = false;

        if (InputValidator.isEmptyInput(user.getCurrentPassword())) {
            binding.etChangePasswordCurrent.setError("Enter your current password");
            error = true;
        }

        if (InputValidator.isEmptyInput(user.getPassword())) {
            binding.etChangePasswordNew.setError("Enter a new password");
            error = true;
        } else if (!InputValidator.isValidPassword(user.getPassword())) {
            binding.etChangePasswordNew.setError("Invalid password");
            error = true;
        } else if (InputValidator.isEmptyInput(user.getConfirmPassword())) {
            binding.etChangePasswordConfirm.setError("Confirm password required");
            error = true;
        } else if (!user.isPasswordMatching()) {
            binding.etChangePasswordConfirm.setError("Password does not match");
            error = true;
        }

        if (!error) viewModel.savePassword(user);
    }
}
