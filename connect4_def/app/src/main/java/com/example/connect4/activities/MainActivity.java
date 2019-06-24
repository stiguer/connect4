package com.example.connect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.connect4.R;
import com.example.connect4.bd.PartidesSQLiteHelper;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PartidesSQLiteHelper partides = new PartidesSQLiteHelper(this, "bd_partides", null, 1);


        Button btnAjuda = (Button) findViewById(R.id.btnAjuda);
        Button btnPartida = (Button) findViewById(R.id.btnPartida);
        Button btnSortir = (Button) findViewById(R.id.btnSortir);
        Button btnConsultar = (Button) findViewById(R.id.btnConsultar);

        btnAjuda.setOnClickListener(this);
        btnPartida.setOnClickListener(this);
        btnSortir.setOnClickListener(this);
        btnConsultar.setOnClickListener(this);
    }

    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.btnConsultar:
                Display displayt = this.getWindowManager().getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                displayt.getMetrics(metrics);

                float widthInches = metrics.widthPixels / metrics.xdpi;
                float heightInches = metrics.heightPixels / metrics.ydpi;
                double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

                if(diagonalInches>=6.5) {
                    startActivity(new Intent(this, AccessBDActivityTab.class));
                }else {
                    startActivity(new Intent(this, AccessBDActivityH.class));
                }
                finish();
                break;
            case R.id.btnAjuda:
                startActivity(new Intent(this, Ajuda.class));
                finish();
                break;
            case R.id.btnPartida:
                startActivity(new Intent(this, Partida.class));
                finish();
                break;
            case R.id.btnSortir:
                // surt de l'aplicació
                finish();
                System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.PrefItem:
                startActivity(new Intent(this, PreferenceActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}