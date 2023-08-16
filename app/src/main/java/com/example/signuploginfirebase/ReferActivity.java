package com.example.signuploginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.signuploginfirebase.SignUpActivity;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class ReferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                        String referCode = deepLink.getQueryParameter("referCode");
                        Log.d("DynamicLink", "Refer Code: " + referCode);

                        Intent registrationIntent = new Intent(ReferActivity.this, SignUpActivity.class);
                        registrationIntent.putExtra("referCode", referCode);
                        startActivity(registrationIntent);
                    }
                })
                .addOnFailureListener(this, e -> Log.w("DynamicLink", "getDynamicLink:onFailure", e));
    }
}