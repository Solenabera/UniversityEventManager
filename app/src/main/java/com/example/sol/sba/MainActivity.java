package com.example.sol.sba;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    private FloatingActionButton addPostBtn;

    private BottomNavigationView mainbottomNav;

    private EducationFragment educationFragment;
    private MovieFragment movieFragment;
    private SportFragment sportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        getSupportActionBar().setTitle("ASTU E-Manager");

        if(mAuth.getCurrentUser() != null) {

            mainbottomNav = findViewById(R.id.mainBottomNav);

            // FRAGMENTS

            educationFragment = new EducationFragment();
            movieFragment = new MovieFragment();
            sportFragment = new SportFragment();

            initializeFragment();

            mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

                    switch (item.getItemId()) {

                        case R.id.bottom_action_home:

                            replaceFragment(educationFragment, currentFragment);
                            return true;

                        case R.id.bottom_action_account:

                            replaceFragment(sportFragment, currentFragment);
                            return true;

                        case R.id.bottom_action_notif:

                            replaceFragment(movieFragment, currentFragment);
                            return true;

                        default:
                            return false;


                    }

                }
            });


            addPostBtn = findViewById(R.id.add_post_btn);

            addPostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent newPostIntent = new Intent(MainActivity.this, NewPostActivity.class);
                    startActivity(newPostIntent);

                }
            });

        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){

            sendToLogin();

        } else {

            current_user_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()){

                        if(!task.getResult().exists()){

                            Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                            startActivity(setupIntent);
                            finish();

                        }

                    } else {

                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();


                    }

                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout_btn:

                logOut();

                return true;

            case R.id.notify:

                Intent notoficationIntent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(notoficationIntent);

                return true;
            case R.id.message:

                Intent messageIntent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(messageIntent);

                return true;
            case R.id.action_settings_btn:

                Intent settingsIntent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(settingsIntent);

                return true;

            case R.id.action_password_change_btn:

                Intent changePasswordIntent = new Intent(MainActivity.this, PasswordChangeActivity.class);
                startActivity(changePasswordIntent);

                return true;

            case R.id.action_developer_btn:

                Intent developerIntent = new Intent(MainActivity.this, DeveloperActivity.class);
                startActivity(developerIntent);

                return true;

            case R.id.action_Version_btn:

                Intent versionIntent = new Intent(MainActivity.this, ActivityVersion.class);
                startActivity(versionIntent);

                return true;


               default:
                   return false;


        }

    }

    private void logOut() {


        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main_container, educationFragment);
        fragmentTransaction.add(R.id.main_container, movieFragment);
        fragmentTransaction.add(R.id.main_container, sportFragment);

        fragmentTransaction.hide(movieFragment);
        fragmentTransaction.hide(sportFragment);

        fragmentTransaction.commit();

    }

    private void replaceFragment(Fragment fragment, Fragment currentFragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(fragment == educationFragment){

            fragmentTransaction.hide(sportFragment);
            fragmentTransaction.hide(movieFragment);

        }

        if(fragment == sportFragment){

            fragmentTransaction.hide(educationFragment);
            fragmentTransaction.hide(movieFragment);

        }

        if(fragment == movieFragment){

            fragmentTransaction.hide(educationFragment);
            fragmentTransaction.hide(sportFragment);

        }
        fragmentTransaction.show(fragment);

        //fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }

}
