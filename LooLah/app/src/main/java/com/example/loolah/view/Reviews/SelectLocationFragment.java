package com.example.loolah.view.Reviews;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.loolah.adapter.HomeToiletListAdapter;
import com.example.loolah.databinding.FragmentSelectlocationBinding;
import com.example.loolah.model.Toilet;
import com.example.loolah.viewmodel.ReviewLocationViewModel;

public class SelectLocationFragment extends Fragment implements HomeToiletListAdapter.OnItemClickListener {
    private ReviewLocationViewModel viewModel;
    private FragmentSelectlocationBinding binding;
    private HomeToiletListAdapter adapter;
    /*public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setLocationView(this);

        adapter = new HomeToiletListAdapter();
        adapter.setOnItemClickListener(this);
        binding.rvHomeToilets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvHomeToilets.setAdapter(adapter);

        viewModel.getFilteredToiletList().observe(getViewLifecycleOwner(), toilets -> {
            switch (toilets.getStatus()) {
                case SUCCESS:
                    ArrayList<Toilet> toiletList = toilets.getData();
                    if (toiletList != null && toiletList.size() == 0)
                        binding.tvHomeNoToilets.setVisibility(View.VISIBLE);
                    else binding.tvHomeNoToilets.setVisibility(View.GONE);
                    adapter.setHomeToiletList(toiletList);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to retrieve nearby toilets.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });

        if (checkLocationPermission()) getLocation();
        else requestLocationPermission();

        binding.srlHomeToilets.setOnRefreshListener(() -> {
            if (checkLocationPermission()) {
                binding.tvHomeNoPermission.setVisibility(View.GONE);
                getLocation();
                binding.srlHomeToilets.setRefreshing(false);
            } else binding.tvHomeNoPermission.setVisibility(View.VISIBLE);
        });

        return binding.getRoot();
    }*/
    @Override
    public void onSelectToilet(View view, Toilet toilet) {

    }
}
