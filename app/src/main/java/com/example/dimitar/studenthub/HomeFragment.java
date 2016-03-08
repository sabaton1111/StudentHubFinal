
package com.example.dimitar.studenthub;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.dimitar.studenthub.R;


public class HomeFragment extends Fragment {
    Context context;
    OnClickForumOpenListener onClickForumOpenListener;
    OnClickLessonsOpenListener onClickLessonsOpenListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button forumOpen = (Button)view.findViewById(R.id.buttonForum);
        forumOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForumOpenListener.OnClickForumOpenButton();
            }
        });
        Button lessonsOpen = (Button)view.findViewById(R.id.buttonLessons);
        lessonsOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLessonsOpenListener.OnClickLessonsOpenButton();
            }
        });
        //Setting up the tabs
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

        //Change the text color of tabs
        th.getTabWidget();
        for(int i=0;i<th.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#f5f5f5"));
        }
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof OnClickForumOpenListener)
        {
            this.onClickForumOpenListener = (OnClickForumOpenListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickForumOpenListener");
        }
        if (context instanceof OnClickLessonsOpenListener)
        {
            this.onClickLessonsOpenListener = (OnClickLessonsOpenListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickLessonsOpenListener");
        }
    }
    public interface OnClickForumOpenListener {
        public void OnClickForumOpenButton();
    }
    public interface OnClickLessonsOpenListener {
        public void OnClickLessonsOpenButton();
    }
}
