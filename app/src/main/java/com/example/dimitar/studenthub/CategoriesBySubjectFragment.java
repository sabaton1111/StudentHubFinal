package com.example.dimitar.studenthub;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriesBySubjectFragment extends Fragment
{
    private static final String ARG_SUBJECT_ID = "param1";
    Context context;
    View view;
    String subjectId;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    CategoriesParseArrayAdapter parseArrayAdapter;
    HashMap<String, Object> parseRequestHashMap = new HashMap<String, Object>();

    public CategoriesBySubjectFragment()
    {
    }

    public static CategoriesBySubjectFragment newInstance(String subjectId)
    {
        CategoriesBySubjectFragment fragment = new CategoriesBySubjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBJECT_ID, subjectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.subjectId = getArguments().getString(ARG_SUBJECT_ID);
        }
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
        view = inflater.inflate(R.layout.fragment_categories_by_subject, container, false);
        ListView categoriesListView = (ListView) view.findViewById(R.id.listViewCategories);
        parseArrayAdapter = new CategoriesParseArrayAdapter(context, R.layout.layout_item_subject, parseObjectList);
        categoriesListView.setAdapter(parseArrayAdapter);
        parseRequestHashMap.put("subjectId", this.subjectId);
        ParseCloud.callFunctionInBackground("GetAllCategories", parseRequestHashMap, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    parseObjectList.addAll(parseObjects);
                    parseArrayAdapter.notifyDataSetChanged();
                } else {
                    createToast(e.getMessage());
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        Log.d("context", context.toString());
    }
}