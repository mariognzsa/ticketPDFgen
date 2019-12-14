package com.example.Parcial2.ExamenParcial2E1;


/**
 * {@link com.example.Parcial2.ExamenParcial2E1.postre} represents type of desert.
 * Each object has 3 properties: name, number, and image resource ID.
 */

public class postre {
    // Name of the desert
    private String mDessertName;

    // Number of desserts
    private int mDessertNumber;

    // Drawable resource ID
    private int mImageResourceId;

    /*
     * Create a new dessert object.
     *
     * @param vName is the name of the dessert
     * @param vNumber is the corresponding number of desserts
     * @param image is drawable reference ID that corresponds to the dessert
     * */
    public postre(String vName, int vNumber, int imageResourceId)

    {
        mDessertName = vName;
        mDessertNumber = vNumber;
        mImageResourceId = imageResourceId;
    }

    /**
     * Get the name of the dessert
     */
    public String getDessertName() {
        return mDessertName;
    }

    /**
     * Get the  number of desserts
     */
    public int getDessertNumber() {
        return mDessertNumber;
    }

    /**
     * Get the image resource ID
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }
}
