package com.example.nouran.bakingapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nouran.bakingapp.BakingWrapper;
import com.example.nouran.bakingapp.MasterFragment;
import com.example.nouran.bakingapp.OnStepClickListener;
import com.example.nouran.bakingapp.StepDetailFragment;
import com.example.nouran.bakingapp.R;
import com.example.nouran.bakingapp.data.Steps;

public class DetailActivity extends AppCompatActivity implements OnStepClickListener {

    private boolean mTwoPane = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);


        if (findViewById(R.id.two_pane_liner_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Getting rid of the "Next" button that appears on phones for launching a separate activity


        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }

        initializer();
        if (savedInstanceState == null) {
//            // In two-pane mode, add initial BodyPartFragments to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
//
//            // Creating a new head fragment
            MasterFragment masterFragment = new MasterFragment();
////                masterFragment.setImageIds(AndroidImageAssets.getHeads());
//            // Add the fragment to its container using a transaction
//
//            Bundle bundle=new Bundle();
//            bundle.putParcelable("lol",getIntent().getParcelableExtra("ClickedItem"));
//            masterFragment.setArguments(bundle);
//
//
            fragmentManager.beginTransaction()
                    .add(R.id.master_container, masterFragment)
                    .commit();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initializer() {

    }

    @Override
    public void onStepClickListener(Steps steps) {
        if (mTwoPane) {
            StepDetailFragment container = new StepDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putParcelable("lol", steps);
            fragmentManager.beginTransaction().replace(R.id.container, container).commit();
            container.setArguments(bundle);


        } else {
            final Intent intent = new Intent(DetailActivity.this, StepActivity.class);
            intent.putExtra("lol", steps);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_widget: {
                SharedPreferences.Editor sharedPrefsEditor;
                final String MY_PREFS_NAME = "MyPrefsFile";
                 StringBuilder ingredients;

                sharedPrefsEditor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                sharedPrefsEditor.putString("name", BakingWrapper.getInstance().getBakings().getName());
                ingredients = new StringBuilder("");
                for (int i = 0; i < BakingWrapper.getInstance().getBakings().getIngredients().size(); i++) {
                    ingredients.append(BakingWrapper.getInstance().getBakings().getIngredients().get(i).getQuantity());
                    ingredients.append("  ");
                    ingredients.append(BakingWrapper.getInstance().getBakings().getIngredients().get(i).getMeasure());
                    ingredients.append("  ");
                    ingredients.append(BakingWrapper.getInstance().getBakings().getIngredients().get(i).getIngredient());
                    ingredients.append("\n");
                }

                sharedPrefsEditor.putString("Ingredients", ingredients.toString());
                sharedPrefsEditor.apply();

                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
