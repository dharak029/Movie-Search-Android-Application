package com.example.dharak029.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    Button btnSearch;
    ListView lstResults;
    EditText editSearch;
    static ArrayList<Movie> movieArrayList;
    static ArrayList<Movie> favoriteList;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteList = new ArrayList<Movie>();
        btnSearch = (Button)findViewById(R.id.btnSearch);
        lstResults = (ListView)findViewById(R.id.lstResults);
        editSearch = (EditText)findViewById(R.id.editSearch);
        context = getApplicationContext();

        if(retrieveFromPreference()==null || retrieveFromPreference().size()==0){
            favoriteList = new ArrayList<Movie>();
        }
        else{
            favoriteList = retrieveFromPreference();
        }

        //https://api.themoviedb.org/3/search/movie?query=Jack+Reacher&api_key=1ceb003b4b31dc0f0ae2010b127b7df5
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.themoviedb.org/3/search/movie?query=";
                url = url+editSearch.getText().toString().replaceAll("\\s","+")+"&api_key=1ceb003b4b31dc0f0ae2010b127b7df5&page=1";
                new GetResults(MainActivity.this).execute(url);
            }
        });

        lstResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Display.class);
                intent.putExtra("movie",movieArrayList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_Popularity)
        {
            Comparator<Movie> comparator = new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    Double scan1 = o1.getPopularity();
                    Double scan2 = o2.getPopularity();
                    Integer scanCompare = Double.compare(scan1, scan2);
                    return scanCompare;
                }
            };
            Collections.sort(movieArrayList,comparator);
            CustomAdapter resultAdapter = new CustomAdapter(context,R.layout.movie_item,movieArrayList);
            lstResults.setAdapter(resultAdapter);
            lstResults.setItemsCanFocus(true);
        }
        else if (item.getItemId() == R.id.action_Favorites)
        {
            if(MainActivity.favoriteList!=null && MainActivity.favoriteList.size()!=0){
                Intent intent = new Intent(context,Favorites.class);
                startActivity(intent);
            }
        }
        else if (item.getItemId() == R.id.action_Rating)
        {
            Comparator<Movie> comparator = new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    Double scan1 = o1.getRating();
                    Double scan2 = o2.getRating();
                    Integer scanCompare = Double.compare(scan1, scan2);
                    return scanCompare;
                }
            };
            Collections.sort(movieArrayList,comparator);
            CustomAdapter resultAdapter = new CustomAdapter(context,R.layout.movie_item,movieArrayList);
            lstResults.setAdapter(resultAdapter);
            lstResults.setItemsCanFocus(true);
        }
        else{
            finish();
            moveTaskToBack(true);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static ArrayList<Movie>  retrieveFromPreference(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("favoriteList", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        ArrayList<Movie> favoriteList = gson.fromJson(json, type);
        return favoriteList;
    }
    public static void addToPreference(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Log.d("demo",""+favoriteList.size());
        Gson gson = new Gson();
        String json = gson.toJson(favoriteList);
        editor.putString("favoriteList", json);
        editor.commit();
    }
    public static void setData(ArrayList<Movie> list){
        movieArrayList = list;
    }
}
