package ua.rDev.myEng.model;

/**
 * Created by pk on 06.07.2017.
 */

public class Country {
    String intro;
    String photoUrl;
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Country() {
    }

    public Country(String intro, String photoUrl) {
        this.intro = intro;
        this.photoUrl = photoUrl;
    }

    public String getIntro() {

        return intro;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
