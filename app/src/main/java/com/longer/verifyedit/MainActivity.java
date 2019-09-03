package com.longer.verifyedit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.longer.zfbtable.ZfbActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toZft(View view) {
        startActivity(new Intent(MainActivity.this, ZfbActivity.class));
    }

    public void openPhoneCode(View view) {
        startActivity(new Intent(MainActivity.this, PhoneCodeActivity.class));
    }
}
