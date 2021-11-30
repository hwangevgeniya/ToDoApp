package com.geektech.taskapplication.ui.board;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.viewpager.widget.ViewPager;

import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.geektech.taskapplication.Prefs;
import com.geektech.taskapplication.R;
import com.geektech.taskapplication.databinding.FragmentBoardBinding;
import com.geektech.taskapplication.ui.home.HomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;


public class BoardFragment extends Fragment {
    FragmentBoardBinding binding;
    private BoardAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BoardAdapter();
        binding.viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabDots);
        new TabLayoutMediator(tabLayout, binding.viewPager,
                (tab, position) ->
                        //tab.setText("OBJECT " + (position + 1))
                        tab.setIcon(R.drawable.tab_selector)
        ).attach();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

        skipFragments();
        binding.btnSkip.setOnClickListener(v -> navigateUp());


    }

    private void skipFragments() {
        adapter.setOnClickBtns(new BoardAdapter.onClickBtns() {
          @Override
            public void startFragment() {
                navigateUp();
            }
        });
    }

    private void navigateUp(){
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

}