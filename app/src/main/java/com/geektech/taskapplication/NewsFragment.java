package com.geektech.taskapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.taskapplication.databinding.FragmentNewsBinding;
import com.geektech.taskapplication.ui.models.News;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Objects;

public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        News news = (News) getArguments().getSerializable("model");
        if(news != null){
            binding.editText.setText(news.getTitle());
        }

        binding.btnSave.setOnClickListener(v -> {
           sendData();
        });
}

    private void sendData() {
        String text = Objects.requireNonNull(binding.editText.getText()).toString();
        News news = new News(text,System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        getParentFragmentManager().setFragmentResult("rk_news", bundle);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}