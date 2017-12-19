package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static final String EXTRA_SCROLL_POSITION = "extra_scroll_position";
    private static final int DEFAULT_SCROLL_POSITION = 0;

    private ScrollView mSvDetails;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save scrollview position
        outState.putInt(EXTRA_SCROLL_POSITION, mSvDetails.getScrollY());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mSvDetails = findViewById(R.id.details_sv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        // restore scrollview position
        if(savedInstanceState!=null){
            final int y = savedInstanceState.getInt(EXTRA_SCROLL_POSITION, DEFAULT_SCROLL_POSITION);
            mSvDetails.post(new Runnable() {
                @Override
                public void run() {
                    mSvDetails.scrollTo(0,y);
                }
            });
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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //populate bind detail data into view
        TextView alias = findViewById(R.id.also_known_tv);

        StringBuffer aliasName = new StringBuffer();

        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            aliasName.append(sandwich.getAlsoKnownAs().get(i));
            if(i<sandwich.getAlsoKnownAs().size()-1)
                aliasName.append(", ");
            else
                aliasName.append(".");

        }

        alias.setText(aliasName);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        TextView ingredient = findViewById(R.id.ingredients_tv);

        StringBuffer ingredients = new StringBuffer();
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            ingredients.append(sandwich.getIngredients().get(i));
            if(i<sandwich.getIngredients().size()-1)
                ingredients.append(", ");
            else
                ingredients.append(".");
        }

        ingredient.setText(ingredients);
    }
}
