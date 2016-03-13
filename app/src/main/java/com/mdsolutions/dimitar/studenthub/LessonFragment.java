package com.mdsolutions.dimitar.studenthub;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class LessonFragment extends Fragment {
View view;
    Boolean check;
    YouTubePlayer tubePlayer;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lesson, container, false);
        final ImageButton follow = (ImageButton)view.findViewById(R.id.imageButtonFollow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    follow.setImageResource(R.drawable.btn_star_on);
                } else {
                    follow.setImageResource(R.drawable.btn_star_off);
                }
            }
        });

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        youTubePlayerFragment.initialize(YouTubeDeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if(!wasRestored)
                {
                    tubePlayer = youTubePlayer;
                    tubePlayer.cueVideo("vyiftpiCs9o");
                    tubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    tubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean isFullscreen) {
                            if(isFullscreen)
                            {
                                tubePlayer.setFullscreen(false);
                                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity)context, YouTubeDeveloperKey.DEVELOPER_KEY, "vyiftpiCs9o");
                                startActivity(intent);
                            }
                        }
                    });
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_youtube_holder, youTubePlayerFragment);
        transaction.commit();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
