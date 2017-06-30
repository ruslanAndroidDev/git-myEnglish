package ua.rDev.myEng.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.rDev.myEng.R;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.data.SkyEngService;
import ua.rDev.myEng.data.SkyEngWord;
import ua.rDev.myEng.databinding.CountryDetailLayoutBinding;
import ua.rDev.myEng.model.Country;

/**
 * Created by pk on 17.06.2017.
 */

public class CountryDetailActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {
    private static final String FIRST_LAUNCH_KEY = "country_first";
    CountryDetailLayoutBinding countryLayoutBinding;
    SkyEngService service;
    ImageView popIv;
    boolean PLAY_MUSIC = false;
    Country country;
    MyCardView popCard;
    Animation animation;


    long second;
    SharedPreferences sPref;
    boolean firstLaunch;
    AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        country = intent.getParcelableExtra("country");
        countryLayoutBinding = DataBindingUtil.setContentView(this, R.layout.country_detail_layout);
        popIv = (ImageView) findViewById(R.id.pop_iv);
        popCard = (MyCardView) findViewById(R.id.pop_card);
        sPref = getPreferences(MODE_PRIVATE);
        firstLaunch = sPref.getBoolean(FIRST_LAUNCH_KEY, true);
        if (firstLaunch) {
            showMyDialog();
        }
    }

    private void showMyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.country_msg)).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countryLayoutBinding.countryName.setText(country.getName());

        countryLayoutBinding.setClicker(this);
        OvershootInterpolator interpolator = new OvershootInterpolator();
        countryLayoutBinding.introTv.setInterpolator(interpolator);
        countryLayoutBinding.geoTv.setInterpolator(interpolator);
        countryLayoutBinding.historyTv.setInterpolator(interpolator);
        countryLayoutBinding.sightsTv.setInterpolator(interpolator);

        splitString(country.getIntro(), countryLayoutBinding.introTv);
        splitString(country.getGeo(), countryLayoutBinding.geoTv);
        splitString(country.getHistory(), countryLayoutBinding.historyTv);
        splitString(country.getSights(), countryLayoutBinding.sightsTv);

        Picasso.with(this).load(country.getPhotoUrl()).into(countryLayoutBinding.countryImageView);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_pop_show);
        animation.setAnimationListener(this);

        adView = countryLayoutBinding.countryBanner;
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
    }

    void splitString(final String s, final TextView tv) {
        String[] arr = s.split(" ");
        int currentPosition = 0;
        final SpannableString spannableString = new SpannableString(s);
        for (final String ss : arr) {
            final int finalCurrentPosition = currentPosition;
            ClickableSpan span1 = new ClickableSpan() {
                String word = ss;
                int myPosition = finalCurrentPosition;

                @Override
                public void onClick(View textView) {
                    loadTranslate(word);
                    BackgroundColorSpan[] spans = spannableString.getSpans(0, s.length(), BackgroundColorSpan.class);
                    for (int i = 0; i < spans.length; i++) {
                        spannableString.removeSpan(spans[i]);
                    }
                    spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#aed581")), myPosition, myPosition + ss.length() + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv.setText(spannableString);
                }

                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false); // remove underline
                }
            };
            try {
                spannableString.setSpan(span1, currentPosition, currentPosition + ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                currentPosition = currentPosition + ss.length() + 1;
            } catch (RuntimeException r) {
            }
        }

        tv.setText(spannableString);
        tv.setMovementMethod(createMovementMethod(this));
    }

    void loadTranslate(final String word) {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://dictionary.skyeng.ru/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(SkyEngService.class);
        }
        second = System.currentTimeMillis() + 6000;
        if (popCard.getVisibility() == View.INVISIBLE) {
            popCard.hide(false);
            popCard.startAnimation(animation);
            Timer timer = new Timer();
            timer.execute();
        }
        Call<ArrayList<SkyEngWord>> call = service.translateWord(word);
        call.enqueue(new Callback<ArrayList<SkyEngWord>>() {
            @Override
            public void onResponse(Call<ArrayList<SkyEngWord>> call, Response<ArrayList<SkyEngWord>> response) {
                try {
                    if (PLAY_MUSIC) {
                        MediaPlayer mp = new MediaPlayer();
                        mp.setDataSource("https:" + response.body().get(0).getMeanings().get(0).getSoundUrl());
                        try {
                            mp.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mp.start();
                    }
                    Picasso.with(getBaseContext())
                            .load("https:" + response.body().get(0).getMeanings().get(0).getPreviewUrl())
                            .into(popIv);
                    countryLayoutBinding.popOriginalTv.setText(word);
                    countryLayoutBinding.popTranslateTv.setText(response.body().get(0).getMeanings().get(0).getTranslation().getText());
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_name), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
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
            case R.id.pop_btn_music:
                if (PLAY_MUSIC) {
                    PLAY_MUSIC = false;
                    countryLayoutBinding.popBtnMusic.setImageResource(R.drawable.ic_volume_off_black_48dp);
                } else {
                    PLAY_MUSIC = true;
                    countryLayoutBinding.popBtnMusic.setImageResource(R.drawable.ic_volume_up_black_24dp);
                }
        }
    }

    private MovementMethod createMovementMethod(Context context) {
        final GestureDetector detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return true;
            }
        });
        return new ScrollingMovementMethod() {
            @Override
            public boolean onTouchEvent(@NonNull TextView widget, @NonNull Spannable buffer, @NonNull MotionEvent event) {
                // check if event is a single tab
                boolean isClickEvent = detector.onTouchEvent(event);

                // detect span that was clicked
                if (isClickEvent) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        // execute click only for first clickable span
                        // can be a for each loop to execute every one
                        link[0].onClick(widget);
                        return true;
                    }
                }

                // let scroll movement handle the touch
                return super.onTouchEvent(widget, buffer, event);
            }
        };
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        popCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    class Timer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (System.currentTimeMillis() < second) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_hide);
            animation.setFillAfter(true);
            popCard.hide(true);
            popCard.startAnimation(animation);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firstLaunch) {
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean(FIRST_LAUNCH_KEY, false);
            ed.commit();
        }
        adView.destroy();
    }
}
