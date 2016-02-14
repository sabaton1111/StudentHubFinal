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


public class PasswordFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_password, container, false);
        Button button10 = (Button)view.findViewById(R.id.buttonNext2);
        if (container != null) {
            container.removeAllViews();
        }
        button10.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Fragment fragment2;
                fragment2 = new EmailFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.parent_fragment,fragment2);
                ft.commit();
            }
        });
        return view;
    }

}
