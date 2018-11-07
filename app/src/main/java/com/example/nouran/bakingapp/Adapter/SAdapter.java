package com.example.nouran.bakingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nouran.bakingapp.Activities.StepActivity;
import com.example.nouran.bakingapp.BakingWrapper;
import com.example.nouran.bakingapp.OnStepClickListener;
import com.example.nouran.bakingapp.R;
import com.example.nouran.bakingapp.StepWrapper;
import com.example.nouran.bakingapp.data.Steps;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SAdapter extends RecyclerView.Adapter<SAdapter.SHolder> {

    private Context context;
//    private ArrayList<Ingredients> detail;
    private ArrayList<Steps> step;
    OnStepClickListener onStepClickListener;

    public SAdapter(Context context , ArrayList<Steps> step, OnStepClickListener onStepClickListener) {
        this.context = context;
//        this.detail = detail;
        this.step = step;
        this.onStepClickListener=onStepClickListener;
    }

    @Override
    public SHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.steps,parent,false);
        return new SHolder(view);
    }


    @Override
    public void onBindViewHolder(SHolder holder,final int position){


        holder.textView.setText(step.get(position).getShortDescription());
        Picasso.with(context).load(step.get(position).getThumbnailURL()).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepWrapper.getInstance().setSteps(step.get(position));
                onStepClickListener.onStepClickListener(step.get(position));
//                onStepClickListener.onStepClickListener();
            }
        });
    }

    @Override
    public int getItemCount() {
        return step.size();
    }

    public static class SHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public SHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.step_image);
            textView = itemView.findViewById(R.id.step_txt);
            linearLayout = itemView.findViewById(R.id.main_layout_container);
        }
    }
}
