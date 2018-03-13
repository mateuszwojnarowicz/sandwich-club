package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //String KEYS used in JSON
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String jsonString) {
        try {

            //Entire JSON object
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONObject name = jsonObject.getJSONObject(KEY_NAME);

            //EXTRACTS mainName String from JSON
            String mainName = name.optString(KEY_MAIN_NAME);

            //EXTRACTS a list of other names from JSON
            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);
            List<String> alsoKnowsAs = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnowsAs.add(alsoKnownAsArray.optString(i));
            }

            //EXTRACTS placeOfOrigin String from JSON
            String placeOfOrigin = jsonObject.optString(KEY_PLACE_OF_ORIGIN);

            //EXTRACTS description String from JSON
            String description = jsonObject.optString(KEY_DESCRIPTION);

            //EXTRACTS image url as a String from JSON
            String image = jsonObject.optString(KEY_IMAGE);

            //EXTRACTS a list of ingredients from JSON
            JSONArray ingredientsArray = jsonObject.getJSONArray(KEY_INGREDIENTS);
            List<String> ingredients = new ArrayList<String>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.optString(i));
            }

            //RETURNS sandwich object
            return new Sandwich(mainName, alsoKnowsAs, placeOfOrigin, description, image, ingredients);


        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

}
