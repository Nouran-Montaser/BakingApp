package com.example.nouran.bakingapp;

import com.example.nouran.bakingapp.data.Bakings;

public class BakingWrapper {
    private static final BakingWrapper ourInstance = new BakingWrapper();
    Bakings bakings;

    public Bakings getBakings() {
        return bakings;
    }

    public void setBakings(Bakings bakings) {
        this.bakings = bakings;
    }

  public   static BakingWrapper getInstance() {
        return ourInstance;
    }

    private BakingWrapper() {
    }
}
