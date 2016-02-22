package com.example.dimitar.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseObject;

import org.w3c.dom.Text;

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
    public int getItemViewType(int position) {
        return super.getItemViewType(parseObjects.size() - 1 - position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_category, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textFirst = (TextView) view.findViewById(R.id.textViewFirst);
            viewHolder.textSecond = (TextView) view.findViewById(R.id.textViewSecond);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ParseObject parseObject = parseObjects.get(position);

        viewHolder.textFirst.setText(parseObject.getString("title"));
        viewHolder.textSecond.setText(parseObject.getCreatedAt().toString());

        return view;
    }
}