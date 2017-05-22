package com.example.pk.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("tag", "onNavigationItemSelected");
        try {
            if (id == R.id.my_vocabluary_item) {
                presenter.vocabluaryItemSelected();
            } else if (id == R.id.main_item) {
                presenter.mainFragmentItemSelected();
            } else if (id == R.id.item_repeat_word) {
                presenter.startChallengeFragmentItemSelected();
            } else if (id == R.id.item_iregular_verbs) {
                presenter.irgVerbsItemSelected();
            } else if (id == R.id.english_tense) {
                presenter.tenseFragmentItemSelected();
            }
        } catch (Exception e) {
            Log.d("tag", "error" + e.getMessage());
        }
        return true;
    }

    FragmentTransaction ft;
    MainFragment mainFragment;

    @Override
    public void showMainFragment() {
        if (mainFragment == null)
            mainFragment = new MainFragment();
        showFragment(mainFragment, false);
        toolbar.setTitle("My Englishtst");
    }

    private void showFragment(Fragment fragment, boolean addToBackStack) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.conteiner, fragment);
        if (addToBackStack) {
            ft.addToBackStack("");
        }
        ft.commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void openWebActivity(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        //  intent.putExtra("url", "http://easy-english.com.ua/irregular-verbs/");
        intent.putExtra("url", url);
        intent.putExtra("scale", 130);
        startActivity(intent);
    }
}
