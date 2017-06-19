package com.example.pk.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.Utill;
import com.example.pk.myapplication.presenter.MainPresenter;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class MainActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    private static final int REQUEST_CODE = 509;
    @InjectPresenter
    MainPresenter presenter;

    NavigationView navigationView;

    Toolbar toolbar;
    DrawerLayout drawer;

    VocabularyFragment vocabularyFragment;
    TenseFragment tenseFragment;

    Fragment openFragment;
    SharedPreferences sPref;
    public final String FIRST_LAUNCH_KEY = "firstLaunch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView_nav);
        imageView.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.header_photo, 220, 160));

        sPref = getPreferences(MODE_PRIVATE);
        boolean firstLaunch = sPref.getBoolean(FIRST_LAUNCH_KEY, true);
        if (firstLaunch == true) {
            loadTutorial();
        }
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

    @Override
    public void onBackPressed() {
        if (openFragment instanceof VocabularyFragment) {
            if (vocabularyFragment.isPanelShow()) {
                vocabularyFragment.hidePanel();
            } else {
                super.onBackPressed();
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my_vocabluary_item) {
            presenter.onVocabluaryItemSelect();
        } else if (id == R.id.main_item) {
            presenter.onMainFragmentSelect();
        } else if (id == R.id.item_repeat_word) {
            presenter.onStartChallengeFragmentSelect();
        } else if (id == R.id.item_iregular_verbs) {
            presenter.onVerbsItemSelect(getResources().getString(R.string.verbs_url));
        } else if (id == R.id.english_tense) {
            presenter.onTenseFragmentSelect();
        }
        return true;
    }

    FragmentTransaction ft;
    InterestingFactFragment interestingFactFragment;

    @Override
    public void showMainFragment() {
        if (isNetworkAvailable()) {
            if (interestingFactFragment == null)
                interestingFactFragment = new InterestingFactFragment();
            showFragment(interestingFactFragment);
        } else {
            NoInternetConnectionFragment noInternetConnectionFragment = new NoInternetConnectionFragment();
            showFragment(noInternetConnectionFragment);
        }
        toolbar.setTitle("My English");

        vocabularyFragment = new VocabularyFragment();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNttworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNttworkInfo != null && activeNttworkInfo.isConnected();
    }

    private void showFragment(Fragment fragment) {
        drawer.closeDrawer(GravityCompat.START);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.conteiner, fragment);
        openFragment = fragment;
        ft.commit();
    }

    @Override
    public void openWebActivity(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("scale", 130);
        startActivity(intent);
    }

    @Override
    public void showVocabluaryFragment() {
        if (vocabularyFragment == null)
            vocabularyFragment = new VocabularyFragment();
        showFragment(vocabularyFragment);
        toolbar.setTitle(getResources().getString(R.string.my_dictionary));
    }

    @Override
    public void showStartChallangeFragment() {
        Intent intent = new Intent(this, ChallengeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTenseFragment() {
        if (tenseFragment == null)
            tenseFragment = new TenseFragment();
        showFragment(tenseFragment);
        toolbar.setTitle(getResources().getString(R.string.tenses));
    }
}
