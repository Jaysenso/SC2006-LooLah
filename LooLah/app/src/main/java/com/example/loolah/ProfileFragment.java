package com.example.loolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profile_fragment = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton btn_setting = profile_fragment.findViewById(R.id.ib_profile_settings);
        btn_setting.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_settingFragment);
        });

        return profile_fragment;
    }
}
