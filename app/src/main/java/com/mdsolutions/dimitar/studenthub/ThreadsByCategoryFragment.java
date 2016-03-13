package com.mdsolutions.dimitar.studenthub;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// Fragment for showing List of Threads by given Category
public class ThreadsByCategoryFragment extends Fragment
{
    Context context;
    View view;
    private ParseObject category;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    ThreadsParseArrayAdapter parseArrayAdapter;
    HashMap<String, Object> parseRequestHashMap = new HashMap<String, Object>();
    OnClickThreadItemListener onClickThreadItemListener;
    OnClickNewThreadButtonListener onClickNewThreadButtonListener;
    public ThreadsByCategoryFragment()
    {
    }

    public void setCategory(ParseObject category)
    {
        this.category = category;
    }

    public static ThreadsByCategoryFragment newInstance(ParseObject category)
    {
        ThreadsByCategoryFragment fragment = new ThreadsByCategoryFragment();
        fragment.setCategory(category);
        return fragment;
    }

    public void FetchDataWithModel()
    {
        parseObjectList.clear();
        parseObjectList.addAll(ForumModel.getThreads(category.getObjectId()));
        parseArrayAdapter.notifyDataSetChanged();

        /*if(ForumModel.getThreads(category.getObjectId()).size() > 0)
        {
            createToast(((ParseUser) (ForumModel.getThreads(category.getObjectId()).get(0).get("user"))).getUsername());
        }*/
    }

    public void LoadData()
    {
        LoadLocalData();
        // Now that the local DB content is displayed, if there is a network, load online content for changes
        LoadOnlineData();
    }

    public void LoadLocalData()
    {
        final List<ParseObject> ramThreads = ForumModel.getThreads(this.category.getObjectId());
        if (ramThreads == null || ramThreads.size() == 0) {
            ParseQuery query = ParseQuery.getQuery("Thread");
            query.whereEqualTo("category", this.category);
            //query.include("user");
            query.fromLocalDatastore();
            query.ignoreACLs();
            query.include("user");
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    if (e != null)
                    {
                        e.printStackTrace();
                    }
                    else
                    {
                        ForumModel.UpdateThreadData(category.getObjectId(), list);
                        FetchDataWithModel();
                    }
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    if (throwable != null)
                    {
                        try {
                            throw throwable;
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                    final List<ParseObject> list = (List<ParseObject>) o;
                    ForumModel.UpdateThreadData(category.getObjectId(), (List) o);
                    FetchDataWithModel();
                }
            });
        } else
        {
            FetchDataWithModel();
        }
    }

    public void LoadOnlineData()
    {
        parseRequestHashMap.put("categoryId", this.category.getObjectId());
        ParseCloud.callFunctionInBackground("GetAllThreads", parseRequestHashMap, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    ForumModel.UpdateThreadData(category.getObjectId(), parseObjects);
                    final String pinName = "threads" + category.getObjectId();
                    ParseObject.unpinAllInBackground(pinName, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(pinName, parseObjects);
                        }
                    });
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
        parseArrayAdapter = new ThreadsParseArrayAdapter(context, R.layout.layout_item_thread, parseObjectList);
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
        view = inflater.inflate(R.layout.fragment_threads_by_category, container, false);
        final ListView threadsListView = (ListView) view.findViewById(R.id.listViewThreads);
        threadsListView.setAdapter(parseArrayAdapter);
        threadsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickThreadItemListener.onClickThreadItem(parseArrayAdapter.getItem(position));
            }
        });
        FloatingActionButton button1 = (FloatingActionButton) view.findViewById(R.id.fab_new_thread);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewThreadButtonListener.onClickNewThreadButton(category);
            }
        });
        TextView textViewCategoryTitle = (TextView) view.findViewById(R.id.textViewCategoryTitle);
        textViewCategoryTitle.setText(category.getString("title"));

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnClickThreadItemListener)
        {
            this.onClickThreadItemListener = (OnClickThreadItemListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickCategoryItemListener");
        }
        if (context instanceof OnClickNewThreadButtonListener)
        {
            onClickNewThreadButtonListener = (OnClickNewThreadButtonListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickNewThreadButtonListener");
        }
    }

    public interface OnClickThreadItemListener
    {
        void onClickThreadItem(ParseObject thread);
    }
    public interface OnClickNewThreadButtonListener
    {
        public void onClickNewThreadButton(ParseObject category);
    }
}