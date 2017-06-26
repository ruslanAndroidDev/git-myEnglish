package ua.rDev.myEng.model;

/**
 * Created by pk on 16.06.2017.
 */

public class ClicableWord {
    private String word;
    private boolean isCheck;

    public ClicableWord(String word) {
        this.word = word;
        this.isCheck = false;
    }

    public String getWord() {
        return word;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }
}
