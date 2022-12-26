package com.example.dailycalendar;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Event extends AppCompatActivity
{
    public static ArrayList<Event> eventsList = new ArrayList<>();
    static Context mycontext;
    Event(Context context)
    {
        mycontext = context;
    }

    public static String readJsonDataFromFile(Context context) throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try{

            String jsonString = null;
            inputStream = context.getResources().openRawResource(R.raw.football);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream,"UTF-8"));

            while((jsonString = bufferedReader.readLine()) != null){
                builder.append(jsonString);
            }
        } finally {

            if(inputStream!=null){
                inputStream.close();
            }
        }
        return builder.toString();

    }


    public static ArrayList<Event> eventsForDate(Context context, LocalDate date) throws IOException, JSONException {
        ArrayList<Event> events = new ArrayList<>();
        String eventName = "PSG vs MAN";
        LocalDate fecha = LocalDate.of(2022, 12, 15);
        LocalTime tiempo = LocalTime.of(22, 15, 30, 123456789);
        Event newEve = new Event(eventName, fecha, tiempo);

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        String jsonString = readJsonDataFromFile(context);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray teamsArray = jsonObject.optJSONArray("teams");
        JSONArray matchesArray = jsonObject.optJSONArray("matches");

        for (int i = 0; i < matchesArray.length(); i++) {

            JSONObject match = matchesArray.optJSONObject(i);

            String matchDate = match.optString("matchDate");
            String[] dateParts = matchDate.split("T");
            LocalDate localDate = LocalDate.parse(dateParts[0], DateTimeFormatter.ISO_LOCAL_DATE);
            LocalTime localTime = LocalTime.parse(dateParts[1].substring(0, dateParts[1].length() - 1), DateTimeFormatter.ISO_LOCAL_TIME);
            JSONObject homeTeam = match.optJSONObject("homeTeam");
            int idTeamHome = homeTeam.optInt("idTeam");
            String code_home = teamsArray.optJSONObject(idTeamHome - 1).optString("code");

            JSONObject awayTeam = match.optJSONObject("awayTeam");
            int idTeamAway = awayTeam.optInt("idTeam");
            String code_away = teamsArray.optJSONObject(idTeamAway - 1).optString("code");

            String name = code_home + " vs " + code_away;
            Event Json_ev = new Event(name, localDate, localTime);

            if (Json_ev.getDate().equals(date)) {
                events.add(Json_ev);
            }

        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(Context context, LocalDate date, LocalTime time) throws IOException, JSONException {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        String jsonString = readJsonDataFromFile(context);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray teamsArray = jsonObject.optJSONArray("teams");
        JSONArray matchesArray = jsonObject.optJSONArray("matches");

        for (int i = 0; i < matchesArray.length(); i++) {

            JSONObject match = matchesArray.optJSONObject(i);

            String matchDate = match.optString("matchDate");
            String[] dateParts = matchDate.split("T");
            LocalDate localDate = LocalDate.parse(dateParts[0], DateTimeFormatter.ISO_LOCAL_DATE);
            LocalTime localTime = LocalTime.parse(dateParts[1].substring(0, dateParts[1].length() - 1), DateTimeFormatter.ISO_LOCAL_TIME);
            JSONObject homeTeam = match.optJSONObject("homeTeam");
            int idTeamHome = homeTeam.optInt("idTeam");
            String code_home = teamsArray.optJSONObject(idTeamHome - 1).optString("code");

            JSONObject awayTeam = match.optJSONObject("awayTeam");
            int idTeamAway = awayTeam.optInt("idTeam");
            String code_away = teamsArray.optJSONObject(idTeamAway - 1).optString("code");

            String name = code_home + " vs " + code_away;
            Event Json_ev = new Event(name, localDate, localTime);

            if (Json_ev.getDate().equals(date) && Json_ev.time.getHour() == time.getHour())  //if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(Json_ev);
            /*
             int eventHour = event.time.getHour();
            int cellHour = time.getHour();
             */

        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;

    /* Esto se tiene que modificar en funcion de los campos que tenemos en el JSOn y se tienen
     que generar tantas funciones como campos de estructura del JSON
     */
    public Event(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}


