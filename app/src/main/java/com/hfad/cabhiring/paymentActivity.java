package com.hfad.cabhiring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class paymentActivity extends AppCompatActivity {
    int countt = 0;
    int countd = 0;

    EditText et1,et2,et3,et4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        et1 = (EditText) findViewById(R.id.etCardNumber);
        et2 = (EditText) findViewById(R.id.etExpires);
        et3 = (EditText) findViewById(R.id.etName);
        et4 = (EditText) findViewById(R.id.etCVC);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strOrigCard = null,strOrigDate = null,strOrigCVV = null;
                if (countt <= et1.getText().toString().length()
                        &&(et1.getText().toString().length()==4
                        ||et1.getText().toString().length()==9
                        ||et1.getText().toString().length()==14)){
                    et1.setText(et1.getText().toString()+" ");
                    int pos = et1.getText().length();
                    et1.setSelection(pos);
                }else if (countt >= et1.getText().toString().length()
                        &&(et1.getText().toString().length()==4
                        ||et1.getText().toString().length()==9
                        ||et1.getText().toString().length()==14)){
                    et1.setText(et1.getText().toString().substring(0,et1.getText().toString().length()-1));
                    int pos = et1.getText().length();
                    et1.setSelection(pos);
                    strOrigCard = et1.getText().toString();
                }else if(et1.getText().toString().length() > 19){
                    Toast.makeText(paymentActivity.this, "enter a valid card number", Toast.LENGTH_SHORT).show();
                }
                countt = et1.getText().toString().length();

                if (countd <= et2.getText().toString().length()
                        &&(et2.getText().toString().length()==2
                        ||et2.getText().toString().length()==4 )){
                    et2.setText(et2.getText().toString()+"/");
                    int pos = et2.getText().length();
                    et2.setSelection(pos);
                }else if (countd >= et2.getText().toString().length()
                        &&(et2.getText().toString().length()==2
                        ||et2.getText().toString().length()==4
                )){
                    et2.setText(et2.getText().toString().substring(0,et2.getText().toString().length()-1));
                    int pos = et2.getText().length();
                    et2.setSelection(pos);
                    strOrigCard = et2.getText().toString();
                }else if(et2.getText().toString().length() > 4){
                    Toast.makeText(paymentActivity.this, "enter a valid date", Toast.LENGTH_SHORT).show();
                }
                countt = et1.getText().toString().length();

                if(et3.getText().toString().length() > 3){
                    Toast.makeText(paymentActivity.this, "enter a valid cvv", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}