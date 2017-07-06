package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.adapter.CountryAdapter;
import ua.rDev.myEng.model.Country;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by pk on 08.10.2016.
 */
public class CountryActivity extends MvpAppCompatActivity implements ChildEventListener {
    ProgressDialog pd;
    RecyclerView recyclerView;
    CountryAdapter adapter;
    ArrayList<Country> data;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        String color = preferences.getString("color", "1");
        if (color.equals("1")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme2);
        }
        setContentView(R.layout.country_layout);
        toolbar = (Toolbar) findViewById(R.id.country_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.main_item));
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        data = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.country_recycler);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new CountryAdapter(data, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (!isNetworkAvailable()) {
            pd.hide();
            Toast.makeText(this, getResources().getString(R.string.no_connect), Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference = mDatabase.getReference("article/");
            reference.addChildEventListener(this);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNttworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNttworkInfo != null && activeNttworkInfo.isConnected();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        pd.hide();
        Country country = dataSnapshot.getValue(Country.class);
        adapter.arrayList.add(country);
        adapter.notifyItemInserted(0);
    }

    private void close() {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        finish();
    }

    @Override
    public void onBackPressed() {
        close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d("tag", "onCanceled" + databaseError.getMessage());
    }
}
