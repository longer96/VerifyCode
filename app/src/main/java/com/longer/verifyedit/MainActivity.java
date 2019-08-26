package com.longer.verifyedit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.longer.verifyedittext.PhoneCode;
import com.longer.zfbtable.ZfbActivity;

public class MainActivity extends AppCompatActivity {


    private PhoneCode phonecode;
    private PhoneCode phonecode2;
    private PhoneCode phonecode3;
    private TextView tvConfirm;
    private String vcode;
    private String vcode2;
    private String vcode3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvConfirm = findViewById(R.id.tvConfirm1);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonecode.setInputData("");
                phonecode2.setInputData("");
                phonecode3.setInputData("");
            }
        });

        phonecode = findViewById(R.id.phonecode);
        phonecode2 = findViewById(R.id.phonecode2);
        phonecode3 = findViewById(R.id.phonecode3);
        setInputListener();
    }

    private void setInputListener() {
        phonecode.setOnVerificationCodeCompleteListener(new PhoneCode.OnVerificationCodeCompleteListener() {
            @Override
            public void verificationCodeComplete(String verificationCode) {
                vcode = verificationCode;
                Toast.makeText(MainActivity.this, "验证码: " + verificationCode, Toast.LENGTH_SHORT).show();
                tvConfirm.setBackgroundColor(Color.parseColor("#eb6951"));
            }

            @Override
            public void verificationCodeIncomplete(String verificationCode) {
                tvConfirm.setBackgroundColor(Color.parseColor("#aaaaaa"));
            }
        });

        phonecode2.setOnVerificationCodeCompleteListener(new PhoneCode.OnVerificationCodeCompleteListener() {
            @Override
            public void verificationCodeComplete(String verificationCode) {
                vcode2 = verificationCode;
                Toast.makeText(MainActivity.this, "验证码2: " + verificationCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void verificationCodeIncomplete(String verificationCode) {
            }
        });
        phonecode3.setOnVerificationCodeCompleteListener(new PhoneCode.OnVerificationCodeCompleteListener() {
            @Override
            public void verificationCodeComplete(String verificationCode) {
                vcode3 = verificationCode;
                Toast.makeText(MainActivity.this, "验证码3: " + verificationCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void verificationCodeIncomplete(String verificationCode) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("vcode", vcode);
        outState.putString("vcode2", vcode2);
        outState.putString("vcode3", vcode3);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phonecode.setInputData(savedInstanceState.getString("vcode"));
        phonecode2.setInputData(savedInstanceState.getString("vcode2"));
        phonecode3.setInputData(savedInstanceState.getString("vcode3"));
    }

    public void aaaa(View view) {
        startActivity(new Intent(MainActivity.this, ZfbActivity.class));
    }
}
