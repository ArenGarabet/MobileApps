package com.example.mobileapps.ui.theme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.StringReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserProfileActivity extends AppCompatActivity {

    private TextView  textViewWelcome,textViewFirstName, textViewLastName, textViewEmail, textViewRegisterDate;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setTitle("Name");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        findViews();
        signOut();
        updateProfile();

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }else{
            Toast.makeText(UserProfileActivity.this,"User Not Found", Toast.LENGTH_SHORT).show();
        }


    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        FirebaseUserMetadata metadata = firebaseUser.getMetadata();
        long registerTimeStamp = metadata.getCreationTimestamp();
        String datePattern = "E, dd MMMM yyyy hh:mm a z";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        sdf.setTimeZone(TimeZone.getDefault());
        String register = sdf.format(new Date(registerTimeStamp));
        String registerDate = getResources().getString(R.string.user_since, register);
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        textViewEmail.setText(email);
        textViewFirstName.setText(name);
        textViewWelcome.setText(name);

        String welcome = getResources().getString(R.string.welcome_user);
        textViewWelcome.setText(welcome);
        progressBar.setVisibility(View.GONE);
    }

    private void signOut(){
        Button buttonSignOut = findViewById(R.id.button_sign_out);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(UserProfileActivity.this, "Signed Out!", Toast.LENGTH_SHORT).show();
                Intent mainActivity = new Intent(UserProfileActivity.this, MainActivity.class);
                mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainActivity);
                finish();
            }
        });
    }

    private void updateProfile(){
        Button buttonUpdateProfile = findViewById(R.id.button_update_profile);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateProfile = new Intent(UserProfileActivity.this, UpdateProfile.class);
                updateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(updateProfile);
                finish();
            }
        });
    }

    private void findViews() {
        textViewWelcome = findViewById(R.id.textView_welcome);
        textViewFirstName = findViewById(R.id.textView_show_name);
        textViewEmail = findViewById(R.id.textView_show_email);
        textViewRegisterDate = findViewById(R.id.textview_register);
        progressBar = findViewById(R.id.progressbar);
    }
}