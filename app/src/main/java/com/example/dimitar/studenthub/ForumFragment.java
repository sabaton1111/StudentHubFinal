package com.example.dimitar.studenthub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForumFragment extends Fragment
{
    private static final String ARG_SUBJECT_ID = "param1";
    Context context;
    View view;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    SubjectParseArrayAdapter parseArrayAdapter;
    OnClickSubjectItemListener onClickSubjectItemListener;

    public ForumFragment()
    {
    }

    public static ForumFragment newInstance()
    {
        ForumFragment fragment = new ForumFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        parseArrayAdapter = new SubjectParseArrayAdapter(context, R.layout.layout_item_subject, parseObjectList);
        ParseCloud.callFunctionInBackground("GetAllSubjects", new HashMap<String, Object>(), new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    parseObjectList.addAll(parseObjects);
                    parseArrayAdapter.notifyDataSetChanged();
                } else {
                    createToast(e.getMessage() + "Forum");
                }
            }
        });
    }

    public void createToast(String text)
    {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("Created", "Done!!!");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forum, container, false);
        ListView subjectsListView = (ListView) view.findViewById(R.id.listViewSubjects);
        parseArrayAdapter = new SubjectParseArrayAdapter(context, R.layout.layout_item_subject, parseObjectList);
        subjectsListView.setAdapter(parseArrayAdapter);
        subjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickSubjectItemListener.onClickSubjectItem(parseObjectList.get(position).getObjectId());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnClickSubjectItemListener)
        {
            onClickSubjectItemListener = (OnClickSubjectItemListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
        Log.d("context", context.toString());
    }

    public interface OnClickSubjectItemListener
    {
        public void onClickSubjectItem(String subjectId);
    }
}