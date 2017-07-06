package ua.rDev.myEng.model;

/**
 * Created by pk on 06.07.2017.
 */

public class Country {
    String introHtml;
    String name;
    String photoUrl;
    String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Country() {
    }

    public String getIntroHtml() {

        return introHtml;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
