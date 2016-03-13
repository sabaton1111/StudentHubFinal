package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by martin on 2/25/16.
 */
public class PostsParseArrayAdapter extends ArrayAdapter<ParseObject>
{
    Context context;
    List<ParseObject> parseObjects;

    public PostsParseArrayAdapter(Context context, int textViewResourceId, List<ParseObject> objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.parseObjects = objects;
    }

    @Override
    public ParseObject getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PostViewHolder viewHolder;
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_threadpost, parent, false);
            viewHolder = new PostViewHolder();
            viewHolder.textUsername = (TextView) view.findViewById(R.id.textViewTitle);
            viewHolder.textCreatedDate = (TextView) view.findViewById(R.id.textViewCreatedDate);
            viewHolder.textContent = (TextView) view.findViewById(R.id.textViewContent);
           // viewHolder.textUsername2 = (TextView)view.findViewById(R.id.textViewUsername2);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (PostViewHolder) view.getTag();
        }
        ParseObject parseObject = super.getItem(position);

        viewHolder.textUsername.setText(((ParseObject)parseObject.get("user")).getString("username"));
//        viewHolder.textUsername2.setText(((ParseObject)parseObject.get("user")).getString("username"));
        viewHolder.textCreatedDate.setText(parseObject.getCreatedAt().toString());
        viewHolder.textContent.setText(parseObject.getString("content"));

        return view;
    }

    private class PostViewHolder
    {
        public TextView textUsername;
        public TextView textCreatedDate;
        public TextView textContent;
        public TextView textUsername2;
    }
}
