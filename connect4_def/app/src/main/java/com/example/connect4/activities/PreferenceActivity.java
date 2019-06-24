package com.example.connect4.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.connect4.R;
import com.example.connect4.fragments.PreferenceFragment;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        if(findViewById(R.id.prefFrag)!= null){
            if (savedInstanceState != null){
                return;
            }
            getFragmentManager().beginTransaction().add(R.id.prefFrag, new PreferenceFragment()).commit();
        }
    }

}
