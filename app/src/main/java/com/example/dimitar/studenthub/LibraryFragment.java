package com.example.dimitar.studenthub;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LibraryFragment extends Fragment {
    Context context;
    OnClickNewCourseButtonListener onClickNewCourseButtonListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_library, container, false);

        View view = inflater.inflate(R.layout.fragment_library,
                container, false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.fab_new_lesson);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickNewCourseButtonListener.onClickNewCourseButton();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnClickNewCourseButtonListener)
        {
            onClickNewCourseButtonListener = (OnClickNewCourseButtonListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickNewCourseButtonListener");
        }
    }

    public interface OnClickNewCourseButtonListener
    {
        public void onClickNewCourseButton();
    }
}
