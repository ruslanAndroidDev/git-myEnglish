package ua.rDev.myEng.view;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.adapter.RegionRecyclerViewAdapter;
import ua.rDev.myEng.model.Region;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by pk on 08.07.2017.
 */

public class RegionActivity extends AppCompatActivity implements ChildEventListener {
    Toolbar toolbar;
    RecyclerView regionRv;
    ArrayList<Region> data;
    ProgressDialog pd;
    RegionRecyclerViewAdapter adapter;

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
        setContentView(R.layout.region_layout);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        toolbar = (Toolbar) findViewById(R.id.region_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Region");
        regionRv = (RecyclerView) findViewById(R.id.region_recycler);
        adapter = new RegionRecyclerViewAdapter(data, this);
        regionRv.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        data = new ArrayList<>();
        regionRv.setAdapter(adapter);
        if (!Utill.isNetworkAvailable(this)) {
            pd.hide();
            Toast.makeText(this, getResources().getString(R.string.no_connect), Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference = mDatabase.getReference("regions/");
            reference.addChildEventListener(this);

        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        pd.hide();
        Region region = dataSnapshot.getValue(Region.class);
        adapter.addItem(region);
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

    }
}
