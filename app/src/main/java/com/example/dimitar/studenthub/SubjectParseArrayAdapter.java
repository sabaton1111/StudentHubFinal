package com.example.dimitar.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Martin on 18.2.2016 г..
 */



public class SubjectParseArrayAdapter extends ArrayAdapter<ParseObject>
{
    Context context;
    List<ParseObject> parseObjects;
    public SubjectParseArrayAdapter(Context context, int textViewResourceId, List<ParseObject> objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.parseObjects = objects;
    }

    @Override
    public ParseObject getItem(int position) {
        return super.getItem(super.getCount() - 1 - position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SubjectViewHolder viewHolder;
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_subject, parent, false);
            viewHolder = new SubjectViewHolder();
            viewHolder.textFirst = (TextView) view.findViewById(R.id.textViewFirst);
            viewHolder.textSecond = (TextView) view.findViewById(R.id.textViewSecond);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (SubjectViewHolder) view.getTag();
        }
        ParseObject parseObject = super.getItem(position);

        viewHolder.textFirst.setText(parseObject.getString("title"));
        viewHolder.textSecond.setText(parseObject.getCreatedAt().toString());

        return view;
    }

    private class SubjectViewHolder
    {
        public TextView textFirst;
        public TextView textSecond;
    }
}