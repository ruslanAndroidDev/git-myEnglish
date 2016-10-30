package com.example.pk.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.pk.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by pk on 10.10.2016.
 */
public class MyImageViewer extends AppCompatActivity {
    ImageView imgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_layout);
        imgView = (ImageView) findViewById(R.id.imageViewer);
        Intent intent = getIntent();
        Picasso.with(this).load(intent.getStringExtra("url")).into(imgView);
    }

}
