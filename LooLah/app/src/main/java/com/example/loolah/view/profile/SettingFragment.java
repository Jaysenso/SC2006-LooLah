package com.example.loolah.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.databinding.FragmentSettingBinding;
import com.example.loolah.view.Setup.LoginActivity;
import com.example.loolah.R;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    private String profilePicUrl;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setSettingView(this);

        if (getArguments() != null) profilePicUrl = getArguments().getString("profilePicUrl");

        return binding.getRoot();
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void onClickBack() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        navHostFragment.getNavController().navigateUp();
    }

    public void onClickChangePassword(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("profilePicUrl", profilePicUrl);

        Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_changePasswordFragment, bundle);
    }

    public void onClickEditProfile(View view) {
        Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_editProfileFragment);
    }

    public void onClickSignOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();
    }
}
