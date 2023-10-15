package com.example.loolah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View setting_fragment = inflater.inflate(R.layout.fragment_setting, container, false);

        Button btn_sign_out = setting_fragment.findViewById(R.id.btn_setting_sign_out);
        btn_sign_out.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().overridePendingTransition(0, 0);
        });

        return setting_fragment;
    }
}
