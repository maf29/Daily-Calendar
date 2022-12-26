package com.example.dailycalendar;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyJSONLoader extends JSONLoader{
    public MyJSONLoader(String fileName) {
        super(fileName);
    }

    @Override
    public JSONObject loadJSONFromAsset(Context context) {
        try {
            return super.loadJSONFromAsset(context);
        } catch (IOException e) {
            // handle IOException
        } catch (JSONException e) {
            // handle JSONException
        }
        return null;
    }
}
