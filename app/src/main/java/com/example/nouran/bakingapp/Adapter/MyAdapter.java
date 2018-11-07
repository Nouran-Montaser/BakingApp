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

import com.example.nouran.bakingapp.Activities.DetailActivity;
import com.example.nouran.bakingapp.BakingWrapper;
import com.example.nouran.bakingapp.data.Bakings;
import com.example.nouran.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.example.nouran.bakingapp.data.Ingredients;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myHolder> {

    private Context context;
    private ArrayList<Bakings> details;

    public MyAdapter(Context context, ArrayList<Bakings> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new myHolder(view);
    }


    @Override
    public void onBindViewHolder(myHolder holder, final int position) {

        holder.textView.setText(details.get(position).getName());
        Picasso.with(context).load(details.get(position).getFoodImage()).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                BakingWrapper.getInstance().setBakings(details.get(position));
                intent.putExtra("ClickedItem", new Bakings(details.get(position).getId(), details.get(position).getName(),
                        details.get(position).getFoodImage(),details.get(position).getIngredients(),details.get(position).getSteps()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class myHolder extends RecyclerView.ViewHolder {

        TextView detailtxt;
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public myHolder(final View itemView) {
            super(itemView);
            detailtxt = itemView.findViewById(R.id.detail_text_view);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.txt);
            linearLayout = itemView.findViewById(R.id.main_layout_container);
        }
    }
}
