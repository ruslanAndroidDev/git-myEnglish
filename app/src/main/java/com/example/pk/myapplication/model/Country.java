package com.example.pk.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pk on 17.06.2017.
 */

public class Country implements Parcelable {
    String name;
    String sights;
    String introHtml;

    public String getIntroHtml() {
        return introHtml;
    }

    protected Country(Parcel in) {
        name = in.readString();
        sights = in.readString();
        geo = in.readString();
        history = in.readString();
        photoUrl = in.readString();
        article = in.readString();
        intro = in.readString();

    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getSights() {
        return sights;
    }

    String geo;
    String history;
    String photoUrl;
    String article;
    String intro;

    public String getIntro() {
        return intro;
    }

    public Country() {
    }

    public String getHistory() {
        return history;
    }

    public String getGeo() {
        return geo;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getArticle() {
        return article;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(sights);
        parcel.writeString(geo);
        parcel.writeString(history);
        parcel.writeString(photoUrl);
        parcel.writeString(article);
        parcel.writeString(intro);
    }
}
