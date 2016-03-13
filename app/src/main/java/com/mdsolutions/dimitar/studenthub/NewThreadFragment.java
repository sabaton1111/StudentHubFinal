package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.HashMap;

public class NewThreadFragment extends Fragment {

    EditText second;
    TextView textPost;
    EditText description;
    ImageButton removeThread;
    TextView textPost2;
    ImageView firstImage;
    ImageView secondImage;
    FloatingActionButton fabSaveThread;
    EditText textTitle;
    EditText textDescription;
    ParseObject category;
    Context context;

    HashMap<String, Object> parseRequestHashMap = new HashMap<String, Object>();

    public void setCategory(ParseObject category)
    {
        this.category = category;
    }

    public static NewThreadFragment newInstance(ParseObject category)
    {
        NewThreadFragment fragment = new NewThreadFragment();
        fragment.setCategory(category);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_new_thread, container, false);
        second = (EditText) view.findViewById(R.id.editTextThread2);
        textPost = (TextView) view.findViewById(R.id.textViewPost2);
        description = (EditText) view.findViewById(R.id.editTextDescription2);
        removeThread = (ImageButton) view.findViewById(R.id.imageButtonRemoveThread);
        textPost2 = (TextView) view.findViewById(R.id.textViewThread2);
        firstImage = (ImageView) view.findViewById(R.id.imageViewThread1);
        secondImage = (ImageView) view.findViewById(R.id.imageViewPost1);


        textTitle = (EditText) view.findViewById(R.id.editTextThread);
        textDescription = (EditText) view.findViewById(R.id.editTextContent);
        FloatingActionButton fabSaveThread = (FloatingActionButton) view.findViewById(R.id.fab_new_thread_save);

        textPost2.setVisibility(view.GONE);
        firstImage.setVisibility(view.GONE);
        secondImage.setVisibility(view.GONE);
        removeThread.setVisibility(view.GONE);
        second.setVisibility(view.GONE);
        textPost.setVisibility(view.GONE);
        description.setVisibility(view.GONE);

        final ImageButton newThread = (ImageButton) view.findViewById(R.id.imageButtonNewThread);
        /*newThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second.setVisibility(view.VISIBLE);
                newThread.setVisibility(view.GONE);
                firstImage.setVisibility(view.VISIBLE);
                textPost2.setVisibility(view.VISIBLE);
                textPost.setVisibility(view.VISIBLE);
                secondImage.setVisibility(view.VISIBLE);
                removeThread.setVisibility(view.VISIBLE);
                description.setVisibility(view.VISIBLE);
            }
        });*/
        removeThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeThread.setVisibility(view.GONE);
                newThread.setVisibility(view.VISIBLE);
                firstImage.setVisibility(view.GONE);
                secondImage.setVisibility(view.GONE);
                second.setVisibility(view.GONE);
                textPost.setVisibility(view.GONE);
                textPost2.setVisibility(view.GONE);
                description.setVisibility(view.GONE);
            }
        });
        fabSaveThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewThread();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void makeNewThread()
    {
        loadRequestHashMap();
        AndroidUtils.createToast("Saving", context);
        ParseCloud.callFunctionInBackground("MakeThread", parseRequestHashMap, new FunctionCallback<Object>() {
            @Override
            public void done(Object object, ParseException e) {
                if(e != null)
                {
                    AndroidUtils.createToast("Error: " + e.getMessage(), context);
                    e.printStackTrace();
                }
                else
                {
                    AndroidUtils.createToast("Saved", context);
                }
            }
        });
    }

    public void loadRequestHashMap()
    {
        parseRequestHashMap.put("title", textTitle.getText().toString());
        parseRequestHashMap.put("content", textDescription.getText().toString());
        parseRequestHashMap.put("categoryId", category.getObjectId());
    }

}
