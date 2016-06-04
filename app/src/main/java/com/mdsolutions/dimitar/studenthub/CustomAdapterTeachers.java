package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Dimitar on 6.3.2016 г..
 */
public class CustomAdapterTeachers extends RecyclerView.Adapter<CustomAdapterTeachers.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    OnClickAboutTeacher onClickAboutTeacher;
    OnClickFollowLesson onClickFollowLesson;
    private String[] mDataSet;
    Context context;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button buttonAbout, buttonFollow;
        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
           this.buttonAbout = (Button)v.findViewById(R.id.buttonAboutMeTeacher);
           this.buttonFollow = (Button)v.findViewById(R.id.buttonPoint);
            buttonFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickFollowLesson.OnClickFollowLesson();
                }
            });
            buttonAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickAboutTeacher.OnClickAboutTeacher();
                }
            });

        /*    v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
*/
        }

    }
    // END_INCLUDE(recyclerViewSampleViewHolder)


    public CustomAdapterTeachers(String[] dataSet, Context context) {
        mDataSet = dataSet;
        this.context = context;
        if (this.context instanceof OnClickAboutTeacher) {
            onClickAboutTeacher = (OnClickAboutTeacher) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickAboutTeacher");
        }
        if (this.context instanceof OnClickFollowLesson) {
            onClickFollowLesson= (OnClickFollowLesson) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickFollowLesson");
        }
        Log.d("context2-", " " + (context == null));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_teacher, viewGroup, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
  /*      viewHolder.buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAboutTeacher.OnClickAboutTeacher();
            }
        });
        viewHolder.buttonFollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickFollowLesson.OnClickFollowLesson();
            }
        });*/

    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)
    public void onAttach(Context context) {
        Log.d("context3-", " " + (context == null));
       // this.context = context;


    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
    public interface OnClickAboutTeacher
    {
        public void OnClickAboutTeacher();
    }
    public interface OnClickFollowLesson
    {
        public void OnClickFollowLesson();
    }
}
