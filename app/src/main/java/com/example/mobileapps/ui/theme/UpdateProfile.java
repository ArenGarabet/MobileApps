package com.example.mobileapps.ui.theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobileapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {
    private EditText editTextUpdateFirstName, editTextUpdateEmail;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        getSupportActionBar().setTitle("Update Profile");
        database = FirebaseDatabase.getInstance().getReference();
        User theUser = new User();
        findViews();



        Button ButtonUpdate = findViewById(R.id.button_update);
        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String textFname = editTextUpdateFirstName.getText().toString();
                String textEmail = editTextUpdateEmail.getText().toString();

                if (TextUtils.isEmpty(textFname)) {
                    Toast.makeText(UpdateProfile.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
                    editTextUpdateFirstName.setError("First Name is required");
                    editTextUpdateFirstName.requestFocus();

                }else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(UpdateProfile.this, "Please enter your new email", Toast.LENGTH_SHORT).show();
                    editTextUpdateEmail.setError("Email is required");
                    editTextUpdateEmail.requestFocus();

                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    updateUser(textFname, textEmail);

                }
            }

            private void updateUser(String textFname, String textEmail) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(textFname).build();
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdateProfile.this, "Name Updated Successful!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                database.child("users").child(theUser.getID()+"").child("userName").setValue(textFname);

                user.updateEmail(textEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProfile.this, "Email Update Successful!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                database.child("users").child(theUser.getID()+"").child("email").setValue(textEmail);

                Intent userProfileActivity = new Intent(UpdateProfile.this, UserProfileActivity.class);
                startActivity(userProfileActivity);
            }
        });

    }
    private void findViews() {
        editTextUpdateFirstName = findViewById(R.id.editText_update_fname);
        editTextUpdateEmail = findViewById(R.id.editText_update_email);
        progressBar = findViewById(R.id.progressbar);

    }





}

