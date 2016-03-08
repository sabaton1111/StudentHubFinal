package com.example.dimitar.studenthub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class LessonFragment extends Fragment {
View view;
    Boolean check;
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
        return view;
    }

}
