package com.example.user.movierecorder;

import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected static MovieHelper dbHelper=null;

    @Override
    protected void onPause() {

        // TODO close database
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if(dbHelper==null)
            dbHelper = new MovieHelper(getApplicationContext(), null, null, 1);

        // TODO open database
    }
}
