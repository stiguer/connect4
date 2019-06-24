package com.example.connect4.activities;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.connect4.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Resultat extends AppCompatActivity implements View.OnClickListener {
    private String data;
    private String text;
    private EditText etdata;
    private EditText etres;
    private EditText etmail;
    private Button btnenviar;
    private Button btnNovapar;
    private Button btnSortir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        btnenviar = findViewById(R.id.btnEnviar);
        btnNovapar = findViewById(R.id.btnNova);
        btnSortir = findViewById(R.id.btnSortir2);
        etdata = findViewById(R.id.data);
        etres = findViewById(R.id.resultat);
        etmail = findViewById(R.id.email);

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dat = new Date();
        data = format1.format(dat);
        text = getIntent().getStringExtra("resultat");

        etdata.setText(data);
        etres.setText(text);

        btnenviar.setOnClickListener(this);
        btnNovapar.setOnClickListener(this);
        btnSortir.setOnClickListener(this);

    }

    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.btnEnviar:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { etmail.getText().toString() });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Log - " + data);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Send Email"));
                finish();
                break;
            case R.id.btnNova:
                startActivity(new Intent(this, Partida.class));
                finish();
                break;
            case R.id.btnSortir2:
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
