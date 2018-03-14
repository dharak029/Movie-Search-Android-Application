package com.example.dharak029.moviesearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class Display extends AppCompatActivity {

    TextView txtTitle,txtReleaseDate,txtRating,txtOverview;
    ImageView imgMvy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        txtRating = (TextView)findViewById(R.id.txtRating);
        txtReleaseDate = (TextView)findViewById(R.id.txtDate);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtOverview = (TextView)findViewById(R.id.txtOverView);
        imgMvy = (ImageView)findViewById(R.id.imgMvy);

        if(getIntent().getSerializableExtra("movie")!=null){

            Movie movie = (Movie) getIntent().getSerializableExtra("movie");
            txtTitle.setText(movie.getMovieName());
            txtReleaseDate.setText(movie.getReleaseDate());
            txtRating.setText(movie.getRating()+"/10");
            txtOverview.setText(movie.getOverview());
            new GetImage().execute("http://image.tmdb.org/t/p/w342/"+movie.getPosterPath());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_home1)
        {

                Intent intent = new Intent(Display.this,MainActivity.class);
                startActivity(intent);


        }
        else{
            finish();
            moveTaskToBack(true);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetImage extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                return image;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            if(s != null) {
                imgMvy.setImageBitmap(s);
            }
        }
    }
}
