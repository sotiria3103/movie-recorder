package com.example.user.movierecorder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.user.movierecorder.BaseActivity.dbHelper;

public class EditMovieActivity extends AppCompatActivity {
    //private Button saveButton;
    EditText textTitle, textYear;
    private static final String TAG="MovieHelper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        textTitle=(EditText)findViewById(R.id.editText1);
        textYear=(EditText)findViewById(R.id.editText2);

        if(dbHelper==null)
            dbHelper = new MovieHelper(getApplicationContext(), null, null, 1);
    }

    public void editMovie (View view){
        String title=textTitle.getText().toString();
        String year=textYear.getText().toString();

        if((title==null || title.isEmpty()) && (year==null || year.isEmpty())){
            Toast.makeText(this, getResources().getString(R.string.no_title_year_toast), Toast.LENGTH_SHORT).show();
        }else if(title==null || title.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.no_title_toast), Toast.LENGTH_SHORT).show();
        }else if (year==null || year.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.no_year_toast), Toast.LENGTH_SHORT).show();
        }else{
            Cursor cursor = dbHelper.findMovie(title, year);
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                int rating = cursor.getInt(cursor.getColumnIndex("score"));
                cursor.close();
                finish();
                launchSecondEditMovieActivity(id, title, year, rating);
            } else {
                Toast.makeText(this, getResources().getString(R.string.no_match_found_toast), Toast.LENGTH_SHORT).show();
                //textTitle.setText("");
                //textYear.setText("");
            }
        }
    }

    private void launchSecondEditMovieActivity(int id, String title, String year, int rating){
        Intent intent = new Intent(this, SecondEditMovieActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        intent.putExtra("year",year);
        intent.putExtra("rating",rating);
        startActivity(intent);
    }
}
