package com.example.dimitar.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Martin on 18.2.2016 г..
 */



public class CategoriesParseArrayAdapter extends ArrayAdapter<ParseObject> {
    Context context;
    List<ParseObject> parseObjects;

    public CategoriesParseArrayAdapter(Context context, int textViewResourceId, List<ParseObject> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.parseObjects = objects;
    }

    @Override
    public ParseObject getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_category, parent, false);
            viewHolder = new CategoryViewHolder();
            viewHolder.textFirst = (TextView) view.findViewById(R.id.textViewFirst);
            viewHolder.textSecond = (TextView) view.findViewById(R.id.textViewDate);
            view.setTag(viewHolder);
        } else {
            viewHolder = (CategoryViewHolder) view.getTag();
        }
        ParseObject parseObject = super.getItem(position);

        Date date = parseObject.getCreatedAt();
        SimpleDateFormat formater = new SimpleDateFormat("d/M/yyyy");
        String datestring = formater.format(date);

        viewHolder.textFirst.setText(parseObject.getString("title"));
        viewHolder.textSecond.setText(datestring);

        return view;
    }

    private class CategoryViewHolder
    {
        public TextView textFirst;
        public TextView textSecond;
    }
}