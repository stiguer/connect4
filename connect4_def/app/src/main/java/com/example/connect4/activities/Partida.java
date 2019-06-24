package com.example.connect4.activities;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.connect4.R;


import android.content.Intent;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.connect4.bd.PartidesSQLiteHelper;
import com.example.connect4.fragments.GridFrag;
import com.example.connect4.fragments.LogFrag;
import com.example.connect4.utils.Board;

import com.example.connect4.utils.Config;
import com.example.connect4.utils.Game;
import com.example.connect4.utils.Player;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Partida extends AppCompatActivity implements GridFrag.OnFragmentInteractionListener, LogFrag.OnFragmentInteractionListener {

    private Game game;
    private Board board;
    private Player player1;
    private Player cpu;
    private boolean temps;
    private LinearLayout fitxesLayout;
    private GridView grid;
    private TextView tempsjoc;
    private ImageView torn;
    // entrega 2
    private TextView log;
    private String nom;
    private int mida;
    private String control;
    private String res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Intent in = getIntent();
        grid = findViewById(R.id.grid);
        tempsjoc = findViewById(R.id.temps);
        torn = findViewById(R.id.imagetorn);
        fitxesLayout = findViewById(R.id.fitxesLayout);

        log = findViewById(R.id.log);
        if (log != null){
            log.setMovementMethod(new ScrollingMovementMethod());
        }

        // extras
        mida = Integer.parseInt(prefs.getString("pref3", "3"));
        nom = prefs.getString("pref1", "");

        control = "Sense control de temps";
        temps = prefs.getBoolean("pref2", false);
        if(temps) {
            control = "Amb control de temps";
            tempsjoc.setTextColor(Color.RED);
        }

        board = new Board(mida);
        player1 = new Player(nom, Config.VERMELL);
        cpu = new Player("cpu", Config.GROC);

        game = new Game(this, board.getSize());
        game.setFitxes(fitxesLayout);
        game.setPlayer(player1);
        game.setCpuPlayer(cpu);
        game.setGrid(grid);
        game.setTiempo(tempsjoc);
        game.setTurnImg(torn);
        game.setTurn(player1);
        game.setControlTime(temps);
        game.posarFitxa();
        grid.setNumColumns(board.getSize());
        res = "";
    }

    public void resultats(String res, String temps, String resultat) {
        Intent intent = new Intent(this, Resultat.class);
        intent.putExtra("resultat", res);
        startActivity(intent);

        PartidesSQLiteHelper partides = new PartidesSQLiteHelper(this, "bd_partides", null, 1);
        SQLiteDatabase db = partides.getWritableDatabase();

        String data;
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dat = new Date();
        data = format1.format(dat);

        ContentValues nouRegistre = new ContentValues();
        nouRegistre.put("alias", nom);
        nouRegistre.put("data", data);
        nouRegistre.put("mida", mida);
        nouRegistre.put("control", control);
        nouRegistre.put("temps", temps);
        nouRegistre.put("resultat", resultat);

        db.insert("Partides", null, nouRegistre);
        db.close();

        finish();
    }

    public void update(int row, int col, String tempsin, String tempsfin, long tempsRestant){
        if(tempsin == null){
            res += "LOG...\n"+"Alias: "+nom+"; Mida graella = "+mida+"\n"+control+"\n"
                    +"Casella ocupada: ("+String.valueOf(row) +", "+ String.valueOf(col)+")"+"\n\n";
            log.setText(res);
        }else if(tempsin != null && control.equals("Sense control de temps")){
            res += "Temps in. tirada = "+tempsin+" scs; Temps fin. tirada = "+tempsfin+" scs."
                    +"\n"+"Casella ocupada: ("+String.valueOf(row) +", "+ String.valueOf(col)+")"+"\n\n";
            log.setText(res);
        }else {
            res += "Temps in. tirada = "+tempsin+" scs; Temps fin. tirada = "+tempsfin+" scs."+"\n"+"Temps restant = "+tempsRestant+" secs."
                    +"\n"+"Casella ocupada: ("+String.valueOf(row) +", "+ String.valueOf(col)+")"+"\n\n";
            log.setText(res);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
