package apps.marieclaire.com.sandwichclub;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import android.databinding.DataBindingUtil;

import apps.marieclaire.com.sandwichclub.model.Sandwich;
import apps.marieclaire.com.sandwichclub.utils.JsonUtils;
import apps.marieclaire.com.sandwichclub.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ActivityDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        // ImageView sandwichImag = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {

            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


        setTitle(sandwich.getMainName());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(detailBinding.imageIv);


        // Get the PlaceOfOrigin
        String originText = sandwich.getPlaceOfOrigin();
        // if Origin is Empty, set this text
        if (originText.isEmpty()) {
            detailBinding.originTv.setText(R.string.message_unknown);
        } else {
            detailBinding.originTv.setText(originText);
        }

        // Get the other names for the Sandwich
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();

        if (alsoKnownAsList.size() == 0) {
            detailBinding.alsoKnownAsLabel.setVisibility(View.GONE);
            detailBinding.alsoKnownAsLabel.setVisibility(View.GONE);
        } else { // Display Other name as  Label & TextView
            detailBinding.alsoKnownAsLabel.setVisibility(View.VISIBLE);
            detailBinding.alsoKnownAsLabel.setVisibility(View.VISIBLE);


            StringBuilder otherNames = new StringBuilder();


            for (String otherName : alsoKnownAsList) {
                otherNames.append(otherName).append(", ");
            }

            otherNames.setLength(otherNames.length() - 2);

            // Set  the SANDWICH other names in the TextView
            detailBinding.alsoKnownTv.setText(otherNames);
        }

        // Get SANDWICH's ingredients
        List<String> ingredientsList = sandwich.getIngredients();

        if (ingredientsList.size() == 0) {
            detailBinding.ingredientsTv.setText(R.string.message_unknown);
        } else {
            StringBuilder ingredients = new StringBuilder();


            for (String ingredient : ingredientsList) {
                ingredients.append(ingredient).append(", ");
            }

            ingredients.setLength(ingredients.length() - 2);

            //  Display the Sandwich's ingredients
            detailBinding.ingredientsTv.setText(ingredients);
        }

        // Get the Sandwich's Description f
        String description = sandwich.getDescription();

        if (description.isEmpty()) {
            detailBinding.descriptionTv.setText(R.string.message_not_available);
        } else {
            detailBinding.descriptionTv.setText(description);
        }
    }
}
