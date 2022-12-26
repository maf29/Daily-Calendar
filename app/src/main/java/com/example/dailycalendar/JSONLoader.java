package com.example.dailycalendar;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public abstract class JSONLoader {
    private String mFileName;

    public JSONLoader(String fileName) {
        mFileName = fileName;
    }

    public JSONObject loadJSONFromAsset(Context context) throws IOException, JSONException {
        InputStream is = context.getAssets().open(mFileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        String json = new String(buffer, "UTF-8");
        return new JSONObject(json);
    }
}
