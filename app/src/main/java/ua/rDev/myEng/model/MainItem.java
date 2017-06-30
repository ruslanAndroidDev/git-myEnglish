package ua.rDev.myEng.model;

/**
 * Created by pk on 26.06.2017.
 */

public class MainItem {
    String title;
    int res;

    public MainItem(String title, int res) {
        this.title = title;
        this.res = res;
    }

    public String getTitle() {

        return title;
    }

    public int getRes() {
        return res;
    }
}
