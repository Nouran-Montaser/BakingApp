package com.example.nouran.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nouran.bakingapp.Adapter.SAdapter;
import com.example.nouran.bakingapp.data.Bakings;
import com.example.nouran.bakingapp.data.Steps;

import java.util.ArrayList;

public class MasterFragment extends Fragment {

    private LinearLayoutManager layoutManager;
    private ArrayList<Steps> details;
    private SAdapter myAdapter;
    private RecyclerView recyclerView;
    private TextView textView;
    Bakings bakings;
    private boolean mTwoPane;
    private OnStepClickListener mCallback;


    public MasterFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+"must implement OnStepClickListener ");
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_master, container, false);

        textView = view.findViewById(R.id.detail_text_view);
        recyclerView = view.findViewById(R.id.recycleerr_view);
//        bakings = BakingWrapper.getInstance().getBakings();
        bakings=getArguments().getParcelable("lol");

        myAdapter = new SAdapter(getContext(),bakings.getSteps(),mCallback);
        StringBuilder ingredients = new StringBuilder("");
        for (int i = 0; i < bakings.getIngredients().size(); i++) {
            ingredients.append(bakings.getIngredients().get(i).getQuantity());
            ingredients.append("  ");
            ingredients.append(bakings.getIngredients().get(i).getMeasure());
            ingredients.append("  ");
            ingredients.append(bakings.getIngredients().get(i).getIngredient());
            ingredients.append("\n");


        }
        textView.setText(ingredients.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(myAdapter);


        return view;
    }
}


