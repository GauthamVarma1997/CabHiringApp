package com.hfad.cabhiring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Intent i = new Intent(MainActivity.this,MainPageActivity.class);
            startActivity(i);
            finish();

        }
        else
        {
            setContentView(R.layout.activity_main);
        }



    }
    public void open_signup(View v)
    {
        Intent i = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(i);
    }
    public void open_signin(View v)
    {
        Intent i = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(i);
    }
}
