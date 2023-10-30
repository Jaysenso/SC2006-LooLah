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

import com.example.loolah.Setup.LoginActivity;
import com.example.loolah.R;


public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View setting_fragment = inflater.inflate(R.layout.fragment_setting, container, false);

        ImageButton btn_back = setting_fragment.findViewById(R.id.ib_setting_back);
        Button btn_change_password = setting_fragment.findViewById(R.id.btn_setting_change_password);
        Button btn_sign_out = setting_fragment.findViewById(R.id.btn_setting_sign_out);

        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        btn_change_password.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_settingFragment_to_changePasswordFragment);
        });

        btn_sign_out.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().overridePendingTransition(0, 0);
        });

        Button btn_edit_profile = setting_fragment.findViewById(R.id.btn_setting_edit_profile);
        btn_edit_profile.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_settingFragment_to_editProfileFragment);
        });

        return setting_fragment;
    }
}
