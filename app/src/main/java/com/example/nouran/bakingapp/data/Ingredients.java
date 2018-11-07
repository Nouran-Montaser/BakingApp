package com.example.nouran.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {
    private String measure;

    private String ingredient;

    private String quantity;

    public Ingredients(String measure, String ingredient, String quantity) {
        this.measure = measure;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public String getMeasure ()
    {
        return measure;
    }

    public void setMeasure (String measure)
    {
        this.measure = measure;
    }

    public String getIngredient ()
    {
        return ingredient;
    }

    public void setIngredient (String ingredient)
    {
        this.ingredient = ingredient;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [measure = "+measure+", ingredient = "+ingredient+", quantity = "+quantity+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
        dest.writeString(this.quantity);
    }

    protected Ingredients(Parcel in) {
        this.measure = in.readString();
        this.ingredient = in.readString();
        this.quantity = in.readString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}