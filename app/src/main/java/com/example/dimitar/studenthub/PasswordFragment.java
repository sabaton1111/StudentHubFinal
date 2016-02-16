package com.example.dimitar.studenthub;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.AccessibleObject;


public class PasswordFragment extends Fragment
{
    Activity activity;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_password, container, false);
        activity = getActivity();
        Button button10 = (Button)view.findViewById(R.id .nextButton2);
        if (container != null) {
            container.removeAllViews();
        }
        button10.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((OnClickSignUpNextButtonListener) activity).onClickSignUpNextButton(R.id.fragment_password);
            }
        });
        return view;
    }

}
