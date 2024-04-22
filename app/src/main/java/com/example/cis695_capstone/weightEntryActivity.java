package com.example.cis695_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class weightEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_entry);
    }

    public void submitButton(View button){
        // Todo: Logic to update SQL Database
        // Ex: Date editview and Weight editview cleaned and added to database.
        // Success message pops up and return to summary page
        Intent i = new Intent(this, MainActivity.class);
    }

    public void cancelButton(View button){
        Intent i = new Intent(this, MainActivity.class);
    }
}