package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwith = new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);

            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            JSONArray alsoKnownAsArray = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsArray.getString(i));
            }

            ArrayList<String> ingredientsList = new ArrayList<>();
            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }

            sandwith.setMainName(jsonObject.getJSONObject("name").getString("mainName"));
            sandwith.setAlsoKnownAs(alsoKnownAsList);
            sandwith.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwith.setDescription(jsonObject.getString("description"));
            sandwith.setImage(jsonObject.getString("image"));
            sandwith.setIngredients(ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;   // If there is any exception during parsing the JSON
        }
        return sandwith;
    }
}
