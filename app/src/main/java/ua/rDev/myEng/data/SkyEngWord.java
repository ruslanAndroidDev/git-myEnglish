package ua.rDev.myEng.data;

import java.util.ArrayList;

/**
 * Created by pk on 19.06.2017.
 */

public class SkyEngWord {
    ArrayList<Meaning> meanings;

    public ArrayList<Meaning> getMeanings() {
        return meanings;
    }

   public class Meaning {
        public String getSoundUrl() {
            return soundUrl;
        }

        public String getPreviewUrl() {
            return previewUrl;
        }

        public String getTranscription() {
            return transcription;
        }

        public String getPartOfSpeechCode() {
            return partOfSpeechCode;
        }

        public Translation getTranslation() {
            return translation;
        }

        String soundUrl;
        String previewUrl;
        String transcription;
        String partOfSpeechCode;
        Translation translation;
    }
    public class Translation{
        String text;

        public String getText() {
            return text;
        }
    }
}
