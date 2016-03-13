package com.mdsolutions.dimitar.studenthub;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Fragment for showing the first page of the forum - list of all Subjects
public class LibrarySubjectsFragment extends Fragment
{
    private static final String ARG_SUBJECT_ID = "param1";
    Context context;
    View view;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    SubjectParseArrayAdapter parseArrayAdapter;
    OnClickLibrarySubjectItemListener onClickSubjectItemListener;

    public LibrarySubjectsFragment()
    {
    }

    public static LibrarySubjectsFragment newInstance()
    {
        LibrarySubjectsFragment fragment = new LibrarySubjectsFragment();
        return fragment;
    }

    public void FetchDataWithModel()
    {
        parseObjectList.clear();
        parseObjectList.addAll(ParseDataModel.subjects);
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
        if (ParseDataModel.subjects.size() == 0)
        {
            ParseQuery query = ParseQuery.getQuery("Subject");
            query.fromLocalDatastore();
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    ParseDataModel.UpdateSubjectsData(list);
                    FetchDataWithModel();
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    ParseDataModel.UpdateSubjectsData((List) o);
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
        ParseCloud.callFunctionInBackground("GetAllSubjects", new HashMap<String, Object>(), new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    ParseDataModel.UpdateSubjectsData(parseObjects);
                    ParseObject.pinAllInBackground(parseObjects);
                    FetchDataWithModel();
                } else {
                    createToast(e.getMessage() + "Forum");
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArrayAdapter = new SubjectParseArrayAdapter(context, R.layout.layout_item_subject, parseObjectList);
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forum, container, false);
        ListView subjectsListView = (ListView) view.findViewById(R.id.listViewSubjects);
        subjectsListView.setAdapter(parseArrayAdapter);
        subjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickSubjectItemListener.onClickLibrarySubjectItem(parseObjectList.get(position));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnClickLibrarySubjectItemListener)
        {
            onClickSubjectItemListener = (OnClickLibrarySubjectItemListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickSubjectItemListener");
        }
    }

    public interface OnClickLibrarySubjectItemListener
    {
        void onClickLibrarySubjectItem(ParseObject subject);
    }
}