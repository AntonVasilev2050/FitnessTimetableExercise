package com.example.fitnesstimetable;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static String offLineTimetableJSON = null;
    private final String url = "https://sample.fitnesskit-admin.ru/schedule/get_group_lessons_v2/1/";
    private static boolean isOnLine = true;

    private RecyclerView recyclerViewExercises;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ExercisesAdapter adapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String timetableJSON = getTimetableJSON(url);
        if(timetableJSON == null && preferences.getString("timetableJSON", null) != null){
            timetableJSON = preferences.getString("timetableJSON", null);
            Toast.makeText(this, "Нет связи с сервером, загруженны данные последнего сеанса", Toast.LENGTH_LONG).show();
        }
        if(timetableJSON == null){
            Toast.makeText(this, "Нет связи с сервером", Toast.LENGTH_SHORT).show();
        }
        exercises = getExercises(timetableJSON);
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        adapter = new ExercisesAdapter(exercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExercises.setAdapter(adapter);
    }

    private ArrayList<Exercise> getExercises(String timetableJSON){
        ArrayList<Exercise> exercises = new ArrayList<>();
        if(timetableJSON == null){
            exercises.add(new Exercise("нет данных", " -:- ", " -:-",
                    "нет данных", "нет данных", "нет данных",
                    1, 0));
            return exercises;
        }
        try {
            JSONArray jsonArray = new JSONArray(timetableJSON);
            for(int i = 0; i <= jsonArray.length(); i++){
                JSONObject jsonExercise = jsonArray.getJSONObject(i);
                String name = jsonExercise.getString("name");
                String startTime = jsonExercise.getString("startTime");
                String endTime = jsonExercise.getString("endTime");
                String teacher = jsonExercise.getString("teacher");
                String place = jsonExercise.getString("place");
                String description = jsonExercise.getString("description");
                int weekDay = jsonExercise.getInt("weekDay");
                int availability = jsonExercise.getInt("availability");
                exercises.add(new Exercise(name, startTime, endTime, teacher, place, description, weekDay, availability));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    private String getTimetableJSON(String url){
        String timetableJSON = null;
        DownloadTask task = new DownloadTask();
        try {
            timetableJSON = task.execute(url).get();
            if(timetableJSON != null) {
                preferences.edit().putString("timetableJSON", timetableJSON).apply();
            }
            return timetableJSON;
        } catch (ExecutionException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return null;
    }

    private static class DownloadTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null){
                    result.append(line);
                    line = bufferedReader.readLine();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                isOnLine = false;
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}