package com.fede.uberclone.providers;

import com.fede.uberclone.models.Passenger;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerProvider {

    DatabaseReference database;

    public PassengerProvider() {
        this.database = FirebaseDatabase.getInstance().getReference().child("users").child("passengers");
    }

    public Task<Void> create(Passenger passenger){
        return database.child(passenger.getId()).setValue(passenger);
    }
}
