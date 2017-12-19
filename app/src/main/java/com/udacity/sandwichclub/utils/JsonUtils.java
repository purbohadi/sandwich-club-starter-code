package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // parse JSON item into sandwich object
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichItem = new JSONObject(json);

            JSONObject nameObj = sandwichItem.getJSONObject("name");
            String mainName = nameObj.getString("mainName");
            JSONArray alsoKnownAs = nameObj.getJSONArray("alsoKnownAs");
            String placeOfOrigin = "";
            if(sandwichItem.getString("placeOfOrigin")!=null)
                placeOfOrigin = sandwichItem.getString("placeOfOrigin");

            String description = sandwichItem.getString("description");

            String imgUrl = sandwichItem.getString("image");
            JSONArray ingredientsArr = sandwichItem.getJSONArray("ingredients");

            ArrayList<String> alias = new ArrayList<>();
            for (int i=0; i < alsoKnownAs.length(); i++)
            {
                alias.add(alsoKnownAs.getString(i));
            }

            ArrayList<String> ingredients = new ArrayList<>();
            for (int i=0; i < ingredientsArr.length(); i++)
            {
                ingredients.add(ingredientsArr.getString(i));
            }

            // set parsed data into sandwich object
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alias);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(imgUrl);
            sandwich.setIngredients(ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
