package com.example.connect4.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.connect4.R;
import com.example.connect4.bd.PartidesSQLiteHelper;
import com.example.connect4.fragments.RegFrag;
import com.example.connect4.partida.PartidaGuardada;

public class DetailRegActivity extends AppCompatActivity implements RegFrag.OnFragmentInteractionListener {

    TextView resultats;
    Button btnTornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reg);

        resultats = findViewById(R.id.detalls);
        btnTornar = (Button) findViewById(R.id.btnTornardet);

        PartidesSQLiteHelper part = new PartidesSQLiteHelper(this, "bd_partides", null, 1);

        Intent in = getIntent();
        String partida = in.getStringExtra("partida");

        SQLiteDatabase db = part.getReadableDatabase();
        String[] parametres = {partida};
        String[] camps = {"alias", "data", "mida", "control", "temps", "resultat"};

        Cursor cursor = db.query("Partides", camps, "data=?", parametres,null,null,null);

        // el primer registre
        cursor.moveToFirst();

        resultats.setText(cursor.getString(0)+"\n"+cursor.getString(1)+"\n"+
                cursor.getInt(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)+"\n"+
                cursor.getString(5));

        btnTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailRegActivity.this, AccessBDActivityH.class));
                finish();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
