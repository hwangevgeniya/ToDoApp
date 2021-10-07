package com.geektech.taskapplication.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapplication.R;
import com.geektech.taskapplication.databinding.FragmentHomeBinding;
import com.geektech.taskapplication.ui.models.News;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment implements NewsAdapter.OnItemClickListener {

    //private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    //private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private boolean isChanged = false;
    private int position;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChanged = false;
                openFragment(null);
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                News news = (News) result.getSerializable("news");
                if (isChanged){
                    adapter.updateItem(news, position);
                }
                else {
                    adapter.addItem(news);
                }
            }
        });


        initList();
    }

    private void initList() {
        adapter.setListener(this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void openFragment(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", news);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.taskFragment, bundle);
    }

    @Override
    public void onClick(int position) {
        this.position = position;
        isChanged = true;
        News news = adapter.getItem(position);
        openFragment(news);
    }

    @Override
    public void onLongClick(int position) {
        News news = adapter.getItem(position);
        new AlertDialog.Builder(requireContext())
                .setTitle("Удаление")
                .setMessage("Удалить запись \" " + news.getTitle() + "\"?")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeItem(position);
                    }
                }).show();
    }
}