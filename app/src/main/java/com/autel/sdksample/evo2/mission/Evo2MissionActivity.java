package com.autel.sdksample.evo2.mission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.autel.sdksample.R;


public class Evo2MissionActivity extends AppCompatActivity {
    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Evo2 Mission");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_mission_evo2);
        findViewById(R.id.WayPoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Evo2MissionActivity.this, Evo2WayPointActivity.class));
                Toast.makeText(getApplicationContext(), "you should realize by yourself", Toast.LENGTH_LONG).show();
            }
        });
    }

}
