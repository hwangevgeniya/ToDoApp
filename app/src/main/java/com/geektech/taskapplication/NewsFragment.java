package com.geektech.taskapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektech.taskapplication.databinding.FragmentNewsBinding;
import com.geektech.taskapplication.ui.models.News;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Objects;

public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;
    News news;
    private ActivityResultLauncher<Intent> registerForActivityResult;
    private String imageUri;

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
        binding.progressBar.setVisibility(View.GONE);
        news = (News) getArguments().getSerializable("model");
        if (news != null) {
            binding.editText.setText(news.getTitle());

        }
        saveImageToFirestoreStorage();

        binding.btnSave.setOnClickListener(v -> {
            sendData();
        });

        registerForActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null) {
                            imageUri = result.getData().getData().toString();
                            Glide.with(binding.getRoot())
                                    .load(result.getData().getData())
                                    .into(binding.imageView);
                        }
                    }
                });
    }

    private void uploadImageToFirestore(Uri uri) {
        StorageReference reference = FirebaseStorage.getInstance()
                .getReference()
                .child("image/" + FirebaseAuth.getInstance().getUid() + "/" + uri.getLastPathSegment() + ".jpg");
        showProgress();
        reference.putFile(uri)

                .continueWithTask(task -> {
                    imageUri = reference.getDownloadUrl().toString();
                    //com.google.android.gms.tasks.zzu@ac33b80
                    return reference.getDownloadUrl();
                })

                .addOnCompleteListener(taskSnapshot -> {
                    imageUri = taskSnapshot.getResult().toString();
                    hideProgress();
                });
    }

    private void sendData() {/*
        showProgress();
        String text = Objects.requireNonNull(binding.editText.getText()).toString();
        if (news != null) {
            news.setTitle(text);
            App.getInstance().getDatabase().newsDao().update(news);
            updateItemInFireStore(news);
        } else {
            if (!binding.editText.getText().toString().equals("")) {
                news = new News(text, System.currentTimeMillis());
                App.getInstance().getDatabase().newsDao().insert(news);
                saveToFirestore(news);
            } else {
                Toast.makeText(requireActivity(), "Add field", Toast.LENGTH_SHORT).show();
            }

        }*/
        String etText = binding.editText.getText().toString();
        if (!etText.equals("") && imageUri != null) {
            if (news != null) {
                news.setTitle(etText);
                App.getInstance().getDatabase().newsDao().update(news);
                updateItemInFireStore(news);
            } else {
                news = new News(etText, System.currentTimeMillis());
                news.setUri(imageUri);
                App.getInstance().getDatabase().newsDao().insert(news);
                showProgress();
                saveToFirestore(news);
                uploadImageToFirestore(Uri.parse(imageUri));
            }
        } else {
            Toast.makeText(requireActivity(), "Add field", Toast.LENGTH_SHORT).show();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        getParentFragmentManager().setFragmentResult("rk_news", bundle);
        //close();
    }

    private void saveImageToFirestoreStorage() {
        binding.imageView.setOnClickListener(v -> {
            registerForActivityResult.launch(new Intent().setAction(Intent.ACTION_PICK).setType("image/*"));
        });
    }

    private void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSave.setVisibility(View.GONE);
    }

    private void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
        binding.btnSave.setVisibility(View.VISIBLE);
    }

    private void updateItemInFireStore(News news) {
        FirebaseFirestore.getInstance().collection("news")
                .document(news.getDocId())
                .update("title", news.getTitle())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            close();
                    }
                });
    }

    private void saveToFirestore(News news) {
        FirebaseFirestore.getInstance().collection("news")
                .add(news)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            close();
                        }
                    }
                });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}