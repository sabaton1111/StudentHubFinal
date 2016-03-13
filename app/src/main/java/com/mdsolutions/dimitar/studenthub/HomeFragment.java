
package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;


public class HomeFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int DATASET_COUNT = 15;
    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView,mRecyclerView1;
    protected CustomAdapterForum mAdapter;
    protected CustomAdapterLessons mAdapter1;
    protected RecyclerView.LayoutManager mLayoutManager,mLayoutManager1;
    protected String[] mDataset,mDataset1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }
    Context context;
    OnClickForumOpenListener onClickForumOpenListener;
    OnClickLessonsOpenListener onClickLessonsOpenListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

     /*   Button forumOpen = (Button)view.findViewById(R.id.buttonForum);
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
        });*/
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

        view.setTag(TAG);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewForum);
        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerViewLesson);
       /* mLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager horizontalLayoutManagaer*/
               mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView.setAdapter(mAdapter);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
       // setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new CustomAdapterForum(mDataset);
        mAdapter1 = new CustomAdapterLessons(mDataset1);
        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }


        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void initDataset() {
        mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            //elements of array
        }
        mDataset1 = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            //elements of array
        }
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
