package ua.rDev.myEng.model;

/**
 * Created by pk on 10.09.2016.
 */
public class Word {
    private String translateWord;
    private String originalWord;
    private int status;

    public int getStatus() {
        return status;
    }

    public Word(String translateWord, String originalWord, int status) {
        this.translateWord = translateWord;
        this.originalWord = originalWord;
        this.status = status;

    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public String getOriginalWord() {
        return originalWord;
    }
}
