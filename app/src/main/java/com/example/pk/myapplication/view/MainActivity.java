package com.example.pk.myapplication.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pk.myapplication.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction ft;


    VocabularyFragment vocabularyFragment;
    TenseFragment tenseFragment;
    StartChallengeFragment repeatWordFragment;
    MainFragment mainFragment;

    public static int flag;
    // 1 - mainFragment is show in FragmentTransaction
    // 0 - mainFragment isn't show in FragmentTransaction


    NavigationView navigationView;
    Toolbar toolbar;
    //settings for acsses to vkSdk
    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this, scope);
            Toast.makeText(this, "Login...", Toast.LENGTH_SHORT).show();
        } else {
            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            mainFragment = new MainFragment();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            if (!isNetworkAvailable()) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.conteiner, new NoInternetConnectionFragment());
                ft.commit();
                flag = 1;
            } else {
                ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.conteiner, mainFragment);
                ft.commit();
                flag = 1;
            }

        }
//        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        try {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.conteiner, mainFragment);
                ft.commit();
                System.gc();
        } catch (NullPointerException e) {
            if (flag == 0) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.conteiner, mainFragment);
                ft.commit();
            } else {
                finish();
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        try {

            if (id == R.id.my_vocabluary_item) {
                Log.d("tag", "klick to item");
                toolbar.setTitle("Мій словник");
                if (vocabularyFragment == null) {
                    vocabularyFragment = new VocabularyFragment();
                }
                flag = 0;
                showFragment(vocabularyFragment, true);
            } else if (id == R.id.main_item) {
                flag = 1;
                showFragment(mainFragment, false);
                toolbar.setTitle("My English");
            } else if (id == R.id.item_repeat_word) {
                if (repeatWordFragment == null) {
                    repeatWordFragment = new StartChallengeFragment();
                }
                flag = 0;
                showFragment(repeatWordFragment, false);
                toolbar.setTitle("English");
            } else if (id == R.id.item_iregular_verbs) {
                Intent intent = new Intent(this,WebActivity.class);
                intent.putExtra("url","http://easy-english.com.ua/irregular-verbs/");
                intent.putExtra("scale",130);
                startActivity(intent);
            } else if (id == R.id.english_tense) {
                if (tenseFragment == null) {
                    tenseFragment = new TenseFragment();
                }
                flag = 0;
                showFragment(tenseFragment, false);
                toolbar.setTitle("Часи");
            }
        } catch (Exception e) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFragment(Fragment fragment, boolean addtoBackStack) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.conteiner, fragment);
        if (addtoBackStack) {
            ft.addToBackStack("");
        }
        ft.commit();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNttworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNttworkInfo != null && activeNttworkInfo.isConnected();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
