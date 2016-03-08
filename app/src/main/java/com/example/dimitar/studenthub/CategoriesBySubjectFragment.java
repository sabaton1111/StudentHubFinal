package com.example.dimitar.studenthub;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriesBySubjectFragment extends Fragment
{
    private static final String ARG_SUBJECT_ID = "param1";
    Context context;
    View view;
    private ParseObject subject;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    CategoriesParseArrayAdapter parseArrayAdapter;
    HashMap<String, Object> parseRequestHashMap = new HashMap<String, Object>();
    OnClickCategoryItemListener onClickCategoryItemListener;


    OnClickNewCategoryButtonListener onClickNewCategoryButtonListener;
    public CategoriesBySubjectFragment()
    {
    }

    public void setSubject(ParseObject subject)
    {
        this.subject = subject;
    }

    public static CategoriesBySubjectFragment newInstance(ParseObject subject)
    {
        CategoriesBySubjectFragment fragment = new CategoriesBySubjectFragment();
        fragment.setSubject(subject);
        return fragment;
    }

    public void FetchDataWithModel()
    {
        parseObjectList.clear();
        parseObjectList.addAll(ForumModel.getCategories(subject.getObjectId()));
        parseArrayAdapter.notifyDataSetChanged();
    }

    public void LoadData()
    {
        LoadLocalData();
        // Now that the local DB content is displayed, if there is a network, load online content for changes
        LoadOnlineData();
    }

    public void LoadLocalData()
    {
        final List<ParseObject> ramCategories = ForumModel.getCategories(this.subject.getObjectId());
        if (ramCategories == null || ramCategories.size() == 0) {
            ParseQuery query = ParseQuery.getQuery("Category");
            query.whereEqualTo("subject", this.subject);
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    Log.d("Deba", "!!!!! Cat");
                    ForumModel.UpdateCategoryData(subject.getObjectId(), list);
                    FetchDataWithModel();
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    //Log.d("Deba", "))))) category" + o.toString() + ((ParseObject)((List)o).get(0)).getParseObject("subject"));
                    ForumModel.UpdateCategoryData(subject.getObjectId(), (List) o);
                    FetchDataWithModel();
                }
            });
        }
        else
        {
            FetchDataWithModel();
        }
    }

    public void LoadOnlineData()
    {
        parseRequestHashMap.put("subjectId", this.subject.getObjectId());
        ParseCloud.callFunctionInBackground("GetAllCategories", parseRequestHashMap, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    ForumModel.UpdateCategoryData(subject.getObjectId(), parseObjects);
                    ParseObject.pinAllInBackground(parseObjects);
                    FetchDataWithModel();
                } else {
                    createToast(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArrayAdapter = new CategoriesParseArrayAdapter(this.context, R.layout.layout_item_category, parseObjectList);
        LoadData();
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
        categoriesListView.setAdapter(parseArrayAdapter);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickCategoryItemListener.onClickCategoryItem(parseArrayAdapter.getItem(position));
            }
        });
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.fab_new_category);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewCategoryButtonListener.onClickNewCategoryButton();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnClickCategoryItemListener)
        {
            this.onClickCategoryItemListener = (OnClickCategoryItemListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickCategoryItemListener");
        }
        Log.d("context", context.toString());

        if (context instanceof OnClickNewCategoryButtonListener)
    {
        onClickNewCategoryButtonListener = (OnClickNewCategoryButtonListener) context;
    }
    else
    {
        throw new RuntimeException(context.toString() + " must implement OnClickNewCategoryButtonListener");
    }
    }

    public interface OnClickCategoryItemListener
    {
        public void onClickCategoryItem(ParseObject category);
    }
    public interface OnClickNewCategoryButtonListener
    {
        public void onClickNewCategoryButton();
    }
}