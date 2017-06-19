package com.example.pk.myapplication.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.data.SkyEngService;
import com.example.pk.myapplication.data.SkyEngWord;
import com.example.pk.myapplication.databinding.CountryLayoutBinding;
import com.example.pk.myapplication.model.Country;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pk on 17.06.2017.
 */

public class CountryActivity extends AppCompatActivity implements View.OnClickListener {
    CountryLayoutBinding countryLayoutBinding;
    SkyEngService service;
    ImageView popIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Country country = intent.getParcelableExtra("country");
        countryLayoutBinding = DataBindingUtil.setContentView(this, R.layout.country_layout);
        countryLayoutBinding.setCountry(country);
        popIv = (ImageView) findViewById(R.id.pop_iv);
        countryLayoutBinding.setClicker(this);
        countryLayoutBinding.introTv.setInterpolator(new OvershootInterpolator());
        countryLayoutBinding.geoTv.setInterpolator(new OvershootInterpolator());
        countryLayoutBinding.historyTv.setInterpolator(new OvershootInterpolator());
        countryLayoutBinding.sightsTv.setInterpolator(new OvershootInterpolator());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dictionary.skyeng.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SkyEngService.class);

        splitString(country.getIntro());

        Picasso.with(this).load(country.getPhotoUrl()).into(countryLayoutBinding.countryImageView);

    }

    void splitString(String s) {
        Log.d("tag", s.length() + "length");
        String[] arr = s.split(" ");
        SpannableString spannableString = new SpannableString(s);
        int currentPosition = 0;
        for (final String ss : arr) {
            ClickableSpan span1 = new ClickableSpan() {
                String word = ss;

                @Override
                public void onClick(View textView) {
                    Log.d("tag", word);
                    loadTranslate(word);

                }
            };
            Log.d("tag", "currentPosition " + currentPosition + ": end " + (ss.length() - 1 + currentPosition));
            try {
                spannableString.setSpan(span1, currentPosition, currentPosition + ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                currentPosition = currentPosition + ss.length() + 1;
            } catch (RuntimeException r) {

            }
        }

        countryLayoutBinding.introTv.setText(spannableString);
        countryLayoutBinding.introTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    void loadTranslate(final String word) {
        Call<ArrayList<SkyEngWord>> call = service.translateWord(word);
        call.enqueue(new Callback<ArrayList<SkyEngWord>>() {
            @Override
            public void onResponse(Call<ArrayList<SkyEngWord>> call, Response<ArrayList<SkyEngWord>> response) {
                try {
                    countryLayoutBinding.popCard.setVisibility(View.VISIBLE);
                    Picasso.with(getBaseContext())
                            .load("https:" + response.body().get(0).getMeanings().get(0).getPreviewUrl())
                            .into(popIv);
                    countryLayoutBinding.popOriginalTv.setText(word);
                    countryLayoutBinding.popTranslateTv.setText(response.body().get(0).getMeanings().get(0).getTranslation().getText());
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_name), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SkyEngWord>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_geo:
                countryLayoutBinding.geoTv.toggle();
                if (countryLayoutBinding.geoTv.isExpanded()) {
                    countryLayoutBinding.btnGeo.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    countryLayoutBinding.btnGeo.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                break;
            case R.id.btn_history:
                countryLayoutBinding.historyTv.toggle();
                if (countryLayoutBinding.historyTv.isExpanded()) {
                    countryLayoutBinding.btnHistory.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    countryLayoutBinding.btnHistory.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                break;
            case R.id.btn_sights:
                countryLayoutBinding.sightsTv.toggle();
                if (countryLayoutBinding.sightsTv.isExpanded()) {
                    countryLayoutBinding.btnSights.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    countryLayoutBinding.btnSights.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                break;
            case R.id.btn_intro:
                countryLayoutBinding.introTv.toggle();
                if (countryLayoutBinding.introTv.isExpanded()) {
                    countryLayoutBinding.btnIntro.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    countryLayoutBinding.btnIntro.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                break;
            case R.id.pop_btn:
                MyDataBaseHelper.writetodb(getApplicationContext(), countryLayoutBinding.popTranslateTv.getText().toString(), countryLayoutBinding.popOriginalTv.getText().toString());
                Toast.makeText(getApplicationContext(), countryLayoutBinding.popOriginalTv.getText().toString() + " " + getResources().getString(R.string.added_word), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
