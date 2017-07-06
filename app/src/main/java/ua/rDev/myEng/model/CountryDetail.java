package ua.rDev.myEng.model;

/**
 * Created by pk on 17.06.2017.
 */

public class CountryDetail {
    String name;
    String sights;

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

    public CountryDetail() {
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
}
