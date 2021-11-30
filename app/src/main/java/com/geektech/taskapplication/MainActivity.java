package com.geektech.taskapplication;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geektech.taskapplication.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination,
                                             @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_home ||
                        destination.getId() == R.id.navigation_dashboard ||
                        destination.getId() == R.id.navigation_notifications ||
                        destination.getId() == R.id.profileFragment){
                    binding.navView.setVisibility(View.VISIBLE);

                } else {
                    binding.navView.setVisibility(View.INVISIBLE);
                }

                if(destination.getId() == R.id.boardFragment){
                    getSupportActionBar().hide();
                }
                else {
                    getSupportActionBar().show();
                }
            }
        });

        Prefs prefs = App.getInstance().getPrefs();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            navController.navigate(R.id.loginFragment);
        }
        if(!prefs.isBoardShown()) {
            navController.navigate(R.id.boardFragment);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

}