package com.example.nouran.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.example.nouran.bakingapp.R;
import com.example.nouran.bakingapp.StepDetailFragment;
import com.example.nouran.bakingapp.data.Steps;

public class StepActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_activity);

        if (savedInstanceState == null)
            fragmentCode();
    }


    private void fragmentCode() {
        Steps i = getIntent().getParcelableExtra("lol");
        Bundle bundle=new Bundle();
        bundle.putParcelable("lol",i);
        StepDetailFragment container = new StepDetailFragment();
        container.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container,container).commit();

    }

}