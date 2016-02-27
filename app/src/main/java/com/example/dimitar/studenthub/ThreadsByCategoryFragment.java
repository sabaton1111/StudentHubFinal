package com.example.dimitar.studenthub;

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

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
        if (parseArrayAdapter == null)
        {
            Log.d("adaptera e null...", "---------");
        }
        Log.d(parseObjectList.toString(), "--te tova e lista na thredovete");
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
                    Log.d("Deba", "!!!!! Cat");
                    if (e != null)
                    {
                        createToast(e.getMessage() + "thlocal");
                    }
                    else
                    {
                        Log.d("emi", " update s list za threadove");
                        ForumModel.UpdateThreadData(category.getObjectId(), list);
                        FetchDataWithModel();
                    }
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    //Log.d("Deba", "))))) category" + o.toString() + ((ParseObject)((List)o).get(0)).getParseObject("subject"));
                    if (throwable != null)
                    {
                        try {
                            throw throwable;
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                    Log.d("emi", " update s o za threadove");
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
        Log.d("Deba", "categoriqta e " + this.category.getObjectId());
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
        Log.d("Created", "Done!!!");
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
        Log.d("context", context.toString());
    }

    public interface OnClickThreadItemListener
    {
        void onClickThreadItem(ParseObject thread);
    }
}