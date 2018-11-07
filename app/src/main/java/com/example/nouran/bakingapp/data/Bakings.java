package com.example.nouran.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bakings implements Parcelable {

    private String id, name;
    private int foodImage;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public Bakings(String id, String name, int foodImage, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps) {

        this.id = id;
        this.name = name;
        this.foodImage = foodImage;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.foodImage);
        dest.writeList(this.ingredients);
        dest.writeTypedList(this.steps);
    }

    protected Bakings(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.foodImage = in.readInt();
        this.ingredients = new ArrayList<Ingredients>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.steps = in.createTypedArrayList(Steps.CREATOR);
    }

    public static final Creator<Bakings> CREATOR = new Creator<Bakings>() {
        @Override
        public Bakings createFromParcel(Parcel source) {
            return new Bakings(source);
        }

        @Override
        public Bakings[] newArray(int size) {
            return new Bakings[size];
        }
    };
}
