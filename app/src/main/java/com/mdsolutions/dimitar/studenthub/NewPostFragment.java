package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.HashMap;

public class NewPostFragment extends Fragment {
    FloatingActionButton fabSave;
    TextView textViewThreadTitle;
    EditText editTextContent;
    HashMap<String, Object> parseRequestHashMap = new HashMap<>();
    ParseObject thread;
    Context context;

    public void setThread(ParseObject thread)
    {
        this.thread = thread;
    }

    public static NewPostFragment newInstance(ParseObject thread)
    {
        NewPostFragment fragment = new NewPostFragment();
        fragment.setThread(thread);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_new_post, container, false);

        fabSave = (FloatingActionButton) view.findViewById(R.id.fab_new_post_save);
        textViewThreadTitle = (TextView) view.findViewById(R.id.textViewThreadTitle);
        editTextContent = (EditText) view.findViewById(R.id.editTextContent);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewPost();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void makeNewPost()
    {
        loadRequestHashMap();
        AndroidUtils.createToast("Saving", context);
        ParseCloud.callFunctionInBackground("MakePost", parseRequestHashMap, new FunctionCallback<Object>() {
            @Override
            public void done(Object object, ParseException e) {
                if(e == null)
                {
                    AndroidUtils.createToast("Saved", context);
                }
                else
                {
                    AndroidUtils.createToast("Error: " + e.getMessage(), context);
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadRequestHashMap()
    {
        parseRequestHashMap.put("content", editTextContent.getText().toString());
        parseRequestHashMap.put("threadId", thread.getObjectId());
    }
}
