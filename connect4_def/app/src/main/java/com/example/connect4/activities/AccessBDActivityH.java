package com.example.connect4.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.connect4.partida.PartidaGuardada;
import com.example.connect4.R;
import com.example.connect4.bd.PartidesSQLiteHelper;
import com.example.connect4.fragments.QueryFrag;

import java.util.ArrayList;

public class AccessBDActivityH extends AppCompatActivity implements QueryFrag.OnFragmentInteractionListener {

    ListView listViewPartides;
    ArrayList<String> llista_partides;
    ArrayList<PartidaGuardada> llista_items;
    PartidesSQLiteHelper part;
    Button btnTornar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_bdh);

        listViewPartides = (ListView) findViewById(R.id.listview);
        btnTornar = (Button) findViewById(R.id.btnTornamenu);

        part = new PartidesSQLiteHelper(getApplicationContext(), "bd_partides", null, 1);

        mostrarLlistaPartides();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, llista_partides);
        listViewPartides.setAdapter(adapter);

        listViewPartides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(AccessBDActivityH.this, DetailRegActivity.class);
                in.putExtra("partida", llista_items.get(position).getData());
                startActivity(in);
                finish();

            }
        });

        btnTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccessBDActivityH.this, MainActivity.class));
                finish();
            }
        });
    }

    private void mostrarLlistaPartides() {
        SQLiteDatabase db = part.getReadableDatabase();
        PartidaGuardada partida = null;
        llista_partides = new ArrayList<String>();
        llista_items = new ArrayList<PartidaGuardada>();

        // slect * from Partides
        Cursor cursor = db.rawQuery("SELECT * FROM Partides",null);

        while (cursor.moveToNext()){
            partida = new PartidaGuardada();
            partida.setAlias(cursor.getString(0));
            partida.setData(cursor.getString(1));
            partida.setResultat(cursor.getString(5));
            llista_partides.add(partida.getAlias()+"\n"+partida.getData()+"\n"+partida.getResultat());
            llista_items.add(partida);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
