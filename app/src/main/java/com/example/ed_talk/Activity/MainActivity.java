package com.example.ed_talk.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ed_talk.Login.LoginActivity;
import com.example.ed_talk.R;
import com.example.ed_talk.Utils.SharedPrefManager;
import com.example.ed_talk.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    DrawerLayout drawer;
    NavigationView mNavigationView;
    View mHeaderView;
    private FirebaseAuth mAuth;
    TextView mUserName,mUserEmail;
    ImageView mUserImage;

    LinearLayout shareExperience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(this);
        if(sharedPrefManager.isLoggedIn()){
            updateSideNavHeader();
        }
        else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void updateSideNavHeader(){
        // NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mHeaderView =  mNavigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        // View
        mUserName = (TextView) mHeaderView.findViewById(R.id.name);
        mUserEmail = (TextView) mHeaderView.findViewById(R.id.email);
        mUserImage = (ImageView) mHeaderView.findViewById(R.id.imageView);

        // Set username & email
        mUserName.setText(mAuth.getCurrentUser().getDisplayName().split(" ")[0]);
        mUserEmail.setText(mAuth.getCurrentUser().getEmail());
        Glide.with(getApplicationContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(mUserImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void placementBrochure(MenuItem item) {
        drawer.closeDrawers();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nitjsr.ac.in/tap/portfolio/NIT%20Jamshedpur%20Placement%20Brochure.pdf")));

    }

    public void logout(MenuItem item) {
        SharedPrefManager shared = new SharedPrefManager(this);
        shared.setIsLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    /*
    public void rateUs(MenuItem item) {
        drawer.closeDrawers();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nitjsr.tapcell")));

    }
    */
    public void openWebsite(MenuItem item) {
        drawer.closeDrawers();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.banasthali.in/")));
    }

    public void shareApp(MenuItem item) {
        drawer.closeDrawers();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = "https://play.google.com/store/apps/details?id=com.nitjsr.tapcell";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Download My TAP : NIT Jamshedpur app to View and Share interview experiences posted by NIT Jamshedpur students.");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    public void openDevActivity(MenuItem item) {
        drawer.closeDrawers();
        Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
        startActivity(intent);
    }

    public void privacyPolicy(MenuItem item) {
        drawer.closeDrawers();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://my-tap.flycricket.io/privacy.html") );
        startActivity(browserIntent);
    }

}

