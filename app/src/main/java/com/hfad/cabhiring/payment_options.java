package com.hfad.cabhiring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class payment_options extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    String no = "";
    Boolean isDelete;
    Button  apply,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        et1 = (EditText) findViewById(R.id.etCardNumber);
        et2 = (EditText) findViewById(R.id.etExpires);
        et3 = (EditText) findViewById(R.id.etName);
        et4 = (EditText) findViewById(R.id.etCVC);

        apply = findViewById(R.id.btapply);
        cancel = findViewById(R.id.btcancel);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before==0)
                    isDelete=false;
                else
                    isDelete=true;
            }


            @Override
            public void afterTextChanged(Editable s) {
                String source = s.toString();
                int length=source.length();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(source);

                if(length==20){
                    stringBuilder.deleteCharAt(length-1);
                    et1.setText(stringBuilder);
                    et1.setSelection(et1.getText().length());
                }

                else if(length>0 && length%5==0)
                {
                    if(isDelete)
                        stringBuilder.deleteCharAt(length-1);
                    else
                        stringBuilder.insert(length-1," ");

                    et1.setText(stringBuilder);
                    et1.setSelection(et1.getText().length());

                }
            }
        });



        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before==0)
                    isDelete=false;
                else
                    isDelete=true;
            }


            @Override
            public void afterTextChanged(Editable s) {
                String source = s.toString();
                int length=source.length();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(source);

                if(length==6){
                    stringBuilder.deleteCharAt(length-1);
                    et2.setText(stringBuilder);
                    et2.setSelection(et2.getText().length());
                }

                else if(length>0 && length%3==0)
                {
                    if(isDelete)
                        stringBuilder.deleteCharAt(length-1);
                    else
                        stringBuilder.insert(length-1,"/");

                    et2.setText(stringBuilder);
                    et2.setSelection(et2.getText().length());

                }
            }
        });


        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(before==0)
                    isDelete=false;
                else
                    isDelete=true;
            }


            @Override
            public void afterTextChanged(Editable s) {
                String source = s.toString();
                int length=source.length();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(source);

                if(length==4){
                    stringBuilder.deleteCharAt(length-1);
                    et4.setText(stringBuilder);
                    et4.setSelection(et4.getText().length());
                }

                /*else if(length>0 && length%5==0)
                {
                    if(isDelete)
                        stringBuilder.deleteCharAt(length-1);
                    else
                        stringBuilder.insert(length-1," ");

                    et1.setText(stringBuilder);
                    et1.setSelection(et1.getText().length());

                }*/
            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDate() &&
                        validateNumber() &&
                        validateCVV() &&
                        validateName())
                    Toast.makeText(payment_options.this, "PAYMENT SUCCESSFUL!!!!", Toast.LENGTH_SHORT).show();
                Toast.makeText(payment_options.this, "Driver on your way...", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
            }
        });




    }

    public boolean validateDate(){
        String dt = et2.getText().toString();
        int l = dt.length();
        if(l<5) {
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            String temp = dt.substring(0, 2);
            int t = Integer.parseInt(temp);
            if(t>12) {
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
                return true;
        }
    }

    public boolean validateNumber(){
        String no = et1.getText().toString();
        int l = no.length();
        if(l<19) {
            Toast.makeText(this, "Invalid Card Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;

    }

    public boolean validateCVV(){
        String no = et4.getText().toString();
        int l = no.length();
        if(l<3) {
            Toast.makeText(this, "Invalid CVV", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;

    }

    public boolean validateName(){
        String n = et3.getText().toString();
        if(n.isEmpty()){
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return false;}

        else
            return true;
    }
}