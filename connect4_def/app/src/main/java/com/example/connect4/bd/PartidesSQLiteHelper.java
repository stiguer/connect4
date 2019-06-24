package com.example.connect4.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PartidesSQLiteHelper extends SQLiteOpenHelper {

    private String sqlCreate = "CREATE TABLE Partides " +
                                "(alias TEXT, data TEXT, mida INTEGER," +
                                "control TEXT, temps TEXT, resultat TEXT)";
    public PartidesSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Partides");
        db.execSQL(sqlCreate);
    }
}
