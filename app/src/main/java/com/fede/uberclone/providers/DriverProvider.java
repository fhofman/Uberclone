package com.fede.uberclone.providers;

import com.fede.uberclone.models.Driver;
import com.fede.uberclone.models.Passenger;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverProvider {
    DatabaseReference database;

    public DriverProvider() {
        this.database = FirebaseDatabase.getInstance().getReference().child("users").child("drivers");
    }

    public Task<Void> create(Driver driver){
        return database.child(driver.getId()).setValue(driver);
    }
}
