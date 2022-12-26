package com.example.dailycalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        //cambiar por los valores de JSON
        //por ahora no hace falta porque no tocaremos esta vista
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
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

    public void writeJson(Context context, JSONObject jsonObject) throws IOException {
        /*OutputStream outputStream = ;

        OutputStream os = new FileOutputStream(context.getResources().openRawResource(R.raw.football));
        OutputStreamWriter osw = new OutputStreamWriter(os);

        osw.write(jsonObject.toString());
        osw.close();*/
    }

    public void saveEventAction(View view) throws IOException, JSONException {

        String eventName = eventNameET.getText().toString();
        System.out.println("eventName ==> "+eventName);
        LocalDate date = CalendarUtils.selectedDate;
        Event newEvent = new Event(eventName, date, time);
        Event.eventsList.add(newEvent);

        String jsonString = readJsonDataFromFile(getApplicationContext());
        JSONObject jsonObject = new JSONObject(jsonString);

        //JSONArray matchesArray = jsonObject.optJSONArray("matches");
        JSONArray matches = jsonObject.getJSONArray("matches");
        System.out.println("matches: "+matches);
        // Create a new JSONObject to add to the array
        JSONObject newObject = new JSONObject();
        newObject.put("matchDate", date);

        JSONObject homeTeamObject = new JSONObject();
        homeTeamObject.put("idTeam", 7);    //falta buscar si ya existe id y añadirlo sino
        newObject.put("homeTeam", homeTeamObject);

        JSONObject awayTeamObject = new JSONObject();
        awayTeamObject.put("idTeam", 10);
        newObject.put("awayTeam", awayTeamObject);

        // Add the new JSONObject to the JSONArray
        //matchesArray.put(newObject);
        matches.put(newObject);
        System.out.println("matches (despues): "+matches);
        // Write the modified JSONObject back to the file in the raw folder
        //writeJsonDataToFile(jsonObject.toString());
        //FileUtils.addJsonObjectToArrayInFile(this, newObject, "matches", "football.json");
        /*try (FileWriter writer = new FileWriter("foot.json")) {
            writer.write(jsonObject.toString());
        }*/
        System.out.println("jsonObject --> "+jsonObject);

        // Write the modified JSONObject to the football.json file


        try {
            // Open the JSON file for writing
            // Get the path to the app's internal files directory
            File internalFilesDir = getFilesDir();

            // Create the file in the internal files directory
            File file = new File(internalFilesDir, "my_files/data.json");

            // Open the file for writing
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            // Write the JSON string to the file
            fileOutputStream.write(jsonObject.toString().getBytes());
            fileOutputStream.close();
        }catch (Exception e) {
            // If an error occurs, log the error message
            Log.e("MainActivity", "Error saving file: " + e.getMessage());
        }


        /*
        * Aqui va la parte de añadir al json
        * */
        finish();
    }
}
