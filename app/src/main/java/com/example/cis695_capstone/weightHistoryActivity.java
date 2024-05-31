package com.example.cis695_capstone;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;
import java.util.List;

public class weightHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_history);
        renderHistory();
    }

    private void renderHistory(){
        DatabaseHelper databaseHelper = new DatabaseHelper(weightHistoryActivity.this);
        List<weightEntry> history = databaseHelper.getAllEntries();
        ListView myListView = (ListView) findViewById(R.id.listView);
        EntryListAdapter adapter = new EntryListAdapter(this,
                R.layout.dynamic_listview_adapter, (ArrayList<weightEntry>)history);
        myListView.setAdapter(adapter);
    }

    public void returnToSummary(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    class EntryListAdapter extends ArrayAdapter<weightEntry> {
        private static final String TAG = "EntryListAdapter";
        private final Context context;
        int resource;
        public EntryListAdapter(Context context, int resource, ArrayList<weightEntry> objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            int weight = getItem(position).getWeight();
            String date = getItem(position).getDate();
            String location = getItem(position).getImage();
            weightEntry newEntry = new weightEntry(weight, date, location);
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            TextView dateSet = (TextView) convertView.findViewById(R.id.dateGenerated);
            TextView weightSet = (TextView) convertView.findViewById(R.id.weightGenerated);
            ImageView imageSet = (ImageView) convertView.findViewById(R.id.imageGenerated);
            Button editButton = (Button) convertView.findViewById(R.id.editButton);

            dateSet.setText(date);
            weightSet.setText(Integer.toString(weight));
            if(location != null) imageSet.setImageURI(Uri.parse(location));
            imageSet.setMinimumHeight(300);
            imageSet.setMinimumWidth(300);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(weightHistoryActivity.this, weightEntryActivity.class);
                    i.putExtra("ID", position);
                    startActivity(i);
                }
            });

            return convertView;
        }
    }
}