package com.weatone.rbademoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PeopleDetailActivity extends AppCompatActivity {

    People item;

    TextView displayNameTextView;
    TextView ageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);

        Intent intent = getIntent();
        item = (People) intent.getSerializableExtra("item");

        displayNameTextView = (TextView) findViewById(R.id.displayNameTextView);
        ageTextView = (TextView) findViewById(R.id.ageTextView);

        // Fill-in info
        displayNameTextView.setText(item.getDisplayName());
        ageTextView.setText(item.getAge());
    }
}
