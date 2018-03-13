package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Bind all needed views
    @BindView(R.id.image_iv) ImageView photoIv;
    @BindView(R.id.also_know_as_tv1) TextView alsoKnownAsTv1;
    @BindView(R.id.also_know_as_tv2) TextView alsoKnownAsTv2;
    @BindView(R.id.place_of_origin_tv2) TextView placeOfOriginTv2;
    @BindView(R.id.ingredients_tv2) TextView ingredientsTv2;
    @BindView(R.id.description_tv2) TextView descriptionTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //Some error handling
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        //GET the current position from the MainActivity
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        //GET the sandwich object
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        //Some error handling
        if (sandwich == null) {
            closeOnError();
            return;
        }

        //Populate all the TextViews
        populateUI(sandwich);

        //SET the ImageView to the right photo using Picasso
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(photoIv);

        //Set the text in the action bar to the name of current sandwich
        setTitle(sandwich.getMainName());

    }

    private void populateUI(Sandwich sandwich){

        //POPULATE other names (also known as)
        //If the list is empty, then set visibility of the views to GONE
        //Else set the TextView to other names (divide them by a comma)
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if(alsoKnownAs.isEmpty()){
            alsoKnownAsTv1.setVisibility(View.GONE);
            alsoKnownAsTv2.setVisibility(View.GONE);
        } else {
            StringBuilder alsoKnowsAsStringBuilder = new StringBuilder();
            for(int i = 0; i < alsoKnownAs.size(); i++){
                if(i == (alsoKnownAs.size()-1)){
                    alsoKnowsAsStringBuilder.append(alsoKnownAs.get(i));
                    break;
                }
                alsoKnowsAsStringBuilder.append(alsoKnownAs.get(i) + ", ");
            }
            alsoKnownAsTv2.setText(alsoKnowsAsStringBuilder);
        }

        //POPULATE the place of origin
        //If the String is empty, then set the TextView to unknown
        //Else set the place of origin
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if(placeOfOrigin==null||placeOfOrigin.equals("")){
            placeOfOriginTv2.setText(getResources().getString(R.string.unknown));
        } else {
            placeOfOriginTv2.setText(placeOfOrigin);
        }

        //POPULATE the ingredients list (as a bulleted list)
        List<String> ingredients = sandwich.getIngredients();
        StringBuilder ingredientsStringBuilder = new StringBuilder();
        for(int i = 0; i < ingredients.size(); i++){
            if(i==0){
                ingredientsStringBuilder.append("\u2022 ");
            } else {
                ingredientsStringBuilder.append("\n\u2022 ");
            }
            ingredientsStringBuilder.append(ingredients.get(i));
        }
        ingredientsTv2.setText(ingredientsStringBuilder);

        //POPULATE description
        descriptionTv2.setText(sandwich.getDescription());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
