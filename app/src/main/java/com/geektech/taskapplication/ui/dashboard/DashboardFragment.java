package com.geektech.taskapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.geektech.taskapplication.R;
import com.geektech.taskapplication.databinding.FragmentDashboardBinding;
import com.geektech.taskapplication.ui.home.NewsAdapter;
import com.geektech.taskapplication.ui.interfaces.OnItemClickListener;
import com.geektech.taskapplication.ui.models.News;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    //private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLongClick(int position) {
                deleteNews(adapter.getItem(position));
                adapter.removeItem(position);
            }
        });

    }

    private void deleteNews(News item) {
        FirebaseFirestore.getInstance().collection("news")
                .document(item.getDocId())
                .delete();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //dashboardViewModel =
        //        new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        //final TextView textView = binding.textDashboard;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        FirebaseFirestore.getInstance()
                .collection("news")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        List<News> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : snapshots) {
                            News news = snapshot.toObject(News.class);
                            news.setDocId(snapshot.getId());
                            list.add(news);
                        }

                        //List<News> list = snapshots.toObjects(News.class);
                        adapter.addItems(list);
//                        binding.recyclerView.setAdapter(adapter);
                    }
                });
    }

    private void getDataLive(){
        FirebaseFirestore.getInstance().collection("news")
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<News> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : value) {
                            News news = snapshot.toObject(News.class);
                            news.setDocId(snapshot.getId());
                            list.add(news);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}