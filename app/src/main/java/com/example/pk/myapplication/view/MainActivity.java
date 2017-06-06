package com.example.pk.myapplication.view;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.presenter.MainPresenter;

public class MainActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    @InjectPresenter
    MainPresenter presenter;

    NavigationView navigationView;

    Toolbar toolbar;
    DrawerLayout drawer;

    VocabularyFragment vocabularyFragment;
    TenseFragment tenseFragment;

    boolean SHOW_IC_FLAG;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.ic_date);
        if (SHOW_IC_FLAG) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
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
            presenter.onVerbsItemSelect();
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
            SHOW_IC_FLAG = true;
            showFragment(interestingFactFragment);
        } else {
            NoInternetConnectionFragment noInternetConnectionFragment = new NoInternetConnectionFragment();
            SHOW_IC_FLAG = false;
            showFragment(noInternetConnectionFragment);
        }
        toolbar.setTitle("My English");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNttworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNttworkInfo != null && activeNttworkInfo.isConnected();
    }

    private void showFragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.conteiner, fragment);
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();
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
        toolbar.setTitle("Мій Словник");
        SHOW_IC_FLAG = false;
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
        toolbar.setTitle("Часи");
        SHOW_IC_FLAG = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogFragment datePickerFragment = new DatePickerFragment(new Listener() {
            @Override
            void onDataSet(DatePicker view, int year, int month, int day) {
                int mont = month + 1;
                interestingFactFragment.updateData(year + "-" + mont + "-" + day);
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
        return true;
    }
}
