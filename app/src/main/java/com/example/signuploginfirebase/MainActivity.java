package com.example.signuploginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class MainActivity extends AppCompatActivity {

    TextView userName, homepage;
    Button logout;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        userName = findViewById(R.id.userName);
        homepage = findViewById(R.id.homepage);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Firebase Dynamic Links here
                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(MainActivity.this, pendingDynamicLinkData -> {
                            Uri deepLink = null;
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();
                                String referCode = deepLink.getQueryParameter("referCode");

                                // Start the RegistrationActivity and pass the referCode
                                Intent registrationIntent = new Intent(MainActivity.this, SignUpActivity.class);
                                registrationIntent.putExtra("referCode", referCode);
                                startActivity(registrationIntent);
                            }
                        });

                // startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null){
            String gName = gAccount.getDisplayName();
            userName.setText(gName);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
            }
        });
    }
}