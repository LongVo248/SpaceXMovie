package cf.vandit.movie_app.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.fragments.FavouritesFragment;
import cf.vandit.movie_app.fragments.MovieFragment;
import cf.vandit.movie_app.fragments.ProfileFragment;
import cf.vandit.movie_app.fragments.SearchFragment;
import cf.vandit.movie_app.retrofit.dto.AccountInfo;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    public static AccountInfo accountInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountInfo= (AccountInfo) getIntent().getSerializableExtra("accountInfo");

        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovieFragment()).commit();
        toolbar.setTitle("Movies");

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_movie:
                        if (!getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName().equals("MovieFragment")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovieFragment()).commit();
                            toolbar.setTitle("Movies");
                        }
                        break;
                    case R.id.nav_search:
                        if (!getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName().equals("SearchFragment")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                            toolbar.setTitle("Search");
                        }
                        break;
                    case R.id.nav_favourites:
                        if (!getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName().equals("FavouritesFragment")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouritesFragment()).commit();
                            toolbar.setTitle("Favourites");
                        }
                        break;
                    case R.id.nav_profile:
                        if (!getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName().equals("ProfileFragment")){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                            toolbar.setTitle("Profile");
                        }
                        break;
                }
                return true;
            }
        });
    }


}