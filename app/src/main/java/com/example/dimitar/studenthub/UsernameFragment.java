package com.example.dimitar.studenthub;

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

public class UsernameFragment extends Fragment {


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_username, container, false);
       Button button = (Button)view.findViewById(R.id.buttonNext);
        if (container != null) {
            container.removeAllViews();
        }
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Fragment fragment1;
                fragment1 = new PasswordFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.parent_fragment,fragment1);
                ft.commit();
            }
        });
        return view;

    }




}
