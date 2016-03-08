package com.example.dimitar.studenthub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class NewThreadFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_new_thread, container, false);
        final EditText second = (EditText)view.findViewById(R.id.editTextThread2);
        final TextView textPost = (TextView)view.findViewById(R.id.textViewPost2);
        final EditText description = (EditText)view.findViewById(R.id.editTextDescription2);
        final ImageButton removeThread = (ImageButton)view.findViewById(R.id.imageButtonRemoveThread);
        final TextView textPost2 = (TextView)view.findViewById(R.id.textViewThread2);
        final ImageView firstImage = (ImageView)view.findViewById(R.id.imageViewThread1);
        final ImageView secondImage = (ImageView)view.findViewById(R.id.imageViewPost1);
        textPost2.setVisibility(view.GONE);
        firstImage.setVisibility(view.GONE);
        secondImage.setVisibility(view.GONE);
        removeThread.setVisibility(view.GONE);
        second.setVisibility(view.GONE);
        textPost.setVisibility(view.GONE);
        description.setVisibility(view.GONE);

        final ImageButton newThread = (ImageButton)view.findViewById(R.id.imageButtonNewThread);
        newThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second.setVisibility(view.VISIBLE);
                newThread.setVisibility(view.GONE);
                firstImage.setVisibility(view.VISIBLE);
                textPost2.setVisibility(view.VISIBLE);
                textPost.setVisibility(view.VISIBLE);
                secondImage.setVisibility(view.VISIBLE);
                removeThread.setVisibility(view.VISIBLE);
                description.setVisibility(view.VISIBLE);
            }
        });
        removeThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeThread.setVisibility(view.GONE);
                newThread.setVisibility(view.VISIBLE);
                firstImage.setVisibility(view.GONE);
                secondImage.setVisibility(view.GONE);
                second.setVisibility(view.GONE);
                textPost.setVisibility(view.GONE);
                textPost2.setVisibility(view.GONE);
                description.setVisibility(view.GONE);
            }
        });

        return view;
    }

}
