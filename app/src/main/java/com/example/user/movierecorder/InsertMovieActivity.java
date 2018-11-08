package com.example.user.movierecorder;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import static com.example.user.movierecorder.BaseActivity.dbHelper;

public class InsertMovieActivity extends AppCompatActivity {
    EditText textTitle, textYear;
    RatingBar scoreBar;
    private static final String TAG="MovieHelper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_movie);

        textTitle=(EditText)findViewById(R.id.editText1);
        textYear=(EditText)findViewById(R.id.editText2);
        scoreBar=(RatingBar) findViewById(R.id.ratingBar);

        if(dbHelper==null)
            dbHelper = new MovieHelper(getApplicationContext(), null, null, 1);
    }

    public void insertMovie(View view){
        String title=textTitle.getText().toString();
        String year=textYear.getText().toString();
        int score=(int)scoreBar.getRating();
        if((title==null || title.isEmpty()) && (year==null || year.isEmpty())){
            Toast.makeText(this, getResources().getString(R.string.no_title_year_toast), Toast.LENGTH_SHORT).show();
        }else if(title==null || title.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.no_title_toast), Toast.LENGTH_SHORT).show();
        }else if (year==null || year.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.no_year_toast), Toast.LENGTH_SHORT).show();
        }else if (Integer.valueOf(year)<1878 || Integer.valueOf(year)>2030){
            Toast.makeText(this, getResources().getString(R.string.wrong_year_toast), Toast.LENGTH_SHORT).show();
        }else{
            Cursor cursor = dbHelper.findMovie(title, year);

            if (cursor.moveToFirst()) {
                Toast.makeText(this, getResources().getString(R.string.movie_exists_toast), Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.saveMovie(title, year, score);
                Toast.makeText(this, getResources().getString(R.string.saved_toast), Toast.LENGTH_SHORT).show();
                textTitle.setText("");
                textYear.setText("");
                scoreBar.setRating(0);
            }
            cursor.close();
            finish();
        }
    }

}