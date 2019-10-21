package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses JSON into Sandwich objects
 */
public class JsonUtils {

    private static final String TAG = "JsonUtils";
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String IMAGE = "image";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String INGREDIENTS = "ingredients";

    /**
     * Parse the JSON and return Sandwich object
     * @param json JSON
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {

        // check if the JSON is empty
        if (json == null || json.isEmpty()) {
            Log.e(TAG, "JSON passed to parseSandwichJson method is empty");
            return null;
        }

        Sandwich sandwich = null;

        // Parse the json string
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject(NAME);

            //  get the Sandwich name
            String mainName = name.getString(MAIN_NAME);

            // get the image
            String sandwichImage = jsonObject.getString(IMAGE);

            //  get Also Known As values
            JSONArray alsoKnownAsJsonArray = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++)
            {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            // get Place of Origin
            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);

            // get Description
            String description = jsonObject.getString(DESCRIPTION);

            //  get Ingredients
            JSONArray ingredientsJsonArray = jsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++)
            {
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, sandwichImage, ingredients);
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Exception occured when parsing the JSON in parseSandwichJson method", e);
            return null;
        }
        return sandwich;
    }
}

