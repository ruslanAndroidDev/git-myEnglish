package ua.rDev.myEng.model;

/**
 * Created by pk on 23.09.2016.
 */
public  class Variant {
    private String falseVariant1;
    private String falseVariant2;
    private String falseVariant3;
    private String falseVariant4;

    public Variant(String falseVariant1, String falseVariant2, String falseVariant3, String falseVariant4) {
        this.falseVariant1 = falseVariant1;
        this.falseVariant2 = falseVariant2;
        this.falseVariant3 = falseVariant3;
        this.falseVariant4 = falseVariant4;
    }


    public String getFalseVariant1() {
        return falseVariant1;
    }

    public String getFalseVariant2() {
        return falseVariant2;
    }

    public String getFalseVariant3() {
        return falseVariant3;
    }

    public String getFalseVariant4() {
        return falseVariant4;
    }
}