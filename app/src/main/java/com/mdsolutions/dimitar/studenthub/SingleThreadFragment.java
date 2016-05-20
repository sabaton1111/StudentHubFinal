package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SingleThreadFragment extends Fragment
{
    Context context;
    View view;
    private ParseObject thread;
    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
    PostsParseArrayAdapter parseArrayAdapter;
    HashMap<String, Object> parseRequestHashMap = new HashMap<String, Object>();
    OnClickNewPostButtonListener onClickNewPostButtonListener;
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
        parseObjectList.addAll(ParseDataModel.getPosts(this.thread.getObjectId()));
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
        final List<ParseObject> ramPosts = ParseDataModel.getPosts(this.thread.getObjectId());
        if (ramPosts == null || ramPosts.size() == 0) {
            ParseQuery query = ParseQuery.getQuery("Post");
            String pinName = "threads" + thread.getObjectId();
            query.whereEqualTo("thread", this.thread);
            query.fromPin(pinName);
            query.ignoreACLs();
            query.include("user");
            query.findInBackground(new FindCallback() {
                @Override
                public void done(List list, ParseException e) {
                    ParseDataModel.UpdatePostData(thread.getObjectId(), list);
                    FetchDataWithModel();
                }

                @Override
                public void done(Object o, Throwable throwable) {
                    try {
                        throw throwable;
                    } catch (Throwable throwable1) {
                        throwable1.printStackTrace();
                    }
                    ParseDataModel.UpdatePostData(thread.getObjectId(), (List) o);
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
        ParseCloud.callFunctionInBackground("GetAllPosts", parseRequestHashMap, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    ParseDataModel.UpdatePostData(thread.getObjectId(), parseObjects);
                    final String pinName = "threads" + thread.getObjectId();
                    ParseObject.unpinAllInBackground(pinName, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(pinName, parseObjects, new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    FetchDataWithModel();
                                }
                            });
                        }
                    });
                } else {
                    createToast(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArrayAdapter = new PostsParseArrayAdapter(context, R.layout.layout_item_threadpost, parseObjectList,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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
        view = inflater.inflate(R.layout.fragment_single_thread, container, false);
        ListView threadPostsListView = (ListView) view.findViewById(R.id.listViewThreadPosts);
        threadPostsListView.setAdapter(parseArrayAdapter);

        View header = inflater.inflate(R.layout.layout_header_threadpost, threadPostsListView, false);

        TextView textViewHeaderTitle = (TextView) header.findViewById(R.id.textViewTitle);
        TextView textViewHeaderContent = (TextView) header.findViewById(R.id.editTextContent);
        textViewHeaderTitle.setText(thread.getString("title"));
        textViewHeaderContent.setText(thread.getString("content"));

        threadPostsListView.addHeaderView(header);

        FloatingActionButton buttonPost = (FloatingActionButton) view.findViewById(R.id.fab_new_post);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewPostButtonListener.onClickNewPostButton(thread);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        if (context instanceof OnClickNewPostButtonListener)
        {
            onClickNewPostButtonListener = (OnClickNewPostButtonListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnClickNewPostButtonListener");
        }
    }
    public interface OnClickNewPostButtonListener
    {
        void onClickNewPostButton(ParseObject thread);
    }
}
