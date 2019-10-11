package com.longer.verifyedit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
//                phonecode.setText("");
                phonecode.setNumber(false);
                phonecode2.setNumber(false);
                phonecode3.setNumber(true);
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
//        银行卡验证
//        BankInfoBean infoBean = new BankInfoBean();
//        infoBean.setBankcode("6217003810028144056");
//        if (infoBean.checkBankCard()) {
//            Log.i("longer", "是");
//            Log.i("longer", "vaiue:" + String.format("infoBean.getBankName:%s  + %s", infoBean.getBankName(), infoBean.getCardType()));
//        } else
//            Log.i("longer", "不是");

//        phonecode.setCodeLength(4);
//        phonecode.setBgNormal(R.drawable.bg_line_normal);
//        phonecode.setBgFocus(R.drawable.bg_line_focus);
//        phonecode3.setCodeTextSize(29);
//        phonecode.setCodeTextColor(Color.parseColor("#D81B60"));
//        phonecode3.setCodeTextColor(this.getResources().getColor(R.color.red));
//        phonecode2.setTvHeight(40);
//        phonecode2.setTvWidth(40);
//        phonecode2.setCodeMargin(10);
//        phonecode.setCodeMargin(-1);

//        phonecode.setCodeStyle(1001);
//        phonecode2.setCodeStyle(21000);
//        phonecode3.setCodeStyle(1002);
//        phonecode.setCodeTextSize(28);
//        phonecode.setNormalStrokeColor(Color.RED);
//        phonecode2.setNormalContentColor(Color.parseColor("#ae00e0"));
//        phonecode.setFocusStrokeColor(Color.BLUE);
//        phonecode2.setFocusContentColor(this.getResources().getColor(R.color.colorAccent));
//        phonecode.setBold(true);
//        phonecode.setStrokeSize(2);
//        phonecode2.setStrokeSize(2);
//        phonecode3.setStrokeSize(4);
//        phonecode.setNumber(true);
//        phonecode2.setNumber(true);
//        phonecode3.setNumber(false);
//        phonecode.setShowPwd(false);
//        phonecode2.setShowPwd(false);
//        phonecode3.setShowPwd(false);
//        phonecode2.hideKeyboard();
    }
}
