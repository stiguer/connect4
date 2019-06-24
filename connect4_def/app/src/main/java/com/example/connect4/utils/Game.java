package com.example.connect4.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.connect4.R;
import com.example.connect4.activities.Partida;
import com.example.connect4.adapter.ImageAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Game{
    private final Board board;
    private Status status;
    private Player turn;
    private Player player1;
    private Player cpu;
    private final Date datahora;
    private long lapse;
    private int lastRow = 0;
    private int lastCol = 0;
    private boolean controlTime;
    private Context context;
    private GridView grid;
    private LinearLayout fitxesLayout;
    private ImageView turnImg;
    private TextView temps;
    private ImageAdapter adapter;
    private final ArrayList<Integer> lstAvailableIndexes = new ArrayList<Integer>();
    // entrega 2
    private boolean tablet = false;
    private String tempsin;
    private int tempsRestant;


    public Game(Context context, int size) {
        this.context = context;
        this.board = new Board(size);
        adapter = new ImageAdapter(context, board);
        this.datahora = new Date();
        this.lapse = 0;
        this.controlTime = false;
        this.tempsRestant = Config.TEMPS_MAX;

    }

    // setters
    public void setFitxes(LinearLayout fitxes) {
        this.fitxesLayout = fitxes;
    }
    public void setTurnImg(ImageView turn) {
        this.turnImg = turn;
    }
    public void setTiempo(TextView tiempo) {
        this.temps = tiempo;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setTurn(Player player) {
        this.turn = player;
    }
    public void setPlayer(Player player) {
        this.player1 = player;
    }
    public void setCpuPlayer(Player cpuPlayer) {
        this.cpu = cpuPlayer;
    }
    public void setControlTime(boolean controlTime) {
        this.controlTime = controlTime;
    }
    public void setGrid(GridView grid) {
        this.grid = grid;
        grid.setAdapter(adapter);
    }


    public boolean comprovarTemps() {
        Date now = new Date();
        lapse = (now.getTime() - datahora.getTime()) / 1000;
        return lapse >= Config.TEMPS_MAX && controlTime;
    }

    public  boolean checkForFinish() {
        return board.winner() != null || board.draw() || comprovarTemps();
    }

    // gestió del temps
    private void manageTime() {
        Date now = new Date();
        lapse = (now.getTime() - datahora.getTime()) / 1000;
        temps.setText(lapse + " secs.");

        // entrega 2
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date();
        String hora = format1.format(time);

        int tempsRest = 0;
        tempsRest = tempsRestant - Math.round(lapse);
        String res = String.valueOf(Math.round(lapse));
        Log.d("myTag", res);

        if (tablet == true && turn == player1){ // solució crash portrait
            ((Partida) context).update(lastRow, lastCol, tempsin, hora, tempsRest);
        }
        tempsin = hora;

    }

    private void drop(int col) {
        if (board.firstEmptyRow(col) != Config.NO_FILES_DISPONIBLES) {
            lastRow = board.firstEmptyRow(col);
            lastCol = col;
            board.occupyCell(col, turn);
            adapter.notifyDataSetChanged();
        }
    }

    private Position playOpponent() { // cpu
        updateAvailableIndexes();
        posarFitxa();
        adapter.notifyDataSetChanged();
        Random rd = new Random();
        int rdm = rd.nextInt(lstAvailableIndexes.size());
        drop(lstAvailableIndexes.get(rdm));
        return new Position(lastRow, lastCol);
    }

    void toggleTurn() {
        if (checkForFinish()) {
            getLog();
            return;
        }
        manageTime();
        turn = cpu;
        turnImg.setImageResource(R.drawable.ic_opcio_groga);
        playOpponent();
        if (checkForFinish()) {
            getLog();
            return;
        }
        turn = player1;
        posarFitxa();
        adapter.notifyDataSetChanged();
        turnImg.setImageResource(R.drawable.ic_opcio_vermella);
    }

    public void updateAvailableIndexes() {
        lstAvailableIndexes.clear();
        for (int i = 0; i < board.getSize(); i++) {
            if (board.firstEmptyRow(i) != Config.NO_FILES_DISPONIBLES) {
                lstAvailableIndexes.add(i);
            }
        }
    }

    public int getCellWidth() {
        Display display = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth=0;
        Display displayt = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        displayt.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

        if(size.x>size.y && diagonalInches>=6.5) { // tab landscape
            screenWidth = (size.x/2)-300;
            tablet = true;
        } else if (size.x>size.y && diagonalInches<6.5){
            screenWidth = 640;
        }else if (size.x<size.y && diagonalInches>=6.5) { // tab portrait
            screenWidth = size.x;
            tablet = true;
        }else {
            screenWidth = size.x;
        }
        return screenWidth / board.getSize();
    }

    public void posarFitxa() {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.firstEmptyRow(i) != Config.NO_FILES_DISPONIBLES) {
                ImageView iv = new ImageView(context);
                int width = getCellWidth();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
                iv.setImageResource(R.drawable.ic_opcio_vermella);
                iv.setLayoutParams(lp);
                fitxesLayout.addView(iv);
                fitxesLayout.invalidate();
                final int column = i;
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drop(column);
                        toggleTurn();
                    }
                });
            } else {
                ImageView iv = new ImageView(context);
                iv.setImageResource(R.drawable.ic_s_opcio_vermella);
                iv.setOnClickListener(null);
                iv.invalidate();
                fitxesLayout.addView(iv);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void getLog() {
        String log = "";
        String temps = "";
        String resultat = "";

        if (board.winner() == player1) {
            setStatus(Status.P1_WINS);
            Date now = new Date();
            long lapse = (now.getTime() - datahora.getTime()) / 1000;
            log += "Alias: " + player1.getName() + " | " + "Mida graella: "
                    + board.getSize() + " | " + "Temps total: " + lapse + " secs. | " + "Has guanyat!";
            temps = String.valueOf(lapse);
            resultat = "Victoria";

        } else if (board.winner() == cpu) {
            setStatus(Status.CPU_WINS);
            Date now = new Date();
            long lapse = (now.getTime() - datahora.getTime()) / 1000;
            log += "Alias: " + player1.getName() + " | " + "Mida graella: "
                    + board.getSize() + " | " + "Temps total: " + lapse + " secs. | " + "Has perdut!";
            temps = String.valueOf(lapse);
            resultat = "Derrota";
        } else {
            if (board.draw()) {
                setStatus(Status.DRAW);
                Date now = new Date();
                long lapse = (now.getTime() - datahora.getTime()) / 1000;
                log += "Alias: " + player1.getName() + " | " + "Mida graella: "
                        + board.getSize() + " | " + "Temps total: " + lapse + " secs. | " + "Has empatat!";
                temps = String.valueOf(lapse);
                resultat = "Empat";
            } else {
                if (comprovarTemps()) {
                    setStatus(Status.DRAW);
                    Date now = new Date();
                    long lapse = (now.getTime() - datahora.getTime()) / 1000;
                    log += "Alias: " + player1.getName() + " | " + "Mida graella: "
                            + board.getSize() + " | " + "Temps total: " + lapse + " secs. | " + "Has esgotat el temps!";
                    temps = String.valueOf(lapse);
                    resultat = "Temps esgotat";
                }
            }
        }
        ((Partida) context).resultats(log, temps, resultat);
    }

}



