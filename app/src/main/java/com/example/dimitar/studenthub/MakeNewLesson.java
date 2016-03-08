package com.example.dimitar.studenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.parse.ParseUser;

import java.io.File;
import java.io.InputStream;


public class MakeNewLesson extends Fragment {

   public static final String TAG = "Test";

    private ScrollView mScrollView;
    private RelativeLayout mFormView;

    private static int RESULT_LOAD_IMAGE = 1;
    Uri myPicture = null;
    Button buttonLoadImage;


    private static int sId = 0;

    private static int id() {
        return sId++;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach(): activity = " + activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(): savedInstanceState = " + savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): container = " + container
                + "savedInstanceState = " + savedInstanceState);

        if (mScrollView == null) {
            // normally inflate the view hierarchy
            mScrollView = (ScrollView) inflater.inflate(R.layout.fragment_make_new_lesson, container, false);
            mFormView = (RelativeLayout) mScrollView.findViewById(R.id.form);


            //Setting up the button for the image
            buttonLoadImage = (Button) mScrollView.findViewById(R.id.selectPicture);

            buttonLoadImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });


        } else {

            ViewGroup parent = (ViewGroup) mScrollView.getParent();
            parent.removeView(mScrollView);
        }
        return mScrollView;
    }

    //Displaying the picture in image view
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) mFormView.findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated(): savedInstanceState = "
                + savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }


    public void onLoadFinished(Loader<Void> id, Void result) {
        Log.d(TAG, "onLoadFinished(): id=" + id);
    }

    public void onLoaderReset(Loader<Void> loader) {
        Log.d(TAG, "onLoaderReset(): id=" + loader.getId());
    }


}