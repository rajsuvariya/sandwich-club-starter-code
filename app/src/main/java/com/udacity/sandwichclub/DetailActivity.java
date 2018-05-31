package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .placeholder(R.mipmap.ic_image_placeholder)
                .into(ingredientsIv);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle(sandwich.getMainName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        originTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());

        StringBuilder ingredientsString = new StringBuilder();
        for (String ingredient : sandwich.getIngredients()) {
            ingredientsString.append(ingredient);
            ingredientsString.append(", ");
        }
        if (!TextUtils.isEmpty(ingredientsString.toString())) {
            ingredientsTv.append(ingredientsString.subSequence(0, ingredientsString.length() - 2).toString());
        } else {
            ingredientsTv.setText(R.string.data_unavailable);
        }

        StringBuilder alsoKnownString = new StringBuilder();
        for (String knowAs : sandwich.getAlsoKnownAs()) {
            alsoKnownString.append(knowAs);
            alsoKnownString.append(", ");
        }
        if (!TextUtils.isEmpty(alsoKnownString.toString())) {
            alsoKnownTv.append(alsoKnownString.subSequence(0, alsoKnownString.length() - 2).toString());
        } else {
            alsoKnownTv.setText(R.string.data_unavailable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
