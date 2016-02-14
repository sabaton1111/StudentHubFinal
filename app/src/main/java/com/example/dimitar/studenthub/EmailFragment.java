package com.example.dimitar.studenthub;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EmailFragment extends Fragment {

        View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_email, container, false);
        Button button4 = (Button)view.findViewById(R.id.buttonNext10);
        if (container != null) {
            container.removeAllViews();
        }
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Fragment fragment3;
                fragment3 = new NamesFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.parent_fragment,fragment3);
                ft.commit();
            }
        });
        return view;
    }


}
