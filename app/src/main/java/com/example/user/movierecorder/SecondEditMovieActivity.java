package com.example.user.movierecorder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.user.movierecorder.BaseActivity.dbHelper;

public class SecondEditMovieActivity extends AppCompatActivity {
    EditText textTitle, textYear;
    RatingBar scoreBar;
    private int id;
    private static final String TAG="MovieHelper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_edit_movie);

        //takes the values from EditMovieActivity.java
        Intent intent=getIntent();
        id=intent.getExtras().getInt("id");
        String title=intent.getExtras().getString("title");
        String year=intent.getExtras().getString("year");
        int rating=intent.getExtras().getInt("rating");

        textTitle=(EditText)findViewById(R.id.editText1);
        textYear=(EditText)findViewById(R.id.editText2);
        scoreBar=(RatingBar) findViewById(R.id.ratingBar);

        //sets as defaults values the existing values
        textTitle.setText(title, TextView.BufferType.EDITABLE);
        textYear.setText(year, TextView.BufferType.EDITABLE);
        scoreBar.setRating(rating);

        if(dbHelper==null)
            dbHelper = new MovieHelper(getApplicationContext(), null, null, 1);
    }

    public void editMovie(View view){
        String title=textTitle.getText().toString();
        String year=textYear.getText().toString();
        int score=(int)scoreBar.getRating();

        boolean result=dbHelper.editMovie(id,title,year,score);
        if(result){
            Toast.makeText(this, getResources().getString(R.string.movie_edited_toast)+" "+title+" "+getResources().getString(R.string.movie_edited_toast2), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, getResources().getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
