package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.adapter.MainAdapter;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 509;


    Toolbar toolbar;
    SharedPreferences sPref;
    public final String FIRST_LAUNCH_KEY = "firstLaunch";

    RecyclerView mainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mainRecyclerView.setAdapter(new MainAdapter(this, new MainAdapter.Listen() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(int position) {
                Log.d("tag", "rvClick + pos:" + position);
                Intent intent = null;
                if (position == 0) {
                    intent = new Intent(getApplicationContext(), CountryActivity.class);
                } else if (position == 1) {
                    intent = new Intent(getApplicationContext(), VocabularyActivity.class);
                } else if (position == 2) {
                    intent = new Intent(getApplicationContext(), TenseActivity.class);
                } else if (position == 3) {
                    intent = new Intent(getApplicationContext(), WebActivity.class);
                    intent.putExtra("url", getResources().getString(R.string.verbs_url));
                    intent.putExtra("scale", 130);
                } else if (position == 4) {
                    intent = new Intent(getApplicationContext(), ChallengeActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), SettingsActivity.class);
                }
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter, R.anim.anim_leave);
                startActivity(intent, options.toBundle());
                finish();
            }
        }));

        toolbar = (Toolbar)

                findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        sPref =

                getPreferences(MODE_PRIVATE);

        boolean firstLaunch = sPref.getBoolean(FIRST_LAUNCH_KEY, true);
        if (firstLaunch == true)

        {
            loadTutorial();
        }
        MobileAds.initialize(this,

                getResources().

                        getString(R.string.app_id));
    }

    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(FIRST_LAUNCH_KEY, false);
        ed.commit();

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(context.getString(R.string.welcome), context.getString(R.string.welcome_subtitle),
                R.color.colorAccent, R.drawable.welcome);

        TutorialItem tutorialItem2 = new TutorialItem(context.getString(R.string.study_words_together), context.getString(R.string.study_words_together_subtitle),
                R.color.splash_2, R.drawable.words);

        TutorialItem tutorialItem3 = new TutorialItem(context.getString(R.string.hint), context.getString(R.string.hint_subtitle),
                R.color.splash_3, R.drawable.hint);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);

        return tutorialItems;
    }

}
