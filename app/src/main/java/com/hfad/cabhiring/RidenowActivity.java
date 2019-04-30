package com.hfad.cabhiring;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.TextView;

public class RidenowActivity extends AppCompatActivity {
    Button text1,text2;
    Button paybutton;
    ImageButton micro,sedan;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridenow);
        // Call the function callInstamojo to start payment here
        Bundle b=getIntent().getExtras();
        String src=b.getString("source");
        String dest=b.getString("destination");
        text1 = findViewById(R.id.stLoc);
        text2 = findViewById(R.id.destLoc2);
        paybutton = (Button)findViewById(R.id.rdNow);
        text1.setText(src);
        text2.setText(dest);
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Choose cab type from above...",Toast.LENGTH_SHORT).show();

            }
        });

        micro = (ImageButton)findViewById(R.id.imageButton4);
        sedan = (ImageButton)findViewById(R.id.imageButton7);


    }

    public void clickme(View v)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Booking...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want book ?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_launcher_background);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "You clicked on YES :   "+which, Toast.LENGTH_SHORT).show();
                Intent j = new Intent(RidenowActivity.this,rideConfirmed.class);
                startActivity(j);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO :   "+which, Toast.LENGTH_SHORT).show();
                // dialog.cancel();
            }
        });
        // Setting Netural "Cancel" Button
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed Cancel button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on Cancel :   "+which,
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }


    public void doPayment(View v)
    {
        String email = "harshil.r1999@gmail.com";
        String phone = "8248464779";
        String amount = "56";
        String purpose = "cabhiring";
        String buyername = "Harshil Shah";
        callInstamojoPay(email,phone,amount,purpose,buyername);
    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }
}
