package com.example.dharak029.moviesearch;

import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dharak029 on 10/16/2017.
 */

public class GetResults extends AsyncTask<String,Void,ArrayList<Movie>> {

    MainActivity activity=null;
    public GetResults(MainActivity activity) {
        this.activity=activity;
    }

    BufferedReader br = null;
    URL url = null;
    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        try {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }

                ArrayList<Movie> musicInfoArrayList = MovieSearchUtil.parseMovieSearchQuery(sb.toString());
                return musicInfoArrayList;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {

        activity.setData(movies);
        ListView listView = (ListView)activity.findViewById(R.id.lstResults);
        CustomAdapter resultAdapter = new CustomAdapter(activity,R.layout.movie_item,movies);
        listView.setAdapter(resultAdapter);
        listView.setItemsCanFocus(true);
        super.onPostExecute(movies);
    }
}
