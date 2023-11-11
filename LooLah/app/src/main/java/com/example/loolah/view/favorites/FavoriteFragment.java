package com.example.loolah.view.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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

        viewModel.getFavoritesToiletListMutableLiveData().observe(getViewLifecycleOwner(), toilets -> {
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

        String[] toilet_types = new String[]{"Type", "Bus Interchange", "Club", "Coffeeshop", "Foodcourt", "Government Office", "Market & Food Centre", "MRT Station", "Park", "Pier", "Place of worship", "Private Office", "Restaurant", "Shopping Centre", "Tourist Attraction", "Community Centre", "Food Court", "Dormitory", "Industrial Complex"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFavoriteFilterType.setAdapter(adapter);

        String[] toilet_districts = new String[]{"District", "Central", "North East", "North West", "South East", "South West"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFavoriteFilterDistrict.setAdapter(adapter);

        String[] toilet_rating = new String[]{"Rating", "1 star", "2 star", "3 star", "4 star", "5 star"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_rating);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFavoriteFilterRating.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onSelectToilet(View view, Toilet toilet) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", toilet.getToiletId());

        Navigation.findNavController(view).navigate(R.id.action_favoritesFragment_to_toiletDetailsFragment, bundle);
    }
}