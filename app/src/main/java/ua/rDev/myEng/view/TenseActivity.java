package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.databinding.TenseFragmentBinding;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by pk on 15.09.2016.
 */
public class TenseActivity extends AppCompatActivity implements View.OnClickListener {
    TenseFragmentBinding binding;
    String url = "";
    Toolbar tense_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        String color = preferences.getString("color", "1");
        if (color.equals("1")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme2);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.tense_fragment);
        if (Utill.getThemeAccentColor(this) == ContextCompat.getColor(this, R.color.colorAccent2)) {
            binding.card1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_blue1));
            binding.card2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_blue2));
            binding.card3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_blue3));
            binding.card4.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_blue4));
        } else {
            binding.card1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_brown1));
            binding.card2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_brown2));
            binding.card3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_brown3));
            binding.card4.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_brown4));
        }
        binding.setClicker(this);
        tense_toolbar = (Toolbar) findViewById(R.id.tense_toolbar);
        setSupportActionBar(tense_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.english_tense));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
        }
        return super.onOptionsItemSelected(item);
    }

    private void close() {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pastSimple:
                url = getResources().getString(R.string.past_simple_url);
                break;
            case R.id.presentSimple:
                url = getResources().getString(R.string.present_simple_url);
                break;
            case R.id.futureSimple:
                url = getResources().getString(R.string.future_simple_url);
                break;
            case R.id.futureinthePastSimple:
                url = getResources().getString(R.string.future_simple_in_the_past_url);
                break;
            case R.id.pastContinuous:
                url = getResources().getString(R.string.past_continuous_url);
                break;
            case R.id.presentContinuous:
                url = getResources().getString(R.string.present_continuous_url);
                break;
            case R.id.futureContinuous:
                url = getResources().getString(R.string.future_continuous_url);
                break;
            case R.id.futureinthePastContinuous:
                url = getResources().getString(R.string.future_continuous_in_the_past_url);
                break;
            case R.id.pastPerfect:
                url = getResources().getString(R.string.past_perfect_url);
                break;
            case R.id.presentPerfect:
                url = getResources().getString(R.string.present_perfect_url);
                break;
            case R.id.futurePerfect:
                url = getResources().getString(R.string.future_perfect_url);
                break;
            case R.id.futureinthePastPerfect:
                url = getResources().getString(R.string.future_perfect_in_the_past_url);
                break;
            case R.id.pastPerfectContinuous:
                url = getResources().getString(R.string.past_perfect_continuous_url);
                break;
            case R.id.presentPerfectContinuous:
                url = getResources().getString(R.string.present_perfect_continuous_url);
                break;
            case R.id.futurePerfectContinuous:
                url = getResources().getString(R.string.future_perfect_continuous_url);
                break;
            case R.id.futureinthePastPerfectContinuous:
                url = getResources().getString(R.string.future_perfect_continuous_in_the_past_url);
                break;
        }
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        close();
    }
}
