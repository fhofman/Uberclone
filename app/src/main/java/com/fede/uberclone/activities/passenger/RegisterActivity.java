package com.fede.uberclone.activities.passenger;

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
import com.fede.uberclone.activities.driver.DriverMapActivity;
import com.fede.uberclone.activities.driver.RegisterDriverActivity;
import com.fede.uberclone.includes.MyToolbar;
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

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences mPref;

    // VIEWS
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;

    Toolbar mToolbar;

    AlertDialog mDialog;

    AuthProvider authProvider;
    PassengerProvider passengerProvider;
    DriverProvider driverProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolbar.show( this, "Registro de Usuario", true);

        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();

        authProvider = new AuthProvider();
        passengerProvider = new PassengerProvider();
        driverProvider = new  DriverProvider();

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        String selectedUser = mPref.getString("user","");
        Toast.makeText(this, "El valor seleccionado es "+ selectedUser, Toast.LENGTH_SHORT).show();
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputPassword = findViewById(R.id.textInputPassword);
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
        if(!name.isEmpty() && !eMail.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){
                mDialog.show();
                registerUser(name, eMail, password);
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

    void create(Passenger passenger){
        passengerProvider.create(passenger).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(RegisterActivity.this, "Passenger Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MapPassengerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, "No se pudo realizar el registro ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String name, String eMail, String password){
        authProvider.register(eMail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Passenger passenger = new Passenger(id, name, eMail);
                    create(passenger);
                }else{
                    Toast.makeText(RegisterActivity.this, "No se pudo realizar el registro ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}