package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)
            throws JSONException {

        Sandwich s;
        String[] nicknames = null;
        String[] ing = null;

        JSONObject sandwich = new JSONObject(json);
        JSONObject ss = sandwich.getJSONObject("name");
        String name = ss.getString("mainName");
        JSONArray aka = ss.getJSONArray("alsoKnownAs");
        nicknames = new String[aka.length()];
        for (int i = 0; i < aka.length(); i++){
            nicknames[i] = aka.getString(i);
        }

        String place = sandwich.getString("placeOfOrigin");

        String description = sandwich.getString("description");

        String image = sandwich.getString("image");

        JSONArray ingredients = sandwich.getJSONArray("ingredients");
        ing = new String[ingredients.length()];
        for (int i = 0; i < ingredients.length(); i++){
            ing[i] = ingredients.getString(i);
        }
        s = new Sandwich(name, nicknames, place, description, image, ing);
        return s;
    }
}
