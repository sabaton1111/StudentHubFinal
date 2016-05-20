package com.mdsolutions.dimitar.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.HashSet;
import java.util.List;

/**
 * Created by martin on 2/25/16.
 */
public class PostsParseArrayAdapter extends ArrayAdapter<ParseObject>
{
    Context context;
    List<ParseObject> parseObjects;
    ParseUser currUser = ParseUser.getCurrentUser();
    private static final int TYPE_POST = 0;
    private static final int TYPE_POST_OF_CURRENT_USER = 1;
    private static final int TYPE_POST_EDITMODE = 2;
    private static final int TYPE_MAX_COUNT = 3;
    LayoutInflater inflater;
    HashSet<String> editingPosts = new HashSet<>();
    String buff = "";

    public PostsParseArrayAdapter(Context context, int textViewResourceId, List<ParseObject> objects,
                                  View.OnClickListener onClickEditButtonListener)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.parseObjects = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void OnClickEditButton(int position)
    {
        AndroidUtils.createToast("CLicked pos = " + position, context);
        editingPosts.add(parseObjects.get(position).getObjectId());
        //AndroidUtils.createToast("contains?=" + editingPosts.contains(parseObjects.get(position)), context);
        notifyDataSetChanged();
    }

    public void OnClickSaveEditButton(int position)
    {
        AndroidUtils.createToast("Clicked save on pos = " + position, context);
    }

    @Override
    public ParseObject getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        boolean isInEditMode = editingPosts.contains(parseObjects.get(position).getObjectId());
        if(!isInEditMode && ((ParseObject)parseObjects.get(position).get("user")).getObjectId() == currUser.getObjectId())
        {
            return TYPE_POST_OF_CURRENT_USER;
        }
        if(isInEditMode)
        {
            return TYPE_POST_EDITMODE;
        }
        return TYPE_POST;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return parseObjects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        PostViewHolder viewHolder;
        View view = convertView;
        int type = getItemViewType(position);

        if (view == null)
        {
            viewHolder = new PostViewHolder();

            switch (type) {
                case TYPE_POST:
                    view = inflater.inflate(R.layout.layout_item_threadpost, parent, false);
                    viewHolder.textContent = (TextView) view.findViewById(R.id.editTextContent);
                    break;
                case TYPE_POST_OF_CURRENT_USER:
                    view = inflater.inflate(R.layout.layout_item_threadpost_of_curent_user, parent, false);
                    viewHolder.textContent = (TextView) view.findViewById(R.id.editTextContent);
                    viewHolder.buttonEditPost = (Button) view.findViewById(R.id.buttonEditPost);
                    break;
                case TYPE_POST_EDITMODE:
                    view = inflater.inflate(R.layout.layout_item_threadpost_editmode, parent, false);
                    viewHolder.editTextContent = (EditText) view.findViewById(R.id.editTextContent);
                    viewHolder.buttonSaveEditPost = (Button) view.findViewById(R.id.buttonSaveEditPost);
                    break;
                default:
                    view = inflater.inflate(R.layout.layout_item_threadpost, parent, false);
                    break;
            }

            viewHolder.textUsername = (TextView) view.findViewById(R.id.textViewTitle);
            viewHolder.textCreatedDate = (TextView) view.findViewById(R.id.textViewCreatedDate);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (PostViewHolder) view.getTag();
        }

        final ParseObject parseObject = super.getItem(position);

        viewHolder.textUsername.setText(((ParseObject)parseObject.get("user")).getString("username"));
//        viewHolder.textUsername2.setText(((ParseObject)parseObject.get("user")).getString("username"));
        viewHolder.textCreatedDate.setText(parseObject.getCreatedAt().toString());
        if(type == TYPE_POST_OF_CURRENT_USER ||type == TYPE_POST)
        {
            viewHolder.textContent.setText(parseObject.getString("content"));
        }

        if(type == TYPE_POST_OF_CURRENT_USER) {
            viewHolder.buttonEditPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnClickEditButton(position);
                }
            });
        }

        if(type == TYPE_POST_EDITMODE)
        {
            viewHolder.buttonSaveEditPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnClickSaveEditButton(position);
                }
            });
            viewHolder.editTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        EditText editTextPost = (EditText) v;
                        buff = editTextPost.getText().toString();
                    }
                }
            });
        }
        AndroidUtils.createToast(buff, context);
        return view;
    }

    private class PostViewHolder
    {
        public TextView textUsername;
        public TextView textCreatedDate;
        public TextView textContent;
        public TextView textUsername2;
        public Button buttonEditPost;
        public Button buttonSaveEditPost;
        public EditText editTextContent;
    }
}
