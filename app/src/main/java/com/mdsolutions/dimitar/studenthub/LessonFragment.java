package com.mdsolutions.dimitar.studenthub;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class LessonFragment extends Fragment {
View view;
    Boolean check;
    YouTubePlayer tubePlayer;
    Context context;
    ParseObject course;
    ImageView imageViewLesson;

    public void setCourse(ParseObject course)
    {
        this.course = course;
    }

    public static LessonFragment newInstance(ParseObject course)
    {
        LessonFragment fragment = new LessonFragment();
        fragment.setCourse(course);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lesson, container, false);
        final ImageButton follow = (ImageButton)view.findViewById(R.id.imageButtonFollow);

        TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitleLesson);
        TextView textViewDescriptionLesson = (TextView) view.findViewById(R.id.textViewDescriptionLesson);
        TextView textViewMainLesson = (TextView) view.findViewById(R.id.textViewMainLesson);
        TextView textViewResources = (TextView) view.findViewById(R.id.textViewResources);
        TextView textViewLectorsLesson = (TextView) view.findViewById(R.id.textViewLectorsLesson);
        textViewTitle.setText(course.getString("title"));
        textViewDescriptionLesson.setText(course.getString("description"));
        textViewResources.setText(course.getString("resourcesLink"));
        ParseUser lector = (ParseUser) course.getList("lectors").get(0);
        textViewLectorsLesson.setText(lector.getUsername());
        TextView textViewHomework = (TextView) view.findViewById(R.id.textViewHomework);
        textViewHomework.setText(course.getString("homework"));
        imageViewLesson = (ImageView) view.findViewById(R.id.imageViewLesson);

        //textViewResources.setMovementMethod(LinkMovementMethod.getInstance());

        ParseFile fileObject = (ParseFile) course.get("picture");
        fileObject
                .getDataInBackground(new GetDataCallback() {

                    public void done(byte[] data,
                                     ParseException e) {
                        if (e == null) {
                            // Decode the Byte[] into
                            // Bitmap
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                            // initialize

                            // Set the Bitmap into the
                            // ImageView
                            imageViewLesson.setImageBitmap(bmp);

                        } else {
                            e.printStackTrace();
                        }
                    }
                });

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
                    tubePlayer.cueVideo(course.getString("videoLink"));
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
