package com.example.nouran.bakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.bakingapp.Adapter.MyAdapter;
import com.example.nouran.bakingapp.data.Bakings;
import com.example.nouran.bakingapp.data.Ingredients;
import com.example.nouran.bakingapp.R;
import com.example.nouran.bakingapp.data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Bakings> details;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredients> ingredients;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializer();

        settingUpTheRecyclerView();

        requestData();
    }

    private void settingUpTheRecyclerView() {

    }

    private void requestData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray all = new JSONArray(response);
                            String name , id;
                            String player = null, description = null;
                            String  measure, ingred;
                            StringBuilder stringBuilder = new StringBuilder("");
                            String quantity;
                            String shortDescription = null,descriptionId = null;

                            for(int i=0;i<all.length();i++)
                            {
                                steps = new ArrayList<>();
                                ingredients = new ArrayList<>();

                                JSONObject member=all.getJSONObject(i);
                                name=member.getString("name");
                                id=member.getString("id");

                                JSONArray m = new JSONArray(member.getString("ingredients"));
                                for (int j = 0; j < m.length(); j++) {

                                    JSONObject ingreMember = m.getJSONObject(j);
                                    quantity = ingreMember.getString("quantity");
                                    measure = ingreMember.getString("measure");
                                    ingred = ingreMember.getString("ingredient");

                                    ingredients.add(new Ingredients(measure,ingred,quantity));
                                }

                                JSONArray s = new JSONArray(member.getString("steps"));
                                for (int k = 0; k < s.length(); k++) {
                                    JSONObject stepMember = s.getJSONObject(k);
                                    descriptionId = stepMember.getString("id");
                                    shortDescription = stepMember.getString("shortDescription");
                                    description = stepMember.getString("description");
                                    player = stepMember.getString("videoURL");
                                    steps.add(new Steps(descriptionId,shortDescription,description,player,R.drawable.imageunavailable));
                                }

                                details.add(new Bakings(id,name,R.drawable.imageunavailable,ingredients,steps));
                            }

                            myAdapter = new MyAdapter(MainActivity.this,details);
                            //myAdapter.setClickListner(this);
                            recyclerView.setAdapter(myAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error in connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void initializer() {
         GridLayoutManager gridLayoutManager;
        recyclerView = findViewById(R.id.recycler_view);
        details = new ArrayList<>();
        steps = new ArrayList<>();
        ingredients = new ArrayList<>();

//        if(MainActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            if (getResources().getBoolean(R.bool.isTablet))
        {
            gridLayoutManager = new GridLayoutManager(this, 3);
        } else
            gridLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


    }
}
