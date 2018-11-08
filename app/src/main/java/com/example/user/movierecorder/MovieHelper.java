package com.example.user.movierecorder;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sotiria on 9/4/2017.
 */

public class MovieHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="MovieDB.db";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_TABLE="movies";

    public static final String COLUMN_ID="_id";
    public static final String TITLE="title";
    public static final String YEAR="year";
    public static final String SCORE="score";

    public MovieHelper(Context context,String name,CursorFactory factory,int version){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    private static final String DATABASE_CREATE="create table movies (_id integer primary key,"+
            "title text not null,year text not null,score integer not null);";
    private SQLiteDatabase db;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    private static final String DATABASE_UPGRADE="DROP TABLE IF EXISTS"+DATABASE_TABLE;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_UPGRADE);
        onCreate(db);
    }

    public void saveMovie (String title, String year, int score){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TITLE,title);
        values.put(YEAR,year);
        values.put(SCORE,score);
        db.insert(DATABASE_TABLE, null,values);
        db.close();
    }

    public boolean deleteMovie (String title, String year){
        db=this.getWritableDatabase();
        boolean result=false;
        String args[]=new String[]{title, year};
        String q="SELECT * FROM movies WHERE title=? AND year=?";
        Cursor cursor=db.rawQuery(q,args);
        if (cursor.moveToFirst()){
            int id=Integer.parseInt(cursor.getString(0));
            db.delete(DATABASE_TABLE,COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            result=true;
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean editMovie(int id, String title, String year, int score){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TITLE,title);
        values.put(YEAR,year);
        values.put(SCORE,score);

        db.update(DATABASE_TABLE,values,COLUMN_ID +"=?",new String[]{String.valueOf(id)});

        db.close();
        return true;
    }



    public Cursor findMovie (String title,String year){
        db=this.getWritableDatabase();
        String args[]=new String[]{title, year};
        String q="SELECT * FROM movies WHERE title=? AND year=?";
        Cursor cursor=db.rawQuery(q,args);
        return cursor;
    }

    public void topMovies() {
        String q = "SELECT * FROM movies ORDER BY score DESC LIMIT 3";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1) +" "+ cursor.getString(2) +"\n Score: "+ cursor.getString(3) + "/10";
                ShowTopMoviesActivity.ArrayofName.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void bottomMovies() {
        String q = "SELECT * FROM movies ORDER BY score LIMIT 3";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1) +" "+ cursor.getString(2) +"\nScore: "+ cursor.getString(3) + "/10";
                ShowBottomMoviesActivity.ArrayofName.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void allMovies() {
        String q = "SELECT * FROM movies";

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1) +" "+ cursor.getString(2) +"\n Score: "+ cursor.getString(3) + "/10";
                ShowAllMoviesActivity.ArrayofName.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
