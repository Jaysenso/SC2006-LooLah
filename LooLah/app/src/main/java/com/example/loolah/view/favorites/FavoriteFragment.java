package com.example.loolah.view.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.R;
import com.example.loolah.adapter.FavoriteAdapter;
import com.example.loolah.databinding.FragmentFavoriteBinding;
import com.example.loolah.model.Toilet;
import com.example.loolah.util.SpinnerUtil;
import com.example.loolah.viewmodel.FavoriteViewModel;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.OnItemClickListener {
    private FavoriteViewModel viewModel;
    private FragmentFavoriteBinding binding;
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setFavoriteView(this);

        adapter = new FavoriteAdapter();
        adapter.setOnItemClickListener(this);
        binding.rvFavToilets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvFavToilets.setAdapter(adapter);

        viewModel.getFilteredToiletList().observe(getViewLifecycleOwner(), toilets -> {
            switch (toilets.getStatus()) {
                case SUCCESS:
                    ArrayList<Toilet> toiletList = toilets.getData();
                    if (toiletList == null || toiletList.size() == 0)
                        binding.tvFavNoToilets.setVisibility(View.VISIBLE);
                    else binding.tvFavNoToilets.setVisibility(View.GONE);
                    adapter.setFavoritesToiletList(toiletList);
                    break;
                case ERROR:
                    Log.d("TEST", "error");
                    break;
                case LOADING:
                    break;
            }
        });

        viewModel.getFavoriteToilets();

        setSpinnerAdapter(binding.spFavoriteFilterType, SpinnerUtil.getType());
        setSpinnerAdapter(binding.spFavoriteFilterDistrict, SpinnerUtil.getDistrict());
        setSpinnerAdapter(binding.spFavoriteFilterRating, SpinnerUtil.getRating());

        return binding.getRoot();
    }

    @Override
    public void onSelectToilet(View view, Toilet toilet) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", toilet.getToiletId());

        Navigation.findNavController(view).navigate(R.id.action_favoritesFragment_to_toiletDetailsFragment, bundle);
    }

    public void onClickSearch() {
        Log.d("TEST", "Clicking Search");
        viewModel.filterToilets(binding.etFavoriteSearch.getText().toString(), SpinnerUtil.getTypeValue(binding.spFavoriteFilterType.getSelectedItemPosition()), SpinnerUtil.getDistrictValue(binding.spFavoriteFilterDistrict.getSelectedItemPosition()), SpinnerUtil.getRatingValue(binding.spFavoriteFilterRating.getSelectedItemPosition()));
    }

    public void setSpinnerAdapter(Spinner spinner, String[] objects) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, objects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}