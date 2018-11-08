package com.example.user.movierecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.user.movierecorder.BaseActivity.dbHelper;

public class DeleteMovieActivity extends AppCompatActivity {
    EditText textTitle, textYear;
    private static final String TAG="MovieHelper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_movie);
        textTitle=(EditText)findViewById(R.id.editText1);
        textYear=(EditText)findViewById(R.id.editText2);

        if(dbHelper==null)
            dbHelper = new MovieHelper(getApplicationContext(), null, null, 1);
    }
    public void deleteMovie (View view){
        String title=textTitle.getText().toString();
        String year=textYear.getText().toString();
        if((title==null || title.isEmpty()) && (year==null || year.isEmpty())){
            Toast.makeText(this, getResources().getString(R.string.no_title_year_toast), Toast.LENGTH_SHORT).show();
        }else if(title==null || title.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.no_title_toast), Toast.LENGTH_SHORT).show();
        }else if (year==null || year.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.no_year_toast), Toast.LENGTH_SHORT).show();
        }else {
            boolean result = dbHelper.deleteMovie(title, year);
            if (result) {
                Toast.makeText(this, getResources().getString(R.string.movie_deleted_toast) + " " + title + " " + getResources().getString(R.string.movie_deleted_toast2), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, getResources().getString(R.string.no_match_found_toast), Toast.LENGTH_SHORT).show();
            }
        }
        //textTitle.setText("");
        //textYear.setText("");
    }
}
