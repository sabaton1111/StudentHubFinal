package com.example.dimitar.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by martin on 2/24/16.
 */
public class ThreadsParseArrayAdapter extends ArrayAdapter<ParseObject>
{
    Context context;
    List<ParseObject> parseObjects;

    public ThreadsParseArrayAdapter(Context context, int textViewResourceId, List<ParseObject> objects)
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
        ThreadViewHolder viewHolder;
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_thread, parent, false);
            viewHolder = new ThreadViewHolder();
            viewHolder.textSecond = (TextView) view.findViewById(R.id.textViewSecond);
            viewHolder.textUsername = (TextView) view.findViewById(R.id.textViewUsername);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ThreadViewHolder) view.getTag();
        }
        ParseObject parseObject = super.getItem(position);
        //ParseUser author = (ParseUser) parseObject.get("user");
        Date date = parseObject.getCreatedAt();
        SimpleDateFormat formater = new SimpleDateFormat("d/M/yyyy");
        String datestring = formater.format(date);
        viewHolder.textSecond.setText(datestring);
        //viewHolder.textUsername.setText(author.getUsername());

        return view;
    }

    private class ThreadViewHolder
    {
        public TextView textFirst;
        public TextView textSecond;
        public TextView textUsername;
    }
}
