
package com.example.dimitar.studenthub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.dimitar.studenthub.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabHost th = (TabHost)view.findViewById(R.id.tabHost);
        th.setup();
        TabHost.TabSpec latest_tab = th.newTabSpec("Latest");
        latest_tab.setContent(R.id.tab1);
        latest_tab.setIndicator("Latest");
        th.addTab(latest_tab);
        latest_tab = th.newTabSpec("Popular");
        latest_tab.setContent(R.id.tab2);
        latest_tab.setIndicator("Popular");
        th.addTab(latest_tab);
        return view;
    }

}
