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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
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
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI();
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            getSupportActionBar().setTitle(sandwich.getMainName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView aka = (TextView) findViewById(R.id.also_known_tv);
        TextView ing = (TextView) findViewById(R.id.ingredients_tv);
        TextView place = (TextView) findViewById(R.id.origin_tv);
        TextView desc = (TextView) findViewById(R.id.description_tv);
        String alsoKnownAs = " ";
        String ingredients = " ";
        for(int i = 0; i < sandwich.getAlsoKnownAs().length; i++) {
            alsoKnownAs += sandwich.getAlsoKnownAs()[i];
            if(i != sandwich.getAlsoKnownAs().length - 1 ){
                alsoKnownAs += ", ";
            }
        }
        for(int i = 0; i < sandwich.getIngredients().length; i++) {
            ingredients += sandwich.getIngredients()[i];
            if(i != sandwich.getIngredients().length - 1 ){
                ingredients += ", ";
            }
        }
        if(sandwich.getAlsoKnownAs().length <= 0){
            aka.setText("N/A");
        }else {
            aka.setText(alsoKnownAs);
        }
        if(sandwich.getIngredients().length <= 0){
            ing.setText("N/A");
        }else {
            ing.setText(ingredients);
        }
        if(sandwich.getPlaceOfOrigin().equals("")){
            place.setText("N/A");
        }else {
            place.setText(sandwich.getPlaceOfOrigin());
        }
        if(sandwich.getDescription().equals("")){
            desc.setText("N/A");
        }else{
            desc.setText(sandwich.getDescription());
        }
    }
}
