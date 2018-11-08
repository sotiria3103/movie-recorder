package com.example.user.movierecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.movierecorder.BaseActivity.dbHelper;

public class ShowTopMoviesActivity extends AppCompatActivity {
    private ListView listView;
    public static ArrayList<String> ArrayofName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_top_movies);

        listView = (ListView) findViewById(R.id.listView1);
        ArrayofName.clear();

        if(dbHelper==null)
            dbHelper = new MovieHelper(getApplicationContext(), null, null, 1);

        dbHelper.topMovies();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ArrayofName); //to simple list item einai etoimo xml,den einai kapoio pou eftia3a egw

        listView.setAdapter(adapter);

        //otan patas panw emfanizei toast me ton titlo,to etos kai to score
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
