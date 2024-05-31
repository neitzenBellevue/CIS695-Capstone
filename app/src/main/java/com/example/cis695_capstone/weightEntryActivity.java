package com.example.cis695_capstone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class weightEntryActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TextView weight;
    private Button imageButton;
    private ImageView imageView;
    DatabaseHelper databaseHelper = new DatabaseHelper(weightEntryActivity.this);
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String fname = "";
    private int idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_entry);
        initDatePicker();

        List<weightEntry> history = databaseHelper.getAllEntries();
        String lastWeight = Integer.toString(databaseHelper.getBegWeight());
        if(!history.isEmpty()) lastWeight = Integer.toString(history.get(history.size() -1).getWeight());

        this.dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate()); // Sets default day to today.
        this.weight = findViewById(R.id.weightEntry);
        weight.setText(lastWeight); // Sets default weight to last known or 150LBS);
        this.imageButton = findViewById(R.id.pictureButton);
        this.imageView = findViewById(R.id.takenPhoto);
        this.idNum = getIntent().getIntExtra("ID", -1);

        if(idNum != -1) fillEntries();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                o -> {
                    if(o.getResultCode() == RESULT_OK && o.getData() != null){;
                        Bundle bundle = o.getData().getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        imageView.setImageBitmap(bitmap);
                        saveBitMap(bitmap);
                        imageButton.setEnabled(false);
                    }
                });
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) == null){
                    activityResultLauncher.launch(intent);
                } else{
                    Toast.makeText(weightEntryActivity.this, "Error: No Camera Detected",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submitButton(View button){
        if(!weight.getText().toString().isEmpty() && Integer.parseInt(weight.getText().toString()) > 0 &&
                !dateButton.getText().toString().isEmpty()){ // Verifying that weight and date exists;
            Intent i = new Intent(this, MainActivity.class);

            weightEntry newEntry = new weightEntry(Integer.parseInt(weight.getText().toString()),
                    dateButton.getText().toString(), "/data/data/com.example.cis695_capstone/files/images/" + fname);

            if(idNum == -1) databaseHelper.addEntry(newEntry);
            else databaseHelper.editEntry(newEntry, "" + idNum);

            startActivity(i);
        } else findViewById(R.id.errorText).setVisibility(View.VISIBLE);
    }

    public void cancelButton(View button){
        Intent i = new Intent(this, MainActivity.class);
        File file = new File("/data/data/com.example.cis695_capstone/files/images/" + fname);
        boolean delete = file.delete(); // Deletes file if not saved.
        startActivity(i);
    }

    private void saveBitMap(Bitmap bm){
        try {
            String root = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).toString();
            File myDir = new File("/data/data/com.example.cis695_capstone/files/images");
            myDir.mkdirs();
            Random rand = new Random();
            if(fname.isEmpty()) this.fname = rand.nextInt(1000000) + ".jpg";
            File file = new File(myDir, fname);

            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch( Exception e) {
            Log.d("onBtnSavePng", e.toString());
        }
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_POSITIVE;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        /*
        Commenting out max date on Professors recommendation for testing.
        datePickerDialog.getDatePicker(a).setMaxDate(System.currentTimeMillis());
         */
    }
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month){
        if(month == 1){
           return "JAN";
        }
        if(month == 2){
            return "FEB";
        }
        if(month == 3){
            return "MAR";
        }
        if(month == 4){
            return "APR";
        }
        if(month == 5){
            return "MAY";
        }
        if(month == 6){
            return "JUN";
        }
        if(month == 7){
            return "JUL";
        }
        if(month ==8){
            return "AUG";
        }
        if(month == 9){
            return "SEP";
        }
        if(month == 10){
            return "OCT";
        }
        if(month == 11){
            return "NOV";
        }
        if(month == 12){
            return "DEC";
        }
        return "Error";
    }

    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        return makeDateString(day, month, year);
    }
    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    private void fillEntries(){
        weightEntry entry = databaseHelper.getAllEntries().get(idNum);

        weight.setText(Integer.toString(entry.getWeight()));
        dateButton.setText(entry.getDate());
        this.fname = entry.getImage();
        imageView.setImageURI(Uri.parse(fname));
    }
}