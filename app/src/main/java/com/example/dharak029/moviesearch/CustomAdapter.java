package com.example.dharak029.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dharak029 on 10/16/2017.
 */

public class CustomAdapter extends ArrayAdapter<Movie> {
    final ArrayList<Movie> movieArrayList;
    Context context;
    int resource;
    Movie movie;

    public CustomAdapter(Context context,int resource,ArrayList<Movie> movieArrayList){
        super(context,resource,movieArrayList);
        this.context = context;
        this.resource = resource;
        this.movieArrayList = movieArrayList;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resource,parent,false);
        }

        TextView txtMovieName = (TextView)convertView.findViewById(R.id.txtMovieName);
        TextView txtReleaseYear =  (TextView)convertView.findViewById(R.id.txtReleaseYear);
        movie = movieArrayList.get(position);
        txtMovieName.setText(movie.getMovieName());
        txtReleaseYear.setText("Released in "+movie.getReleaseDate().substring(0,4));
        new GetImage(convertView).execute("http://image.tmdb.org/t/p/w154/"+movie.getPosterPath());

        final ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.favoriteButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageButton.getTag()==null ) {
                    imageButton.setImageResource(android.R.drawable.btn_star_big_on);
                    imageButton.setTag("off");
                    Movie movie = movieArrayList.get(position);
                    MainActivity.favoriteList.add(movieArrayList.get(position));
                    MainActivity.addToPreference();
                    Toast.makeText(context, "Movie added to favorites."+movieArrayList.get(position).getMovieName(), Toast.LENGTH_SHORT).show();

                }
                else{
                    imageButton.setImageResource(android.R.drawable.btn_star_big_off);
                    imageButton.setTag(null);
                    for(int i=0;i<MainActivity.favoriteList.size();i++){
                        if(movieArrayList.get(position).getMovieName().equals(MainActivity.favoriteList.get(i).getMovieName())) {
                            MainActivity.favoriteList.remove(i);
                            MainActivity.addToPreference();
                            Toast.makeText(context, "Movie removed from favorites."+movieArrayList.get(position).getMovieName(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(context.toString().contains("Favorites") && movie != null) {
                        Intent intent = new Intent(context, Favorites.class);
                        intent.putExtra("list",movieArrayList);
                        context.startActivity(intent);
                    }

                }

            }
        });

        if(MainActivity.favoriteList != null) {
            for (int i = 0; i < MainActivity.favoriteList.size(); i++) {
                if (MainActivity.favoriteList.get(i).getMovieName().equals(movieArrayList.get(position).getMovieName())) {
                    imageButton.setImageResource(android.R.drawable.btn_star_big_on);
                    imageButton.setTag("off");
                }
            }
        }

        return convertView;
    }

    public class GetImage extends AsyncTask<String,Void,Bitmap> {

        View convertview;

        public GetImage(View convertview){
            this.convertview = convertview;
        }

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
                ImageView img = (ImageView) convertview.findViewById(R.id.imgMovie);
                img.setImageBitmap(s);
            }
        }
    }
}
