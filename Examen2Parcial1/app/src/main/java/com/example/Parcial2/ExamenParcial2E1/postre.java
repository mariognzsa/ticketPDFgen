package com.example.Parcial2.ExamenParcial2E1;


/**
 * {@link com.example.Parcial2.ExamenParcial2E1.postre} represents type of desert.
 * Each object has 3 properties: name, number, and image resource ID.
 */

public class postre {
    // Name of the desert
    private String mDessertName;

    //Price
    private int mDessertAmount;

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
    public postre(String vName, int vAmount, int vNumber, int imageResourceId)

    {
        mDessertName = vName;
        mDessertAmount = vAmount;
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
     * Get the  price of desserts
     */
    public int getDessertAmount() {
        return mDessertAmount;
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
