package com.example.nouran.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nouran.bakingapp.Activities.StepActivity;
import com.example.nouran.bakingapp.data.Bakings;
import com.example.nouran.bakingapp.data.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;


public class StepDetailFragment extends Fragment {

    private Steps step;
    private TextView textView;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private String TAG = StepActivity.class.getSimpleName();
    private Uri videoUri;
    private long position;
    private Button prevBtn;
    private Button nextBtn;
    private Bakings bakings;
    private TrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private TrackSelection.Factory videoTrackSelectionFactory;
    private LoadControl loadControl;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource videoSource;

    public StepDetailFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, final @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        step = getArguments().getParcelable("lol");
//        step = StepWrapper.getInstance().getSteps();
        textView = rootView.findViewById(R.id.v_text_view);
        mPlayerView = rootView.findViewById(R.id.video_view);
        prevBtn = rootView.findViewById(R.id.Previous_btn);
        nextBtn = rootView.findViewById(R.id.Next_btn);

        setBakingStep(step);
        Log.i("lol2", "" + position);

        if (nextBtn != null) {


            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = Integer.parseInt(step.getId()) + 1;
                    bakings = BakingWrapper.getInstance().getBakings();
                    int h = 0;
                    for (int i = 0; i < bakings.getSteps().size(); i++) {
                        if (Integer.parseInt(bakings.getSteps().get(i).getId()) == (index)) {
                            StepWrapper.getInstance().setSteps(bakings.getSteps().get(i));
                            step = bakings.getSteps().get(i);
                            setBakingStep(bakings.getSteps().get(i));
                            initializePlayer(Uri.parse(bakings.getSteps().get(i).getVideoURL()));
                            h = 1;
                        }
                        if (h == 1)
                            break;
                    }
                }
            });

            prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = Integer.parseInt(step.getId()) - 1;
                    bakings = BakingWrapper.getInstance().getBakings();
                    int h = 0;
                    for (int i = 0; i < bakings.getSteps().size(); i++) {
                        if (Integer.parseInt(bakings.getSteps().get(i).getId()) == (index)) {
                            StepWrapper.getInstance().setSteps(bakings.getSteps().get(i));
                            step = bakings.getSteps().get(i);
                            setBakingStep(bakings.getSteps().get(i));
                            initializePlayer(Uri.parse(bakings.getSteps().get(i).getVideoURL()));
                            h = 1;
                        }
                        if (h == 1)
                            break;
                    }

                }
            });
        }
//        initializePlayer(Uri.parse(step.getVideoURL()));

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        // Create a default TrackSelector
        bandwidthMeter = new DefaultBandwidthMeter();
        videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        loadControl = new DefaultLoadControl();

        //Initialize the player
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        //Initialize simpleExoPlayerView
        mPlayerView.setPlayer(mExoPlayer);

        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"));

        // Produces Extractor instances for parsing the media data.
        extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        videoSource = new ExtractorMediaSource(mediaUri, dataSourceFactory, extractorsFactory, null, new ExtractorMediaSource.EventListener() {
            @Override
            public void onLoadError(IOException e) {

            }
        });
        // Prepare the player with the source.
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("SELECTED_POSITION", position);
        outState.putParcelable("step",step);
//        outState.putParcelable("stepObject",StepWrapper.getInstance().getSteps());
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("SELECTED_POSITION", C.TIME_UNSET);
            step = savedInstanceState.getParcelable("step");
        }
        Log.i(TAG, "onRestoreInstanceState: " + position);
    }

    @Override
    public void onPause() {
        super.onPause();
//        if(Util.)
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.getPlayWhenReady();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(step.getVideoURL()));
            mExoPlayer.seekTo(position);
            step = getArguments().getParcelable("lol");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mPlayerView == null)
            initializePlayer(Uri.parse(step.getVideoURL()));
    }


    private void setBakingStep(Steps s) {
        if (s.getVideoURL() != null) {
            videoUri = Uri.parse(s.getVideoURL());
            initializePlayer(videoUri);
        }
        Log.i(TAG, "onResponse: " + position);
        if (position != C.TIME_UNSET) {
            Log.i(TAG, "onResponse:  enter");
            mExoPlayer.seekTo(position);
        }
        if (textView != null)
            textView.setText(s.getDescription());
        if (nextBtn != null && prevBtn != null) {
            if (getResources().getBoolean(R.bool.isTablet)) {
                nextBtn.setVisibility(View.GONE);
                prevBtn.setVisibility(View.GONE);
            }
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
        }
    }

}
