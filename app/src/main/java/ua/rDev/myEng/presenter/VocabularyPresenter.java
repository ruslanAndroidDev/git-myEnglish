package ua.rDev.myEng.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

import ua.rDev.myEng.data.MyDataBase;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.model.WordPack;
import ua.rDev.myEng.view.IVocabulary;

/**
 * Created by pk on 22.05.2017.
 */
@InjectViewState
public class VocabularyPresenter extends MvpPresenter<IVocabulary> {
    Context context;
    static ArrayList<Word> words;

    public VocabularyPresenter() {
    }

    public void loadData(Context context) {
        MyDataBaseHelper.loadWordwithDb(context, new MyDataBaseHelper.DataLoadListener() {

            @Override
            public void onLoad(ArrayList<Word> words) {
                getViewState().showData(words);
                VocabularyPresenter.words = words;
            }
        });
    }

    public void writeWord(String translate, String nativeWord) {
        MyDataBaseHelper.writetodb(context, translate, nativeWord);
        getViewState().insertWord(translate, nativeWord, MyDataBase.STATUS_UNKNOWN);
    }

    public void onWordPackClick(WordPack wordPack) {
        getViewState().showPanel(wordPack);
    }

    public void hidePanel() {
        getViewState().hidePanel();
    }

    public void addWordToBd(WordPack wordPack) {
        AsyncHelper helper = new AsyncHelper();
        helper.execute(wordPack);
    }

    class AsyncHelper extends AsyncTask<WordPack, Word, Void> {

        @Override
        protected Void doInBackground(WordPack... wordPacks) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = wordPacks[0].getSize() - 1; i >= 0; i--) {
                if (wordPacks[0].getWordsOriginal(i).isCheck()) {
                    if (isUnique(wordPacks[0].getWordsOriginal(i).getWord(), wordPacks[0])) {
                        MyDataBaseHelper.writetodb(context, wordPacks[0].getWordsTranslate(i), wordPacks[0].getWordsOriginal(i).getWord());
                        Word word = new Word(wordPacks[0].getWordsTranslate(i), wordPacks[0].getWordsOriginal(i).getWord(), MyDataBase.STATUS_UNKNOWN);
                        publishProgress(word);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Word... values) {
            getViewState().insertWord(values[0].getTranslateWord(), values[0].getOriginalWord(), values[0].getStatus());
        }
    }

    private boolean isUnique(String word, WordPack wordPack) {
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getOriginalWord())) {
                return false;
            }
        }
        return true;
    }
}
