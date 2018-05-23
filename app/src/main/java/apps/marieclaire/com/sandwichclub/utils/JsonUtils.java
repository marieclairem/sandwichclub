package apps.marieclaire.com.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import apps.marieclaire.com.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class JsonUtils {
/**
 * Tag for the log messages
 */
    //public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /**
     * An empty private constructor
     *
     */
    private JsonUtils() {
    }

    /**
     * Return a {@link Sandwich} object that has been built up from
     * parsing the given JSON.
     */
    public static Sandwich parseSandwichJson(String json) {

        // check If the JSON string is empty .
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // create Sandwich Object
        Sandwich sandwichObj = null;

        // Try to parse the JSON string and Catch the exception .
        try {


            JSONObject mainJsonObject = new JSONObject(json);


            JSONObject name = mainJsonObject.getJSONObject("name");

            // get  the mainName
            String mainName = name.getString("mainName");

            List<String> sandwichAlsoKnownAsList = new ArrayList<>();

            JSONArray sandwichAlsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            int countAlsoKnownAsArray = sandwichAlsoKnownAsArray.length();
            //  Add JSONArray into the ArrayList
            for (int i = 0; i < countAlsoKnownAsArray; i++) {
                String otherName = sandwichAlsoKnownAsArray.getString(i);
                sandwichAlsoKnownAsList.add(otherName);
            }

            // Get the placeOfOrigin from the  "placeOfOrigin"
            String placeOfOrigin = mainJsonObject.getString("placeOfOrigin");

            // Get the description from the  "description"
            String description = mainJsonObject.getString("description");

            // Get the image path from the  "image"
            String image = mainJsonObject.getString("image");

            List<String> sandwichIngredientsList = new ArrayList<>();
            // Get the JSONArray
            JSONArray sandwichIngredientsArray = mainJsonObject.getJSONArray("ingredients");
            int count = sandwichIngredientsArray.length();

            // Add JSONArray into the ArrayList
            for (int j = 0; j < count; j++) {
                String ingredient = sandwichIngredientsArray.getString(j);
                sandwichIngredientsList.add(ingredient);
            }

            // Sandwich Object with the data from the Json
            sandwichObj = new Sandwich(mainName, sandwichAlsoKnownAsList, placeOfOrigin, description, image, sandwichIngredientsList);


        } catch (JSONException e) {

            Log.e("JsonUtils", "Problem parsing the Sandwich JSON Object", e);
        }

        // return the Sandwich Object
        return sandwichObj;
    }
}
