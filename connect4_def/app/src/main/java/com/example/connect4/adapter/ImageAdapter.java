package com.example.connect4.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.connect4.R;
import com.example.connect4.utils.Board;
import com.example.connect4.utils.Cell;
import com.example.connect4.utils.Config;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Board board;
    private LayoutInflater inflater;

    public ImageAdapter(Context c, Board board){
        this.mContext = c;
        this.board = board;
        inflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { // retorna el nombre de cel·les
        return board.getSize() * board.getSize();
    }

    @Override
    public Cell getItem(int position) {
        return board.getCells()[position / board.getSize()][position % board.getSize()];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cell c = getItem(position);
        int color = Config.GRIS;
        if (c != null) {
            color = c.getPlayer().getColour();
        }

        convertView = inflater.inflate(R.layout.custom_layout, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.image);

        Display display = ((Activity)mContext).getWindowManager().getDefaultDisplay(); // mida pantalla
        Point size = new Point();
        display.getSize(size);
        int screenWidth;

        Display displayt = ((Activity) this.mContext).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        displayt.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

        if(size.x>size.y && diagonalInches>=6.5) {
            screenWidth = (size.x/2)-300;
        } else if (size.x>size.y && diagonalInches<6.5){
            screenWidth = 640;
        }else {
            screenWidth = size.x;
        }
        //String res = String.valueOf(Math.round(diagonalInches));
        ///Log.d("myTag", res);


        screenWidth /= board.getSize();
        //int screenHeight = screenWidth;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth, screenWidth);
        iv.setLayoutParams(lp);

        switch(color) {
            case Config.VERMELL:
                iv.setImageResource(R.drawable.ic_fitxa_vermella);
                break;
            case Config.GROC:
                iv.setImageResource(R.drawable.ic_fitxa_groga);
                break;
            case Config.GRIS:
                iv.setImageResource(R.drawable.ic_fitxa_buida);
                break;
        }

        return convertView;
    }

}
