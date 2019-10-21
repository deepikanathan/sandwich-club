package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

/**
 * Class to display the Sandwich properties such as Place of Origin, Description, Ingredients etc
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //  throw error when Intent is not found
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Pass the single Sandwich string from sandwich_details to JsonUtils and parse the string to Sandwich object
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        //  unable to parse string into Sandwich object
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    /**
     * Show a Toast message when error occurs
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populate the DetailActivity with values in Sandwich object
     * @param sandwich Sandwich object
     */
    private void populateUI(Sandwich sandwich) {

        // set the Sandwich name as the title
        setTitle(sandwich.getMainName());

        // Sandwich Image
        setImage(sandwich);

        //	Also known as
        setAlsoKnownAs(sandwich);

        //	Place of Origin
        setPlaceOfOrigin(sandwich);

        // Description
        setDescription(sandwich);

        // Ingredients
        setIngredients(sandwich);
    }

    /**
     * Set sandwich image. Set a placeholder image if original image is unavailable
     * @param sandwich Sandwich object
     */
    private void setImage(Sandwich sandwich)
    {
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        //  Set the image using the Picasso class.
        //  Set a placeholder image that will be shown if no image is available at the link specified in the JSON
        //  The below 4 lines were taken from "https://knowledge.udacity.com/questions/8039"
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_sandwich_placeholder)
                .into(ingredientsIv);
    }

    /**
     * Set Also Known As property
     * @param sandwich Sandwich object
     */
    private void setAlsoKnownAs(Sandwich sandwich)
    {
        List<String> alsoKnownAsValues = sandwich.getAlsoKnownAs();
        TextView alsoKnownAsTV = findViewById(R.id.also_known_val);
        if (alsoKnownAsValues == null || alsoKnownAsValues.size() == 0)
        {
            alsoKnownAsTV.setText(R.string.value_not_known);
        }
        else
        {
            sandwich.setAlsoKnownAs(alsoKnownAsValues);
            StringBuilder alsoKnownAsSB = new StringBuilder();
            for (String alsoKnownAsValue : alsoKnownAsValues)
            {
                alsoKnownAsSB.append("\u2022").append(alsoKnownAsValue).append("\n");
            }
            //  delete the extra line at the end of the list
            alsoKnownAsSB.setLength(alsoKnownAsSB.length() - 1);
            alsoKnownAsTV.setText(alsoKnownAsSB);
        }
    }

    /**
     * Set Place of Origin property
     * @param sandwich Sandwich object
     */
    private void setPlaceOfOrigin(Sandwich sandwich)
    {
        String placeOfOriginValue = sandwich.getPlaceOfOrigin();
        TextView placeOfOriginTV = findViewById(R.id.origin_val);
        if (placeOfOriginValue == null || placeOfOriginValue.isEmpty())
        {
            placeOfOriginTV.setText(R.string.value_not_known);
        }
        else
        {
            sandwich.setPlaceOfOrigin(placeOfOriginValue);
            placeOfOriginTV.setText("\u2022" + placeOfOriginValue);
        }
    }

    /**
     * Set Description property
     * @param sandwich Sandwich object
     */
    private void setDescription(Sandwich sandwich)
    {
        String descriptionValue = sandwich.getDescription();
        TextView descriptionTV = findViewById(R.id.description_val);
        if (descriptionValue == null || descriptionValue.isEmpty())
        {
            descriptionTV.setText(R.string.value_not_known);
        }
        else
        {
            sandwich.setDescription(descriptionValue);
            descriptionTV.setText(descriptionValue);
        }
    }

    /**
     * Set Ingredients property
     * @param sandwich Sandwich object
     */
    private void setIngredients(Sandwich sandwich)
    {
        List<String> ingredientsValues = sandwich.getIngredients();
        TextView ingredientsTV = findViewById(R.id.ingredients_val);
        if (ingredientsValues == null || ingredientsValues.size() == 0)
        {
            ingredientsTV.setText(R.string.value_not_known);
        }
        else
        {
            sandwich.setIngredients(ingredientsValues);
            StringBuilder ingredientsSB = new StringBuilder();

            for (String ingredientValue : ingredientsValues)
            {
                ingredientsSB.append("\u2022").append(ingredientValue).append("\n");
            }

            //  delete the extra line at the end of the list
            ingredientsSB.setLength(ingredientsSB.length() - 1);
            ingredientsTV.setText(ingredientsSB);
        }
    }
}