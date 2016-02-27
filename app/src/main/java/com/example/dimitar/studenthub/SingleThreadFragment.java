package com.example.dimitar.studenthub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleThreadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleThreadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleThreadFragment extends Fragment
{
    Context context;
    View view;
    private ParseObject thread;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    PostsParseArrayAdapter parseArrayAdapter;
    HashMap<String, Object> parseRequestHashMap = new HashMap<String, Object>();

    public SingleThreadFragment()
    {
    }

    public void setThread(ParseObject thread)
    {
        this.thread = thread;
    }

    public static SingleThreadFragment newInstance(ParseObject thread)
    {
        SingleThreadFragment fragment = new SingleThreadFragment();
        fragment.setThread(thread);
        return fragment;
    }

    public void FetchDataWithModel()
    {
        parseObjectList.clear();
        parseObjectList.addAll(ForumModel.getPosts(this.thread.getObjectId()));
        if (parseArrayAdapter == null)
        {
            Log.d("adaptera e null...", "---------");
        }
        Log.d(parseObjectList.toString(), "--te tova e lista na postovete");
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
        final List<ParseObject> ramPosts = ForumModel.getPosts(this.thread.getObjectId());
        if (ramPosts == null || ramPosts.size() == 0) {
            ParseQuery query = ParseQuery.getQuery("Post");
            query.whereEqualTo("thread", this.thread);
            //query.include("user");
            //query.fromPin();
            query.fromLocalDatastore();
            query.ignoreACLs();
            query.include("user");
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    Log.d("Deba", "!!!!! Post");
                    ForumModel.UpdatePostData(thread.getObjectId(), list);
                    FetchDataWithModel();
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    //Log.d("Deba", "))))) category" + o.toString() + ((ParseObject)((List)o).get(0)).getParseObject("subject"));
                    try {
                        throw throwable;
                    } catch (Throwable throwable1) {
                        throwable1.printStackTrace();
                    }
                    ForumModel.UpdatePostData(thread.getObjectId(), (List) o);
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
        parseRequestHashMap.put("threadId", this.thread.getObjectId());
        Log.d("Deba", "categoriqta e " + this.thread.getObjectId());
        ParseCloud.callFunctionInBackground("GetAllPosts", parseRequestHashMap, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    ForumModel.UpdatePostData(thread.getObjectId(), parseObjects);
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
        parseArrayAdapter = new PostsParseArrayAdapter(context, R.layout.layout_item_threadpost, parseObjectList);
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
        view = inflater.inflate(R.layout.fragment_single_thread, container, false);
        ListView threadPostsListView = (ListView) view.findViewById(R.id.listViewThreadPosts);
        threadPostsListView.setAdapter(parseArrayAdapter);
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
