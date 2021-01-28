package com.fede.uberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fede.uberclone.R;
import com.fede.uberclone.activities.passenger.RegisterActivity;
import com.fede.uberclone.includes.MyToolbar;
import com.fede.uberclone.models.Driver;
import com.fede.uberclone.models.Passenger;
import com.fede.uberclone.providers.AuthProvider;
import com.fede.uberclone.providers.DriverProvider;
import com.fede.uberclone.providers.PassengerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {

    SharedPreferences mPref;

    // VIEWS
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;
    TextInputEditText mTextInputCarBrand;
    TextInputEditText mTextInputCarPlate;

    Toolbar mToolbar;

    AlertDialog mDialog;

    AuthProvider authProvider;
    DriverProvider driverProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolbar.show( this, "Registro de Usuario", true);

        mDialog = new SpotsDialog.Builder().setContext(RegisterDriverActivity.this).setMessage("Espere un momento").build();



        authProvider = new AuthProvider();
        driverProvider = new  DriverProvider();

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mTextInputCarBrand = findViewById(R.id.textInputCarBrand);
        mTextInputCarPlate = findViewById(R.id.textInputCarPlate);

        mButtonRegister = findViewById(R.id.btnRegister);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegisterUser();
            }
        });
    }

    private void clickRegisterUser() {
        String name = mTextInputName.getText().toString();
        final String eMail= mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();
        final String carBrand = mTextInputCarBrand.getText().toString();
        final String carPlate = mTextInputCarPlate.getText().toString();
        if(!name.isEmpty() && !eMail.isEmpty() && !password.isEmpty() && !carBrand.isEmpty() && !carPlate.isEmpty()){
            if(password.length() >= 6){
                mDialog.show();
                registerUser(name, eMail, password, carBrand, carPlate);
            }else{
                Toast.makeText(this, "La password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe ingresar nombre, mail y pass", Toast.LENGTH_SHORT).show();
        }
    }

    /*private void saveUser(String id, String name, String eMail) {
        String selectedUser = mPref.getString("user", "");
        if (selectedUser.equals("driver")) {
            Driver driver = new Driver();
            driver.setEmail(eMail);
            driver.setName(name);
            mDatabase.child("users").child("drivers").child(id).setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registro NO Exitoso", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Driver driver = new Driver();
            driver.setEmail(eMail);
            driver.setName(name);
            mDatabase.child("users").child("passengers").child(id).setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registro NO Exitoso", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }*/

    void create(Driver driver){
        driverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(RegisterDriverActivity.this, "Driver Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterDriverActivity.this, DriverMapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo realizar el registro ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String name, String eMail, String password, String carBrand, String carPlate){
        authProvider.register(eMail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if(task.isSuccessful()){
                    Toast.makeText(RegisterDriverActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Driver driver = new Driver(id, name, eMail, carBrand, carPlate);
                    create(driver);
                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo realizar el registro ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}