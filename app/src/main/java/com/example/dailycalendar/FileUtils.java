package com.example.dailycalendar;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static void addJsonObjectToArrayInFile(Context context, JSONObject jObject, String arrayName, String fileName) {
        try {
            // Read the contents of the file into a String
            FileInputStream inputStream = context.openFileInput(fileName);
            StringBuilder fileContent = new StringBuilder();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                fileContent.append((char) ch);
            }
            inputStream.close();

            // Parse the file content into a JSONObject
            JSONObject jsonObject = new JSONObject(fileContent.toString());

            // Get the array from the object
            JSONArray jsonArray = jsonObject.getJSONArray(arrayName);

            // Add the JSON object to the array
            jsonArray.put(jObject);

            // Write the modified object back to the file as a String
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
