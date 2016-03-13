package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LibraryFragment extends Fragment {
    Context context;
    OnClickNewCourseButtonListener onClickNewCourseButtonListener;
    OnClickLessonOpenListener onClickLessonOpenListener;
    View view;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int DATASET_COUNT = 15;
    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected CustomAdapterLibrary mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_library, container, false);

        view = inflater.inflate(R.layout.fragment_library,
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
        view.setTag(TAG);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLibrary);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new CustomAdapterLibrary(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLibrary);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                     //Open Lesson Fragment
                    onClickLessonOpenListener.OnClickLessonOpen();
                    }
                })
        );

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
        if (context instanceof OnClickLessonOpenListener)
        {
            this.onClickLessonOpenListener = (OnClickLessonOpenListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickLessonOpenListener");
        }
    }

    public interface OnClickNewCourseButtonListener
    {
        public void onClickNewCourseButton();
    }

    public interface OnClickLessonOpenListener {
        public void OnClickLessonOpen();
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
    }


}
