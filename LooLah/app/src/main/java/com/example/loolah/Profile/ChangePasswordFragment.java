package com.example.loolah.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;

public class ChangePasswordFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View setting_fragment = inflater.inflate(R.layout.fragment_change_password, container, false);

        Button btn_save = setting_fragment.findViewById(R.id.btn_change_password_save);
        ImageButton btn_back = setting_fragment.findViewById(R.id.ib_change_password_back);

        btn_save.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_changePasswordFragment_to_profileFragment);
        });

        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        return setting_fragment;
    }
}
