package com.fede.uberclone.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fede.uberclone.R;
import com.fede.uberclone.activities.driver.RegisterDriverActivity;
import com.fede.uberclone.activities.passenger.RegisterActivity;

public class SelectOptionAuthActivity extends AppCompatActivity {

    SharedPreferences mPref;

    Toolbar mToolbar;

    Button mButtonGotoLogin;
    Button mButtonGotoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar Opcion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mButtonGotoLogin = findViewById(R.id.btnGotoLogin);
        mButtonGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });

        mButtonGotoRegister = findViewById(R.id.btnGotoregister);
        mButtonGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
            }
        });


    }

    private void gotoLogin() {
        Intent intent = new Intent( SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void gotoRegister() {
        String userType = mPref.getString("user","");
        Intent intent;
        if(userType.equals("driver")){
            intent = new Intent(SelectOptionAuthActivity.this, RegisterDriverActivity.class);
        }else {
            intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
        }
        startActivity(intent);
    }
}