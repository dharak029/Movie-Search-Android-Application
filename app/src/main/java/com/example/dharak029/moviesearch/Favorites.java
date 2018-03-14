package com.example.dharak029.moviesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Favorites extends AppCompatActivity {

    ListView lstFavotites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        lstFavotites = (ListView)findViewById(R.id.lstFavorites);
        CustomAdapter resultAdapter = new CustomAdapter(this,R.layout.movie_item,MainActivity.favoriteList);
        lstFavotites.setAdapter(resultAdapter);
        lstFavotites.setItemsCanFocus(true);

        if(getIntent().getSerializableExtra("list")!=null){
            ArrayList<Movie> movies = (ArrayList<Movie>) getIntent().getSerializableExtra("list");
            CustomAdapter adapter = new CustomAdapter(this,R.layout.movie_item,movies);
            lstFavotites.setAdapter(resultAdapter);
            lstFavotites.setItemsCanFocus(true);
        }


        lstFavotites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Favorites.this,Display.class);
                intent.putExtra("movie",MainActivity.favoriteList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favmenu1, menu);
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
            Collections.sort(MainActivity.favoriteList,comparator);
            CustomAdapter resultAdapter = new CustomAdapter(this,R.layout.movie_item,MainActivity.favoriteList);
            lstFavotites.setAdapter(resultAdapter);
            lstFavotites.setItemsCanFocus(true);

        }
        else if (item.getItemId() == R.id.action_Home)
        {
            if(MainActivity.favoriteList!=null && MainActivity.favoriteList.size()!=0){
                Intent intent = new Intent(Favorites.this,MainActivity.class);
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
            Collections.sort(MainActivity.favoriteList,comparator);
            CustomAdapter resultAdapter = new CustomAdapter(this,R.layout.movie_item,MainActivity.favoriteList);
            lstFavotites.setAdapter(resultAdapter);
            lstFavotites.setItemsCanFocus(true);

        }
        else{
            finish();
            moveTaskToBack(true);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
