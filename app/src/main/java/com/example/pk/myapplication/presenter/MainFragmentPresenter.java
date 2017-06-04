package com.example.pk.myapplication.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pk.myapplication.model.Model;
import com.example.pk.myapplication.view.IMainFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pk on 01.06.2017.
 */
@InjectViewState
public class MainFragmentPresenter extends MvpPresenter<IMainFragment> {
    DatabaseReference reference;

    public MainFragmentPresenter() {
//        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
//        reference = mDatabase.getReference("pack/");

//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Model model = dataSnapshot.getValue(Model.class);
//                Log.d("tag", model.getIcon());
//                getViewState().fillArray(model.getIcon());
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
