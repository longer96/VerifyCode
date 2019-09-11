package com.longer.verifyedit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.longer.verifyedittext.BankInfoBean;
import com.longer.verifyedittext.PhoneCode;

public class PhoneCodeActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_phone_code);

        tvConfirm = findViewById(R.id.tvConfirm1);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonecode.setText("");
                phonecode2.setText("");
                phonecode3.setText("");
            }
        });

        phonecode = findViewById(R.id.phonecode);
        phonecode2 = findViewById(R.id.phonecode2);
        phonecode3 = findViewById(R.id.phonecode3);
        setInputListener();
    }
    private void setInputListener() {
        phonecode.setOnVCodeCompleteListener(new PhoneCode.OnVCodeInputListener() {
            @Override
            public void vCodeComplete(String verificationCode) {
                vcode = verificationCode;
                Toast.makeText(PhoneCodeActivity.this, "验证码: " + verificationCode, Toast.LENGTH_SHORT).show();
                tvConfirm.setBackgroundColor(Color.parseColor("#eb6951"));
            }

            @Override
            public void vCodeIncomplete(String verificationCode) {
                tvConfirm.setBackgroundColor(Color.parseColor("#aaaaaa"));
            }
        });

        phonecode2.setOnVCodeCompleteListener(new PhoneCode.OnVCodeInputListener() {
            @Override
            public void vCodeComplete(String verificationCode) {
                vcode2 = verificationCode;
                Toast.makeText(PhoneCodeActivity.this, "验证码2: " + verificationCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void vCodeIncomplete(String verificationCode) {
            }
        });
        phonecode3.setOnVCodeCompleteListener(new PhoneCode.OnVCodeInputListener() {
            @Override
            public void vCodeComplete(String verificationCode) {
                vcode3 = verificationCode;
                Toast.makeText(PhoneCodeActivity.this, "验证码3: " + verificationCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void vCodeIncomplete(String verificationCode) {
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
        phonecode.setText(savedInstanceState.getString("vcode"));
        phonecode2.setText(savedInstanceState.getString("vcode2"));
        phonecode3.setText(savedInstanceState.getString("vcode3"));
    }

    public void red(View view) {
//        phonecode.setTvBg();
        phonecode.setTvMargin(14);

        BankInfoBean infoBean = new BankInfoBean();
        infoBean.setBankcode("6217003810028144056");
        if(infoBean.checkBankCard())
            Log.i("longer","是");
        else
            Log.i("longer","不是");
    }
}
