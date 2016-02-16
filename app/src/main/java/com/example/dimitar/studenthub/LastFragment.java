package com.example.dimitar.studenthub;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LastFragment extends Fragment
{
    Activity activity;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button registerButton = (Button) container.findViewById(R.id.registerSignUpButton);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_last, container, false);
        activity = getActivity();
        Button button = (Button)view.findViewById(R.id.registerSignUpButton);
        container.removeAllViews();
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((OnClickSignUpNextButtonListener) activity).onClickSignUpNextButton(R.id.fragment_last);
            }
        });
        return view;
    }
}
