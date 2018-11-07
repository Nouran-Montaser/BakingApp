package com.example.nouran.bakingapp;

import com.example.nouran.bakingapp.data.Steps;

public class StepWrapper {
    private static final StepWrapper ourInstance = new StepWrapper();
    Steps bakings;

    public Steps getSteps() {
        return bakings;
    }

    public void setSteps(Steps bakings) {
        this.bakings = bakings;
    }

    public static StepWrapper getInstance() {
        return ourInstance;
    }

    private StepWrapper() {
    }

}