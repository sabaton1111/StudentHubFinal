package com.mdsolutions.dimitar.studenthub;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UsernameFragment extends Fragment {
    Activity activity;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_username, container, false);
        activity = getActivity();
       Button button = (Button)view.findViewById(R.id.buttonNext);
        if (container != null) {
            container.removeAllViews();
        }
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((OnClickSignUpNextButtonListener) activity).onClickSignUpNextButton(R.id.fragment_username);
            }
        });
        return view;
    }
}
